import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) {
		// EntityManager <= EntityManagerFactory
//		EntityManagerFactory는 JPA의 엔티티 매니저(EntityManager)를 생성하는 공장 역할
		// src/main/resources/META-INF/persistence.xml을 만들도록 되어 있음
		// persistence.xml에서 "my-pu'라는 이름의 Persistence Unit을 찾아서, 이에 맞는 EntityManagerFactory를 생성.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		// EntityManager는 실제 데이터베이스와 상호작용하는 객체
		// 트랜잭션 단위로 동작하는 것이 특징
		//여러 개의 EntityManager를 생성할 수 있지만, 
//		일반적으로 하나의 요청(트랜잭션)마다 하나를 생성해서 사용한 후 닫아(em.close()) 줘야 함.
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// persistence 작업
		// class - table @Entity Annotation이용
		
		// Product - product
		Product p = new Product();
		p.setId(1L);
		p.setName("Phone");
		
		em.persist(p); // 엔티티가 영속성 컨텍스트에 추가됨.
		// 바로 DB에 저장되지 않고, 트랜잭션이 커밋될 때 실제 insert 쿼리가 실행
		// JPA가 관리하는 상태로 존재.
		
		em.getTransaction().commit();
		
		em.close();
		
		emf.close();
	}

}
