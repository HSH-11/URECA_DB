package app.phone.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// 관리자 사용자 모드를 선택하는 MainLauncher
// 관리자는 관리자 로그인 인증 수행
// 사용자는 상품 조회, 주문, 주문 정보 확인, 보유 쿠폰 확인 페이지로 이동

public class MainLauncher extends JFrame {

	public MainLauncher() {
		setTitle("휴대폰 판매 관리 시스템");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setLocationRelativeTo(null);

        JButton adminButton = new JButton("관리자 모드");
        JButton userButton = new JButton("사용자 모드");
  
        
        // 관리자 모드 이벤트
        adminButton.addActionListener(e -> {
            dispose();
            new LoginFrame(); // 관리자 로그인 수행
        });
        
        // 사용자 모드 이벤트 
        userButton.addActionListener(e -> {
            dispose();
            new ProductUserView().setVisible(true); // 상품 조회, 주문, 주문 정보 확인, 보유 쿠폰 확인 페이지
        });

        add(adminButton);
        add(userButton);

        setVisible(true);
    }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainLauncher::new);
	}
}
