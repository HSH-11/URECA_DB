package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.xdevapi.Result;

public class Test1 {

	public static void main(String[] args) throws Exception {
		// MySQL에 접근하기 위해 필요한 3가지
		String url = "jdbc:mysql://localhost:3306/madang";
		String user = "root";
		String pwd = "dldydrb15@";
		
		// Driver 테스트 (DriverManager에 등록)
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// JDBC 드라이버 객체를 DriverManager에 등록 단계 필요 <= 자동으로 처리
		
		// Connection (인터페이스)
		Connection con = DriverManager.getConnection(url,user,pwd); // DB 연결
		
		
		// Statement (sql 전달 객체)
		Statement stmt = con.createStatement();
		
		// insert
//		{
//			String insertSql = "insert into customer values (6,'손흥민','영국 토트넘','010-6666-6666');";
//			int ret = stmt.executeUpdate(insertSql);
//			System.out.println(ret);
//		}
		
//		update
//		{
//			String updateSql = "update customer set address = '대한민국 서울' where custid = 6; ";
//			int ret = stmt.executeUpdate(updateSql);
//			System.out.println(ret);
//		}
//		
//		//delete 
//		{
//			String deleteSql = "delete from customer where custid = 6; ";
//			int ret = stmt.executeUpdate(deleteSql);
//			System.out.println(ret);
//		}
		
		// ResultSet
		ResultSet rs = null;
		// next() -> 다음 row 이동, 현재 row 유효?
		// select list (복수 건)
		{
			String selectSql = "select * from customer; ";
			// 별칭 사용가능하지만 그럴 때는 getInt or getString메서드 사용 시 무조건 별칭 활용
			rs = stmt.executeQuery(selectSql);
			while(rs.next()) { //row 이동
				// 해당 row 에서 필요한 column 획득 <= rs.getInt(), rs.getString() 괄호 안에 인덱스도 가능, 컬럼명도 가능
				System.out.println(rs.getInt("custid") + " | "+ rs.getString("name")+ " | "+ rs.getString("address")+ " | "+ rs.getString("phone"));
			}
		}
		// select one (단 건)
		{
			String selectSql = "select * from customer where custid = 4; ";
			// 별칭 사용가능하지만 그럴 때는 getInt or getString메서드 사용 시 무조건 별칭 활용
			rs = stmt.executeQuery(selectSql);
			if (rs.next()) { //row 이동
				// 해당 row 에서 필요한 column 획득 <= rs.getInt(), rs.getString() 괄호 안에 인덱스도 가능, 컬럼명도 가능
				System.out.println(rs.getInt("custid") + " | "+ rs.getString("name")+ " | "+ rs.getString("address")+ " | "+ rs.getString("phone"));
			}
		}
		
		stmt.close();
		con.close();
	}

}
