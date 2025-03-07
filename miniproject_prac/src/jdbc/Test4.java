package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Statement 사용
// CRUD를 메소드화 - url, user, pwd를 static
// 메서드 내에서 Connection, Statement, ResultSet
// 메서드 내에서 예외 처리

// Statement -> PrepareStatement 전환
// 하드 코딩된 value --> 메소드의 parameter
// DTO
public class Test4 {
// MySQL에 접근하기 위해 필요한 3가지
	static String url = "jdbc:mysql://localhost:3306/madang";
	static String user = "root";
	static String pwd = "dldydrb15@";

	public static void main(String[] args) {

		System.out.println(insertCustomer(6, "손흥민", "영국 토트넘", "010-6666-6666"));
		System.out.println(updateCustomer(6,"대한민국 춘천"));
		System.out.println(deleteCustomer(6));
		List<CustomerDTO> list = listCustomer();
		for (CustomerDTO dto : list) {
			System.out.println(dto);
		}
		CustomerDTO dto = detailCustomer(6); // 없으면 null
		System.out.println(dto);

	}

	static int insertCustomer(int custid, String name, String address, String phone) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String insertSql = "insert into customer values (?,?,?,?); ";// value에 해당하는 부분을 ?로 대치
		
		int ret = -1;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(insertSql);
			pstmt.setInt(1, custid);
			pstmt.setString(2, name);
			pstmt.setString(3, address);
			pstmt.setString(4, phone);

			ret = pstmt.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);
		}
		return ret;
	}

	static int updateCustomer(int custid, String address) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String updateSql = "update customer set address = ? where custid = ?; ";// value에 해당하는 부분을 ?로 대치
		int ret = -1;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, address);
			pstmt.setInt(2, custid);

			ret = pstmt.executeUpdate();
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            DBManager.releaseConnection(pstmt, con);
        }
		return ret;
	}

	static int deleteCustomer(int custid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String deleteSql = "delete from customer where custid = ?; ";
		
		int ret = -1;
		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(deleteSql);
			pstmt.setInt(1, custid);

			ret = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt,con);
		}
		return ret;

	}

	static List<CustomerDTO> listCustomer() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<CustomerDTO> list = new ArrayList<>();
		String selectSql = "select custid, name, address, phone from customer; ";

		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(selectSql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				CustomerDTO dto = new CustomerDTO();
				dto.setCustid(rs.getInt("custid"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));

				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt,con);
		}
		return list;

	}

	static CustomerDTO detailCustomer(int custid) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerDTO dto = null;

		String selectSql = "select * from customer where custid = ?; ";

		try {
			con = DriverManager.getConnection(url, user, pwd);
			pstmt = con.prepareStatement(selectSql);
			pstmt.setInt(1, custid);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new CustomerDTO();
				dto.setCustid(rs.getInt("custid"));
				dto.setName(rs.getString("name"));
				dto.setAddress(rs.getString("address"));
				dto.setPhone(rs.getString("phone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt,con);
		}
		return dto;
	}
}
