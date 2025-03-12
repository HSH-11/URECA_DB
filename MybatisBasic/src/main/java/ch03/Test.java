package ch03;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import ch03.config.MybatisConfig;
import ch03.dao.BookDao;
import ch03.dto.BookDto;

// 1. Mybatis 의존성 추가
// 2. Mybatis 설정(1.xml 2.java)
// 3. mysql jdbc driver 의존성 추가
// 4. 위 설정한 mybatis xml 문서를 라이브러리가 읽어서 처리하도록 코드 작성

//mybatis는 SqlSession을 통해서 DB와 연동

// ch02는 xml mapper 사용 X, Dao에 annotation으로 대체
// <= mybatis-config-2에 class 속성으로 Dao를 줌

// ch03은 xml 설정파일을 java 클래스로 대체
public class Test {

	public static void main(String[] args) throws Exception{
//		Reader reader = Resources.getResourceAsReader("config/mybatis-config-2.xml");
//		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSessionFactory sqlSessionFactory = new MybatisConfig().getSqlSessionFactory();
		SqlSession session = sqlSessionFactory.openSession();
		
		BookDao bookDao = session.getMapper(BookDao.class); // BookDao 클래스 정보를 SqlSession 객체에 전달
		// mybatis로 DB 연동 작업 진행
		// 목록
		{
			List<BookDto> bookList = bookDao.listBook();
			for (BookDto bookDto : bookList) {
				System.out.println(bookDto);
			}
		}
		// 상세
		{
			BookDto bookDto = bookDao.detailBook(1);		
			System.out.println(bookDto);
			
		}
		
//		// 등록
//		{
//			BookDto bookDto = new BookDto(11, "11번째 책", "11 출판사", 20000);
//			int ret = bookDao.insertBook(bookDto);
//			System.out.println(ret);
//			session.commit(); //등록,수정,삭제는 commit() 필요
//		}
//		
//		// 수정
//		{
//			BookDto bookDto = new BookDto(11, "11번째 책 수정", "11 출판사 수정", 30000);
//			int ret = bookDao.updateBook(bookDto);
//			System.out.println(ret);
//			session.commit(); //등록,수정,삭제는 commit() 필요
//		}
		
		// 삭제
//		{
//			int ret = bookDao.deleteBook(11);
//			System.out.println(ret);
//			session.commit();
//		}
//						
		session.close();
	}

}
