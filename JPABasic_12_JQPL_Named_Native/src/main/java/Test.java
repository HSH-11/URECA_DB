import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;


import config.MyPersistenceUnitInfo;
import entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// Named Query: 별도의 이름을 가지는 query를 자바코드 안이 아닌 관련 Entity 상단에 Annotation으로 표현
// 				자바 코드에서는 이름으로 query를 사용
// Native Query: JPQL이 아닌 표준 SQL을 사용
// 위 두 방법을 Spring Data JPA를 이용하는 경우, @Query에 name 또는 native=true 사용
public class Test {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

//		 #1. Orders.findByOrderDate		
//		em.createNamedQuery("Orders.findByOrderDate", Orders.class)
//			.setParameter("orderDate", LocalDate.of(2024,3,11))
//			.getResultList()
//			.forEach( System.out::println);

		// #2. Orders.findByOrderDateRange
//		em.createNamedQuery("Orders.findByOrderDateRange", Orders.class)
//		.setParameter("startDate", LocalDate.of(2024,3,11))
//		.setParameter("endDate", LocalDate.of(2025,4,16))
//		.getResultList()
//		.forEach( System.out::println);

		// #3. Orders.findByProductPriceRange
//		em.createNamedQuery("Orders.findByProductPriceRange", Object[].class)
//		.setParameter("startPrice", 2000)
//		.setParameter("endPrice", 3000)
//		.getResultList()
//		.forEach( objArray -> {
//			System.out.println(objArray[0] + ", " + objArray[1]);
//		});

		// #4. Native Query
//		String sql = 
//				"""
//					select o.* 
//						from orders o,
//							customer c
//					where o.customer_id = c.id
//						and c.name = :customerName
//					
//				""";
//		
//		List<?> ordersList = em.createNativeQuery(sql, Orders.class)
//			.setParameter("customerName", "고객2")
//			.getResultList();
//			
//		ordersList.forEach( orders -> System.out.println((Orders) orders));
//				

//
//        // #1. JPA Criteria API 예제: 특정 주문일로 검색
//		CriteriaBuilder cb = em.getCriteriaBuilder();
//
//		CriteriaQuery<Orders> cq = cb.createQuery(Orders.class);
//		Root<Orders> root = cq.from(Orders.class);
//		cq.select(root).where(cb.equal(root.get("orderDate"), LocalDate.of(2024, 3, 11)));
//		List<Orders> specificDateResults = em.createQuery(cq).getResultList();
//		specificDateResults.forEach(o -> System.out.println("[Criteria] " + o));
		
		
		// Hibernate: select
		// o1_0.id,o1_0.customer_id,o1_0.order_date,o1_0.order_quantity,o1_0.product_id
		// from Orders o1_0 where o1_0.order_date=?
//        [Criteria Exact] Orders [id=7, orderQuantity=5, orderDate=2024-03-11]
//        [Criteria Exact] Orders [id=8, orderQuantity=3, orderDate=2024-03-11]

		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
