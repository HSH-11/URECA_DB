
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// ManyToOne - OneToMany BI 
// 1. ManyToOne을 가진 테이블이 Owing Entity 
// 2. Comment, Post 2개의 테이블이 생성 (테이블 구조는 ManyToOne 실습과 동일)
// 3. 연관관계를 Comment의 post_id 컬럼으로 처리
// find
public class Test2 {
	public static void main(String[] args) {

		// MyPersistenceUnitInfo는 persistence.xml을 대신
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// #1. FetchType 설정 없이, Post 객체를 find
//		Post post = em.find(Post.class, 1);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
		
		// #2. FetchType 설정 없이, Comment 객체를 find
//		Comment comment = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?
		
		// #3. FetchType 설정 없이, Post 객체를 find, toString()으로 comments 사용
//		Post post = em.find(Post.class, 1);
//		System.out.println(post.getComments());
		// 순환 참조 Post-Comment (스택 오버플로우)
		// 양방향에서 toString을 호출
		
		// #4. Post에서 Comment의 toString() 상호 참조 제거
//		Post post = em.find(Post.class, 1);
//		System.out.println(post.getComments());
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select c1_0.post_id,c1_0.id,c1_0.content from Comment c1_0 where c1_0.post_id=?
		
		
		// #5. FetchType 설정 LAZY, Comment 객체를 find
//		Comment comment = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?
		// Post 1건 select
//		System.out.println(comment.getPost());
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
		
		// #6. Post 객체 find(), Comment 객체 생성, 연결 Comment 객체 persist
		Post p  = em.find(Post.class, 1);
		
		Comment c3 = new Comment();
		c3.setContent("댓글 3 내용");
		
		c3.setPost(p);
		
		em.persist(c3);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		
		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
