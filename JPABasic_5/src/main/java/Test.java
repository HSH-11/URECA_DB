
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
		props.put("hibernate.hbm2ddl.auto", "create"); // drop & create


		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Employee emp = new Employee();
		emp.setName("홍길동");
		emp.setAddress("서울 어디");
		
		em.persist(emp);
		
		em.getTransaction().commit(); // 이 시점에 DB에 반영된다.

		em.close();

		emf.close();
	}
}

