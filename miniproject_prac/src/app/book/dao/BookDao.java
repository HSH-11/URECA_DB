package app.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.book.common.DBManager;
import app.book.dto.Book;

// book table 에 대한 crud
public class BookDao {

	public int insertBook(Book book) {
		int ret = -1;
		String sql = "insert into book values ( ?, ?, ?, ? ); ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, book.getBookId());
			pstmt.setString(2, book.getBookName());
			pstmt.setString(3, book.getPublisher());
			pstmt.setInt(4, book.getPrice());
			
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public int updateBook(Book book) {
		int ret = -1;		
		String sql = "update book set bookname = ?, publisher = ?, price = ? where bookid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, book.getBookName());
			pstmt.setString(2, book.getPublisher());
			pstmt.setInt(3, book.getPrice());
			pstmt.setInt(4, book.getBookId());
			
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public int deleteBook(int bookId) {
		int ret = -1;
		String sql = "delete from book where bookid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, bookId);
			
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return ret;
	}
	
	public List<Book> listBook(){
		List<Book> list = new ArrayList<>();
		
		String sql = "select * from book; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("bookid"));
				book.setBookName(rs.getString("bookname"));
				book.setPublisher(rs.getString("publisher"));
				book.setPrice(rs.getInt("price"));
				list.add(book);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return list;
	}
	
	public List<Book> listBook(String searchWord){
		List<Book> list = new ArrayList<>();
		
		String sql = "select * from book where bookname like ?; "; // % 사용 X
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("bookid"));
				book.setBookName(rs.getString("bookname"));
				book.setPublisher(rs.getString("publisher"));
				book.setPrice(rs.getInt("price"));
				list.add(book);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return list;
	}
	
	public Book detailBook(int bookId) {
		Book book = null;
		
		String sql = "select * from book where bookid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, bookId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				book = new Book();
				book.setBookId(rs.getInt("bookid"));
				book.setBookName(rs.getString("bookname"));
				book.setPublisher(rs.getString("publisher"));
				book.setPrice(rs.getInt("price"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return book;
	}
}




















