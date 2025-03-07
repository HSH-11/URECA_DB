package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.xdevapi.Result;

// Statement 사용
// CRUD를 메소드화 - url, user, pwd를 static
// 메서드 내에서 Connection, Statement, ResultSet
// 메서드 내에서 예외 처리
public class Test2 {
// MySQL에 접근하기 위해 필요한 3가지
	static String url = "jdbc:mysql://localhost:3306/madang";
	static String user = "root";
	static String pwd = "dldydrb15@";

	public static void main(String[] args) {

//		insertCustomer();
//		updateCustomer();
//		deleteCustomer();
//		listCustomer();
		detailCustomer();


	}

	static void insertCustomer() {
		Connection con = null;
		Statement stmt = null;

		try {
			con = DriverManager.getConnection(url, user, pwd);
			stmt = con.createStatement();

			String insertSql = "insert into customer values ( 6, '손흥민', '영국 토트넘', '010-6666-6666' ); ";
			int ret = stmt.executeUpdate(insertSql);
			System.out.println(ret);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static void updateCustomer() {
		Connection con = null;
		Statement stmt = null;

		try {
			con = DriverManager.getConnection(url, user, pwd);
			stmt = con.createStatement();

			String updateSql = "update customer set address = '대한민국 서울' where custid = 6; ";
			int ret = stmt.executeUpdate(updateSql);
			System.out.println(ret);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static void deleteCustomer() {
		Connection con = null;
		Statement stmt = null;

		try {
			con = DriverManager.getConnection(url, user, pwd);
			stmt = con.createStatement();

			String deleteSql = "delete from customer where custid = 6; ";
			int ret = stmt.executeUpdate(deleteSql);
			System.out.println(ret);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static void listCustomer() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, pwd);
			stmt = con.createStatement();

			String selectSql = "select * from customer; ";
			rs = stmt.executeQuery(selectSql);
			while (rs.next()) { 
				System.out.println(rs.getInt("custid") + " | " + rs.getString("name") + " | " + rs.getString("address")
						+ " | " + rs.getString("phone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	static void detailCustomer() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, user, pwd);
			stmt = con.createStatement();

			String selectSql = "select * from customer where custid = 4; ";
			rs = stmt.executeQuery(selectSql);
			if (rs.next()) { 
				System.out.println(rs.getInt("custid") + " | " + rs.getString("name") + " | " + rs.getString("address")
						+ " | " + rs.getString("phone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
