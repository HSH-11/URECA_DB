import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test {
	public static void main(String[] args) {

		// MyPersistenceUnitInfo는 persistence.xml을 대신
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");
		props.put("hibernate.hbm2ddl.auto", "update");
		// create: drop & create
		// update: 있으면 안 건드리고 없으면 만든다.

		/*
		 * create Hibernate: drop table if exists Employee Hibernate: create table
		 * Employee (id integer not null, address varchar(255), name varchar(255),
		 * primary key (id)) engine=InnoDB
		 */
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// #1 ~ #3으로 이전 프로젝트 insert작업
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
//		}

		// #2. find
		// 현재 테이블에 있는 데이터를 객체로 전환 (select) 영속화
		// 영속화 된 객체의 필드값을 변경하여 DB에 반영된다.
//		{
//			Employee e = em.find(Employee.class, 2);
//			System.out.println(e);
//		}

		// #3. merge
		// 현재 테이블에 없는 객체를 생성한 경우면 insert, 이미 있는 객체이면 update
		// persist는 insert만
//		{
//			Employee e = new Employee();
//			e.setId(3);
//			e.setName("삼길동");
//			e.setAddress("춘천 어디");
//			
//			em.merge(e); // 영속화 ( 이시점에 insert 되지 않는다.)

		// 테이블에 있는 경우
//			Employee e = new Employee();
//			e.setId(1);
//			e.setName("홍길동2");
//			e.setAddress("창원 어디");
//			
//			em.merge(e); // 영속화 
//		}
		// #4. remove
//		{
//			Employee e = em.find(Employee.class,2);
//			em.remove(e);
//			
//			try {
//				Thread.sleep(5000);
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		 #1. persist & find
//		 현재 영속화 되어 있는 객체를 find()
//		 find()는 대상이 이미 영속화 되어 있으면 테이블에서 조회 X
		{
			Employee e = new Employee();
			e.setId(1);
			e.setName("일길동");
			e.setAddress("경기도 화성");

			em.persist(e);

			Employee e2 = em.find(Employee.class, 1);
			System.out.println(e2);

		}
		em.getTransaction().commit(); // 이 시점에 DB에 반영된다.

		em.close();

		emf.close();
	}
}
