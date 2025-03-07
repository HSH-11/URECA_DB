package app.phone.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import app.phone.dao.OrderDAO;
import app.phone.dto.OrderDTO;
import app.phone.dto.OrderItemDTO;

public class OrderHistoryDialog extends JDialog {
    private JTable table;
    private DefaultTableModel tableModel;
    private OrderDAO orderDAO = new OrderDAO();
    private int customerId;
    private boolean isAdminMode;

    public OrderHistoryDialog(Window owner, int customerId, boolean isAdminMode) {
        super(owner, isAdminMode ? "주문 정보" : "내 주문 내역", ModalityType.APPLICATION_MODAL);
        this.customerId = customerId;
        this.isAdminMode = isAdminMode;

        setSize(700, isAdminMode ? 500 : 450);
        setLocationRelativeTo(owner);

        // 관리자/사용자 모드 구분
        if (isAdminMode) {
            tableModel = new DefaultTableModel(
                new Object[]{"주문번호", "상태", "결제상태", "배송지", "상품명", "수량", "단가", "할인 금액", "총 금액"}, 0
            );
        } else {
            tableModel = new DefaultTableModel(
                new Object[]{"주문번호", "상품명", "수량", "가격", "총 금액", "할인 금액", "배송지"}, 0
            );
        }

        table = new JTable(tableModel);
        loadOrderHistory();

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("주문 삭제");
        deleteButton.addActionListener(e -> deleteSelectedOrder());
        buttonPanel.add(deleteButton);

        if (isAdminMode) {
            JButton closeButton = new JButton("닫기");
            closeButton.addActionListener(e -> dispose());
            buttonPanel.add(closeButton);
        }

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadOrderHistory() {
        tableModel.setRowCount(0);
        List<OrderDTO> orders = orderDAO.getOrdersByCustomerId(customerId);

        for (OrderDTO order : orders) {
            for (OrderItemDTO item : order.getOrderItems()) {
                if (isAdminMode) {
                    tableModel.addRow(new Object[]{
                        order.getOrderId(),
                        order.getOrderStatus(),
                        order.getPaymentStatus(),
                        order.getShippingAddress(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        order.getDiscountAmount(),
                        order.getTotalAmount()
                    });
                } else {
                    tableModel.addRow(new Object[]{
                        order.getOrderId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        order.getTotalAmount(),
                        order.getDiscountAmount(),
                        order.getShippingAddress()
                    });
                }
            }
        }
    }

    private void deleteSelectedOrder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 주문을 선택하세요.");
            return;
        }

        int orderId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "정말 이 주문을 삭제하시겠습니까?", "주문 삭제", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (orderDAO.deleteOrder(orderId)) {
            JOptionPane.showMessageDialog(this, "주문이 삭제되었습니다.");
            loadOrderHistory();
        } else {
            JOptionPane.showMessageDialog(this, "주문 삭제에 실패했습니다.");
        }
    }
}
