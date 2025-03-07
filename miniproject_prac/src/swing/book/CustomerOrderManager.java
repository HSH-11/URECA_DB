package swing.book;

import javax.swing.*;

import app.book.ui.BookManager;
import app.book.ui.EditBookDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerOrderManager extends JFrame {
	private JPanel customerPanel;
    private JPanel orderPanel;
    private JPanel mainPanel;

    public CustomerOrderManager() {
    	setTitle("Swing Template Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create MenuBar
        JMenuBar menuBar = new JMenuBar();
        
        JMenu customerMenu = new JMenu("Customer");
        JMenu orderMenu = new JMenu("Order");
        
        menuBar.add(customerMenu);
        menuBar.add(orderMenu);
        setJMenuBar(menuBar);

        // Create Panels
        customerPanel = new JPanel();
        customerPanel.add(new JLabel("Customer Panel"));
        orderPanel = new JPanel();
        orderPanel.add(new JLabel("Order Panel"));

        // Create Main Panel with CardLayout
        mainPanel = new JPanel(new CardLayout());
        mainPanel.add(customerPanel, "Customer");
        mainPanel.add(orderPanel, "Order");

        // Add Main Panel to Frame
        add(mainPanel);

        // lambda X <- functional interface X
        customerMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	showPanel("Customer");
            }
        });
        
        // lambda X <- functional interface X
        orderMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	showPanel("Order");
            }
        });

        // Show default panel
        showPanel("Customer");
    }

    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerOrderManager().setVisible(true);
            }
        });
    }
}