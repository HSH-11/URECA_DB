import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class Test {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// #1. join
//		String jpql = "select p, o from Product p inner join p.orders o"; // inner 생략 가능
//		String jpql = "select p, o from Product p, Orders o where p.id = o.product.id"; // where id 필드 비교
//		String jpql = "select p, o from Product p, Orders o where p = o.product"; // where 객체 비교
//		String jpql = "select o, p from Product p, Orders o where p = o.product"; // select 객체 순서 변경
//		
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach( objArray -> {
//				System.out.println(objArray[0]);
//				System.out.println(objArray[1]);
//			});
		
		
		// #2. left outer join
		//  Customer 기준
//		String jpql = "select c, o from Customer c left join c.orders o"; // select 객체 순서 변경
//		
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach( objArray -> {
//				System.out.println(objArray[0] + ", " + objArray[1]);
//			});
		
		// #3. left outer join + count + group by
		//  Customer 기준
		//  orders 에서 모든 고객을 대상으로 몇 건 주문했는지 확인 ( 주문하지 않은 고객도 포함 )
//		String jpql = "select c, count(o) from Customer c left join c.orders o group by c"; 
//		
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach( objArray -> {
//				System.out.println(objArray[0] + ", " + objArray[1]);
//			});
		
		// #4. join + 조건
//		String jpql = 
//				"""
//				select p, o 
//				  from Product p join p.orders o
//				 where p.price > 1000
//				   and p.quantity > 10
//				   and o.orderQuantity = 10
//				
//				""";
//		
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach( objArray -> {
//				System.out.println(objArray[0] + ", " + objArray[1]);
//			});
		
		
		// #5. join + 조건 + 일부 필드 
//		String jpql = 
//				"""
//				select p.id, p.name, p.price, o.orderQuantity, o.orderDate 
//					from Product p join p.orders o
//				where p.price > 1000
//					and p.quantity > 10
//					and o.orderQuantity = 10
//				""";
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> {
		//  대응 되는 Dto 객체를 생성 Spring 자동 처리 등
//			System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2] + ", " + objArray[3] + ", " + objArray[4]);
//		});
		
		// #6. subquery + where
		// 주문 건 중에, 제품 가격이 5000 미만인 건에 대해서 질의 subquery 이용
//		String jpql = 
//				"""
//				select o
//					from Orders o
//				where o.product in (select p from Product p where p.price < 5000 )
//				""";
//		
//		em.createQuery(jpql, Orders.class)
//		.getResultList()
//		.forEach(orders -> System.out.println(orders));
		
		// #7. subquery + select
		// 모든 주문 건에 대해 주문한 고객의 이름을 함께 select
		// (select c.name from Customer c) as customerName [Subquery returns more than 1 row]
//		String jpql = 
//				"""
//				select o, (select c.name from Customer c where o.customer = c ) as customerName
//					from Orders o
//				""";
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1]));
		
				
		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
