package swing.book;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddBookDialog extends JDialog {
	private JTextField bookIdField, bookNameField, publisherField, priceField;
	private JButton addButton;
	
	public AddBookDialog(JFrame parent, DefaultTableModel tableModel) {
		setTitle("Book Add Dialog");
		setSize(300, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent); // 부모에 맞게
		
		// input panel
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4, 2));
				
		// field
		bookIdField = new JTextField();
		bookNameField = new JTextField();
		publisherField = new JTextField();
		priceField = new JTextField();
		
		// add field with label, button
		inputPanel.add(new JLabel("Book Id"));
		inputPanel.add(bookIdField);
		inputPanel.add(new JLabel("Book Name"));
		inputPanel.add(bookNameField);
		inputPanel.add(new JLabel("Publisher"));
		inputPanel.add(publisherField);
		inputPanel.add(new JLabel("Price"));
		inputPanel.add(priceField);
				
		// button panel
		JPanel buttonPanel = new JPanel();
				
		// button
		addButton = new JButton("Add");
				
		buttonPanel.add(addButton);		

		// add inputPanel, buttonPanel to Dialog
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
				
		
		// add button actionListner
		addButton.addActionListener( e -> {
			String bookId = bookIdField.getText();
			String bookName = bookNameField.getText();
			String publisher = publisherField.getText();
			String price = priceField.getText();
			
			// 현재는 ui 에 직접 추가하지만 이후 jdbc 를 이용할 때는 db 에 bookId 부터 값을 이용해서 insert 하고 성공하면 list 로 불러와서 화면에 다시 추가한다.
			tableModel.addRow(new Object[] {bookId, bookName, publisher, price});
			
			dispose();
		});
	}
}













