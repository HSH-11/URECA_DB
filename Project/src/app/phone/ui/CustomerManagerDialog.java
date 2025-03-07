package app.phone.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.phone.dao.CouponDAO;
import app.phone.dao.CustomerCouponDAO;
import app.phone.dao.CustomerDAO;
import app.phone.dto.CouponDTO;
import app.phone.dto.CustomerDTO;

public class CustomerManagerDialog extends JDialog {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO = new CustomerDAO();

    public CustomerManagerDialog(JFrame parent) {
        super(parent, "고객 관리", true);

        
        setSize(600, 400);
        setLocationRelativeTo(parent);

        
        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Name", "Email", "Phone", "Address"}, 0);
        customerTable = new JTable(tableModel);

        
        listCustomers();
 
        JScrollPane scrollPane = new JScrollPane(customerTable);

        JButton deleteButton = new JButton("삭제");
        JButton viewOrderButton = new JButton("주문 정보 보기");
        JButton issueCouponButton = new JButton("쿠폰 발급");
        JButton showCouponsButton = new JButton("보유 쿠폰 보기");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewOrderButton);
        buttonPanel.add(issueCouponButton);
        buttonPanel.add(showCouponsButton);

        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        
        deleteButton.addActionListener(e -> deleteCustomer());
        viewOrderButton.addActionListener(e -> viewCustomerOrders());
        issueCouponButton.addActionListener(e -> issueCouponToCustomer());
        showCouponsButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "먼저 고객을 선택하세요.");
                return;
            }

            int customerId = (int) customerTable.getValueAt(selectedRow, 0);  // 고객ID 가져오기
            showCustomerCoupons(customerId);
        });
    }

    private void listCustomers() {
        List<CustomerDTO> customerList = customerDAO.getAllCustomers();
        for (CustomerDTO customer : customerList) {
            tableModel.addRow(new Object[]{customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress()});
        }
    }


    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int customerId = (int) customerTable.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "정말로 이 고객을 삭제하시겠습니까?", "고객 삭제", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean result = customerDAO.deleteCustomer(customerId);
                if (result) {
                    JOptionPane.showMessageDialog(this, "고객이 삭제되었습니다.");
                    tableModel.removeRow(selectedRow); 
                } else {
                    JOptionPane.showMessageDialog(this, "고객 삭제에 실패했습니다.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "삭제할 고객을 선택하세요.");
        }
    }

    private void viewCustomerOrders() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int customerId = (int) customerTable.getValueAt(selectedRow, 0);
            OrderHistoryDialog orderDialog = new OrderHistoryDialog(this, customerId, true);
            orderDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "주문 정보를 확인할 고객을 선택하세요.");
        }
    }
    public void showCustomerCoupons(int customerId) {
        CustomerCouponDAO dao = new CustomerCouponDAO();
        List<CouponDTO> coupons = dao.getCustomerCoupons(customerId);

        StringBuilder sb = new StringBuilder("보유 쿠폰 목록:\n");
        for (CouponDTO coupon : coupons) {
            sb.append(coupon.getCouponName())
              .append(" (").append(coupon.getDiscountRate()).append("% 할인)\n ");
              
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }
    
    private void issueCouponToCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "먼저 쿠폰을 발급할 고객을 선택하세요.");
            return;
        }

        int customerId = (int) customerTable.getValueAt(selectedRow, 0);

        // 쿠폰 선택을 위한 다이얼로그 표시
        CouponDAO couponDAO = new CouponDAO();
        List<CouponDTO> allCoupons = couponDAO.getAllCoupons();

        if (allCoupons.isEmpty()) {
            JOptionPane.showMessageDialog(this, "발급할 쿠폰이 없습니다. 먼저 쿠폰을 등록하세요.");
            return;
        }

        CouponDTO selectedCoupon = (CouponDTO) JOptionPane.showInputDialog(
            this,
            "발급할 쿠폰을 선택하세요.",
            "쿠폰 선택",
            JOptionPane.QUESTION_MESSAGE,
            null,
            allCoupons.toArray(),
            null
        );

        if (selectedCoupon != null) {
            CustomerCouponDAO customerCouponDAO = new CustomerCouponDAO();
            customerCouponDAO.issueCouponToCustomer(customerId, selectedCoupon.getCouponId());
            JOptionPane.showMessageDialog(this, "쿠폰이 발급되었습니다: " + selectedCoupon.getCouponName());
        }
    }

}

