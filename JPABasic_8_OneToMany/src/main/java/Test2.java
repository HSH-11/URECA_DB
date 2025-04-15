
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OneToMany;

public class Test2 {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		/*-------------------------------------------------------------------------------------------------*/

		// #1. FetchType 설정 없이 post 객체만 find
		// OneToMany의 One에 해당하는 Post에 달린 Many에 해당하는 Comment가 복수개 일 수 있으므로
		// default FetchType.LAZY
//		Post p = em.find(Post.class, 1);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?

		// #2. FetchType 설정 없이 Comment 객체만 find
		// OneToMany의 Many에 해당하는 Comment는 연관관계가 없으므로 독립적으로 select 수행
//		Comment c1 = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content from Comment c1_0 where c1_0.id=?

		// #3. FetchType 설정 없이 Post 객체만 find, Post 객체의 comments 사용
//		Post p = em.find(Post.class, 1);
//		p.getComments(); // 참조변수만 가져오므로 Comment 객체를 사용 코드 X
		// Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where
		// p1_0.id=?
//		p.getComments().forEach(comment -> System.out.println(comment));
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select c1_0.Post_id,c1_1.id,c1_1.content from Post_Comment c1_0 join Comment c1_1 on c1_1.id=c1_0.comments_id where c1_0.Post_id=?
//		OneToMany의 주체는 One(Post)이므로 Post에 복수개의 Many를 표현하지 않아 연관 테이블(Post_Comment)를 만들었고 이를 이용함

		// #4. FetchType.EAGER로 설정, Post 객체만 find, Post 객체의 Comments 사용
//		Post p = em.find(Post.class, 1);
//		
//		try {
//			Thread.sleep(5000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		p.getComments().forEach(comment -> System.out.println(comment));
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title,c1_0.Post_id,c1_1.id,c1_1.content from Post p1_0 left join Post_Comment c1_0 on p1_0.id=c1_0.Post_id left join Comment c1_1 on c1_1.id=c1_0.comments_id where p1_0.id=?
//		EAGER이므로 Post와 Post의 연관관계인 Comment를 한꺼번에 가져온다.

		// #5. Post 1개 find, Comment 1개 생성, 연결 -> Comment persist
		Post p = em.find(Post.class, 1);

		Comment c3 = new Comment();
		c3.setContent("코멘트 3 내용");

		// Post p 와 Comment c3 연결 @OneToMany
//		p.setComments(List.of(c3)); // 기존 연결 모두 삭제하고 c3 연결을 한다는 의미
		p.getComments().add(c3); // 기존 연결을 유지 한 채, 새로운 c3 객체 추가
		// 연결 후, p, c3 영속화가 되어야 한다. 근데, p는 이미 find() 했으므로 영속화 진행된 상태
		// c3만 하면된다.
		em.persist(c3);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select c1_0.Post_id,c1_1.id,c1_1.content from Post_Comment c1_0 join Comment c1_1 on c1_1.id=c1_0.comments_id where c1_0.Post_id=?
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: delete from Post_Comment where Post_id=?
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)

		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
