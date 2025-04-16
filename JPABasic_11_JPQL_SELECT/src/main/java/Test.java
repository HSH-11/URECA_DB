
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// JPA Select
// DB SQL 객체 중심으로 표현 또는 대체 하려는 노력 ( 결과적으로 완전히 대체 X => DB 표준 SQL을 일부 사용할 수 밖에 없다.)
public class Test {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

//		#1.
//		테이블 데이터 -> 객체화
//		JPQL 사용 안하고 em의 메소드를 사용 <= 단 건만 select
//		Product p = em.find(Product.class, 1);
//		System.out.println(p);

		// 테이블 데이터 -> 객체화(목록)은 find() 처리 X => JPQL
		// JPQL은 select only
		// 표준 SQL과 비슷하지만, 객체 표현 (Product(클래스명으로 해야함 테이블 명이 아님))

//		#2. Query (Type X)
//		String jpql = "select p from Product p";
//		Query q = em.createQuery(jpql);
//		List<?> productList = q.getResultList();
//		
//		for (Object object : productList) {
//			Product p = (Product) object;
//			System.out.println(p);
//		}

//		#3. TypedQuery (Type O)
//		String jpql = "select p from Product p";

//		TypedQuery<Product> q = em.createQuery(jpql,Product.class);
//		List<Product> productList = q.getResultList();
//		productList.forEach(product -> System.out.println(product));

		// 위 코드 간략히 표현
//		em.createQuery(jpql,Product.class)
//			.getResultList()
//			.forEach(product -> System.out.println(product));

		// #4. 개별 필드
//		select를 할 때 클래스 필드명으로 id,name,price만 가져와보자
//		=> Product로 받을 수 없음(부분 필드만 가져왔기 때문) 따라서 Object[]로 처리
//		String jpql = "select p.id, p.name, p.price from Product p";
//		em.createQuery(jpql,Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2]));

		// #5. select + where ( 필드를 사용 표현 )
//		String jpql = "select p from Product p where p.price > 2000";
//		em.createQuery(jpql,Product.class)
//			.getResultList()
//			.forEach(product -> System.out.println(product));

		// #6. select + where + and + param (PreparedStatement의 ? 대응)
//		String jpql = "select p from Product p where p.price > :price and p.quantity > : quantity";
//		em.createQuery(jpql, Product.class)
//				.setParameter("price", 2000)
//				.setParameter("quantity", 20)
//				.getResultList()
//				.forEach(product -> System.out.println(product));

		// #7. select + where + and + param using index
//		String jpql = "select p from Product p where p.price > ?1 and p.quantity > ?2";
//		em.createQuery(jpql, Product.class)
//		.setParameter(1, 2000)
//		.setParameter(2, 20)
//		.getResultList()
//		.forEach(product -> System.out.println(product));
		
		// #8. select + where + and + param + like
//		String jpql = "select p from Product p where p.price > :price and p.country like :country";
//		em.createQuery(jpql, Product.class)
//		.setParameter("price", 2000)
//		.setParameter("country", "%ko%")
//		.getResultList()
//		.forEach(product -> System.out.println(product));
		
		// #9. select + aggregation function count() (정수형)
		// aggregation function은 1개의 row 리턴 getResultList(복수) 대신 getSingleResult(단수) 사용
//		String jpql = "select count(p) from Product p";
		
//		Long cnt = em.createQuery(jpql, Long.class).getSingleResult(); // Long return
//		System.out.println(cnt);

		// #10. select + aggregation function avg() (실수형)
//		String jpql = "select avg(p.price) from Product p";
		
//		Double avg = em.createQuery(jpql, Double.class).getSingleResult(); // Double return
//		System.out.println(avg);

		// #11. select + aggregation function sum(), min(), max() 한 번에
//		String jpql = "select sum(p.quantity), min(p.quantity), max(p.quantity) from Product p";
//		
//		Object[] objArray = em.createQuery(jpql, Object[].class).getSingleResult(); // Double return
//		System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2]);

		// #12. select + aggregation function sum(), min(), max() + group by country
		String jpql = "select p.country, sum(p.quantity), min(p.quantity), max(p.quantity) from Product p group by p.country";
		
		List<Object[]> objArrayList = em.createQuery(jpql, Object[].class).getResultList(); // Double return
		objArrayList.forEach(objArray -> {
			System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2] + ", " + objArray[3]);
		});

		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
