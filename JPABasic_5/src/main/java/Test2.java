import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Product;
import entity.Student;
import entity.key.ProductKey;
import entity.key.StudentKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test2 {
	public static void main(String[] args) {


		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "update");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();
	
		em.getTransaction().begin();
		
		// @IdClass()
		{
//			Product p = new Product();
//			p.setCode("uplus");
//			p.setNumber(1);
//			p.setColor("blue");
//			
//			em.persist(p);
			
//			ProductKey key = new ProductKey();
//			key.setCode("uplus");
//			key.setNumber(1);
//			Product p = em.find(Product.class, key);
//			System.out.println(p);
		}
		
		// Embedded
		// 항상 Embedded Key로부터 접근
		
//		{
//			StudentKey key = new StudentKey();
//			key.setCode("uplus");
//			key.setNumber(1);
//			
//			Student s = new Student();
//			s.setId(key);
//			s.setName("홍길동");
//			
//			em.persist(s);
		
			StudentKey key = new StudentKey();
			key.setCode("uplus");
			key.setNumber(1);
			
			Student s = em.find(Student.class, key);
			System.out.println(s);
//		}
		
		em.getTransaction().commit(); // 이 시점에 DB에 반영된다.

		em.close();

		emf.close();
	}
}
