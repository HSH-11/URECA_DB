package jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// JDBC를 이용한 MySQL DB Transaction Test
public class TransactionTest {
	static boolean isSuccess;
	
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into customer values (? , ?);";
		int ret = -1;
		
		try {
			// JDBC의 Connection 객체는 default로 autocommit이 true로 설정되어 있음.
			con = DBManager.getConnection();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, 1);
			pstmt.setString(2, "홍길동");
			
			ret = pstmt.executeUpdate();
			System.out.println(ret);
			
			pstmt.setInt(1, 2);
			pstmt.setString(2, "이길동");
			
			ret = pstmt.executeUpdate();
			System.out.println(ret);
			
			pstmt.setInt(1, 3);
			pstmt.setString(2, "김길동");
			
			ret = pstmt.executeUpdate();
			System.out.println(ret);
			
//			con.commit(); //생략되면 DB에 반영되지 않는다.
			
			isSuccess = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			isSuccess = false;
			
		}finally {
			try {
				if (isSuccess) con.commit();
				else con.rollback();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			DBManager.releaseConnection(pstmt, con);
		}
	}

}
