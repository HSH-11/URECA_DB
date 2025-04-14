
import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

public class Test2 {
	public static void main(String[] args) {

		// MyPersistenceUnitInfo는 persistence.xml을 대신
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create"); // drop & create

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// #1. Post
//		Post post = em.find(Post.class, 1);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?

		// 2. Comment
//		Comment comment = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title from Comment c1_0 left join Post p1_0 on p1_0.id=c1_0.post_id where c1_0.id=?

		// 3. Comment
		// @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 일 경우
//		Comment comment = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?

		// 4. Comment
		// @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 일 경우
		// toString()을 통해 Post 수행
//		Comment comment = em.find(Comment.class, 1);
//		System.out.println(comment);
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
		// 자신 Comment만 가져온 후, Post 사용할 때 다시 post를 가져온다.
		
		// 5. find한 Post와 new 한 Comment
		Post post = em.find(Post.class, 1);
		
		Comment c3 = new Comment();
		c3.setContent("코멘트 3");
		
		c3.setPost(post);
		em.persist(c3);
		
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		
		em.getTransaction().commit();

		em.close();

		emf.close();
	}
}
