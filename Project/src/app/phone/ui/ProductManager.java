package app.phone.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import app.phone.dao.ProductDAO;
import app.phone.dto.ProductDTO;
public class ProductManager extends JFrame {

	private JTable table;
	private DefaultTableModel tableModel;
	private JButton searchButton, orderButton, addButton, editButton, listButton, customerButton;
	private JTextField searchWordField;

	private ProductDAO productDAO = new ProductDAO();

	public ProductManager() {
		
		setTitle("휴대폰 판매 관리");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		tableModel = new DefaultTableModel(new Object[] { "Product ID", "Name", "Price", "Stock"}, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // 일단 상품 테이블 편집 불가로 해놓기
			}
		};
		
		table = new JTable(tableModel);

		// DB에서 제품 목록을 가져와서 테이블에 표시
		listProducts();

		// 검색 필드 설정
		Dimension textFieldSize = new Dimension(400, 28);
		searchWordField = new JTextField();
		searchWordField.setPreferredSize(textFieldSize);

		searchButton = new JButton("검색");

		JPanel searchPanel = new JPanel();
		searchPanel.add(new JLabel("제품명 검색"));
		searchPanel.add(searchWordField);
		searchPanel.add(searchButton);

		// 버튼 만들기
		addButton = new JButton("등록");
		editButton = new JButton("수정");
		listButton = new JButton("목록");
		orderButton = new JButton("주문");
		customerButton = new JButton("고객 관리");
		
		// 버튼들을 담는 패널
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(editButton);
		buttonPanel.add(listButton);
		buttonPanel.add(orderButton);			
		buttonPanel.add(customerButton);
		
		// 레이아웃 설정
		setLayout(new BorderLayout());
		add(searchPanel, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// 지금부터 버튼 액션 이벤트 처리
		searchButton.addActionListener(e -> {
			String searchWord = searchWordField.getText();
			if (!searchWord.isBlank()) {
				listProducts(searchWord);
			}
		});

		addButton.addActionListener(e -> {
			AddProductDialog addDialog = new AddProductDialog(this, this.tableModel);
			addDialog.setVisible(true);
		});
		
        orderButton.addActionListener(e -> {   
            OrderForm orderForm = new OrderForm();
            orderForm.setVisible(true);
        });

		editButton.addActionListener(e -> {
			// 선택된 제품이 있으면 해당 제품 정보 수정
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				EditProductDialog editDialog = new EditProductDialog(this, this.tableModel, selectedRow);
				editDialog.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "제품을 선택하셔야 합니다.");
			}
		});
		
		// 제품 목록 갱신용
		listButton.addActionListener(e -> listProducts());
		// 고객 관리 정보용
		customerButton.addActionListener(e -> openCustomerManager());	
		
	}
	private void openCustomerManager() {
	    // 고객관리 창 띄우기
	    CustomerManagerDialog customerDialog = new CustomerManagerDialog(this);
	    customerDialog.setVisible(true);
	}
	
	private void clearTable() {
		tableModel.setRowCount(0);
	}
	
	//productDAO.getAllProducts()로 제품 긁어오기
	private void listProducts() {
		clearTable();
		List<ProductDTO> productList = productDAO.getAllProducts();
		for (ProductDTO product : productList) {
			tableModel.addRow(new Object[] { product.getProductId(), product.getName(), product.getPrice(),
					product.getStockQuantity()});
		}
	}
	
	//특정 제품 찾기용
	private void listProducts(String searchWord) {
		clearTable();
		List<ProductDTO> productList = productDAO.getProductsByName(searchWord);
		for (ProductDTO product : productList) {
			tableModel.addRow(new Object[] { product.getProductId(), product.getName(), product.getPrice(),
					product.getStockQuantity()});
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ProductManager().setVisible(true));
	}
}
