
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// N + 1
// 어떤 Entity A가 연관관계를 가진 상태 Entity B에 대해 FetchType이 LAZY일 때,
// A의 목록을 가져오는 경우, B는 가져오지 않는다.
// A의 목록을 가져와서 각각의 A에 대해 연관관계에 있는 B를 사용하면 B를 가져오게 된다.
// 이때 A의 목록에 포함된 A의 수만큼 B를 가져오는 select가 수행된다.
// 결국 이 과정에서 A 목록 가져오는 select 1회, A 목록 수(N)만큼 B를 select N회 수행 => N + 1

// 오인 (jpa를 이용하는 데, 비효율적인 코드를 바로 잡는 상황)
// EAGER -> LAZY, LAZY -> EAGER로 해결.. (X)

// N + 1 은 join fetch를 통해서 해결

public class Test {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// #1
		// N + 1 확인
//		String jpql = "select p from Post p";
//		List<Post> postList = em.createQuery(jpql, Post.class).getResultList(); // Post 목록을 가져온다.(Post는 LAZY)
		// Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 // 3건

//		postList.forEach(post -> post.getComments().size()); // Post 목록을 순회, 연관관계의 Commnet 객체 확인

		// 3건에 대한 select 추가 수행
//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?
//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?
//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?

		// #2
		// 해결 시도: Post의 Comment에 대한 연관관계 FetchType.EAGER로 변경
		// 여전히 N + 1 문제가 발생 Comment에 대한 join이 일어나지 않았음
//		FetchType.EAGER는 연관된 엔티티를 즉시 로딩하려고 하지만, @OneToMany나 @ManyToMany 관계에서 EAGER를 사용할 경우, 
//		Hibernate는 기본적으로 select 쿼리에서 직접 join하는 방식이 아닌, 각 엔티티를 별도의 쿼리로 로드
//		String jpql = "select p from Post p";
//		List<Post> postList = em.createQuery(jpql, Post.class).getResultList(); // Post 목록을 가져온다.(Post는 LAZY)
		// Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 // 3건

//		postList.forEach(post -> post.getComments().size());

//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?
//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?
//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?

		// #3
		// 해결 시도: Post의 Comment에 대한 연관관계 FetchType.EAGER로 변경
		// #2 대한 결과를 가지고, jpql 대신 find()와 비교, find() 형태의 목록을 가져오는 메소드 X
		// find로 하면 EAGER를 이용해 join으로 가져오는 게 됨 N + 1 발생 X
		// 즉 find() != jpql
//		em.find(Post.class, 1);
//		em.find(Post.class, 2);
//		em.find(Post.class, 3);

//		Hibernate: select p1_0.id,p1_0.content,p1_0.title,c1_0.post_id,c1_0.id,c1_0.content from Post p1_0 left join Comment c1_0 on p1_0.id=c1_0.post_id where p1_0.id=?
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title,c1_0.post_id,c1_0.id,c1_0.content from Post p1_0 left join Comment c1_0 on p1_0.id=c1_0.post_id where p1_0.id=?
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title,c1_0.post_id,c1_0.id,c1_0.content from Post p1_0 left join Comment c1_0 on p1_0.id=c1_0.post_id where p1_0.id=?

		// #4
		// Post 목록 대신 PK로 조건을 줘서 select <= find()와 동일하게 EAGER로 가져올까?
		// Join으로 가져오지 않음
//		String jpql = "select p from Post p where p.id = 1";
//		List<Post> postList = em.createQuery(jpql, Post.class).getResultList();
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=1
		
//		postList.forEach(post -> post.getComments().size());
		
		// 1건에 대한 select 추가 수행
//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?
		
		// 중간 결론
		// #2, #3, #4 의 테스트 결과
		// FetchType을 LAZY -> EAGER로 변경하는 방법 실패!!
		
		// 최종 결론
		// Post 목록을 가져와서 Post만 사용하려는 목적이라면 N + 1 문제 발생 X
		// Post 목록을 가져와서 Post의 연관관계인 Comment를 사용하려면 미리 Comment도 가져오는 것이 N + 1 해결
		// FetchType과 상관없이 join fetch로 해결
		
		// #5
		// N + 1에 대해 join fetch로 미리 가져와서 해결
		String jpql = "select p from Post p join fetch p.comments";
		List<Post> postList = em.createQuery(jpql, Post.class).getResultList();
		
//		Hibernate: select p1_0.id,c1_0.post_id,c1_0.id,c1_0.content,p1_0.content,p1_0.title from Post p1_0 join Comment c1_0 on p1_0.id=c1_0.post_id
		
		postList.forEach(post -> post.getComments().size());
		
		
		
		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
