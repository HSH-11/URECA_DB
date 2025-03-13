import java.util.HashMap;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test {

	public static void main(String[] args) {
		
		// MyPersistenceUnitInfo는 persistence.xml을 대신
		EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
				new MyPersistenceUnitInfo(),new HashMap<>());
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
			
		Product p = new Product();
		p.setId(2L);
		p.setName("Book");
		
		em.persist(p); 
		
		em.getTransaction().commit();
		
		em.close();
		
		emf.close();
	}

}