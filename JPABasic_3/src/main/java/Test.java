import java.util.HashMap;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test {
	public static void main(String[] args) {

		// MyPersistenceUnitInfo는 persistence.xml을 대신
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), new HashMap<>());

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		// #1. persist
		// 현재 테이블에 없는 객체를 생성한 후 객체의 내용을 테이블에 반영 (insert)
//		{
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("경기도 성남");
//
//			em.persist(e);
//
//			e.setAddress("서울 어디");
//		}
		
		// #2. find
		// 현재 테이블에 있는 데이터를 객체로 전환 (select) 영속화
		// 영속화 된 객체의 필드값을 변경하여 DB에 반영된다.
//		{
//			Employee e = em.find(Employee.class, 1);
//			System.out.println(e);
//			e.setAddress("대전 어디"); 
//			System.out.println(e);
//			//.....
//			e.setAddress("부산 어디"); 
//			System.out.println(e);
//
//		}
		
		// #3. merge
		// 현재 테이블에 없는 객체를 생성한 경우면 insert, 이미 있는 객체이면 update
		// persist는 insert만 
		{
//			Employee e = new Employee();
//			e.setId(3);
//			e.setName("삼길동");
//			e.setAddress("춘천 어디");
//			
//			em.merge(e); // 영속화 ( 이시점에 insert 되지 않는다.)
			
			// 테이블에 있는 경우
			Employee e = new Employee();
			e.setId(1);
			e.setName("홍길동2");
			e.setAddress("창원 어디");
			
			em.merge(e); // 영속화 
		}
		em.getTransaction().commit(); // 이 시점에 DB에 반영된다.

		em.close();

		emf.close();
	}
}
