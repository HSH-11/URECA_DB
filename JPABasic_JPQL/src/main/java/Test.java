import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

// JQPL
// select 수행, insert x, update O
public class Test {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "update");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// #1. normal query
//		{
//			// SQL : "Select * from employee"
//			String jpql = "select e from Employee e"; // Entity(Employee.java)를 이용한 query
//			em.createQuery(jpql);
//			Query query = em.createQuery(jpql);
//			List<Employee> list = query.getResultList();
//
//			for (Employee e : list) {
//				System.out.println(e);
//			}
//		}

		// #2. typed query
//		{
//			// SQL : "Select * from employee where id = 3"
//			String jpql = "select e from Employee e where id <= :id";
//			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
//			query.setParameter("id", 3);
//			List<Employee> list = query.getResultList();
//
//			for (Employee e : list) {
//				System.out.println(e);
//			}
//		}

		// #3. named parameter
//		{
//			// SQL : "Select * from employee where id = 3"
//			String jpql = "select e from Employee e where id <= :id";
//			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
//			query.setParameter("id", 3);
//			List<Employee> list = query.getResultList();
//
//			for (Employee e : list) {
//				System.out.println(e);
//			}
//		}
		// #4. positional parameter (?사용)
//		{
//			// SQL : "Select * from employee where id = 3"
//			String jpql = "select e from Employee e where id <= ?1";
//			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
//			query.setParameter(1, 3);
//			List<Employee> list = query.getResultList();
//
//			for (Employee e : list) {
//				System.out.println(e);
//			}
//		}
		// #5. single result
//		{
//			// SQL : "Select * from employee where id = 3"
//			String jpql = "select e from Employee e where id <= ?1";
//			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
//			query.setParameter(1, 3);
//			Employee e = query.getSingleResult();
//
//			System.out.println(e);
//
//		}
		
		// #6. like
		{
			// SQL : "Select * from employee where id = %수민%"
			String jpql = "select e from Employee e where e.name like :searchWord";
			String searchWord = "%수민%";
			TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
			query.setParameter("searchWord", searchWord);
			List<Employee> list = query.getResultList();

			for (Employee e : list) {
				System.out.println(e);
			}
		}

//		em.getTransaction().commit(); 

		em.close();

		emf.close();
	}
}
