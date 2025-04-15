
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// OneToMany
// 1. 테이블 3개 생성, Entity 각각 1개씩, 연관관계 표현하는 테이블 1개
// 2. ManyToOne은 Many가 Owing Entity이므로 Many를 표현하는 테이블에 One 해당하는 컬럼을 추가
// 3. OneToMany는 One이 Owing Entity인데, One을 표현하는 테이블의 복수개의 Many를 표현 X => 별도의 관계 테이블 추가
public class Test {
	public static void main(String[] args) {

		// MyPersistenceUnitInfo는 persistence.xml을 대신
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Post p = new Post();
		p.setTitle("게시글 1");
		p.setContent("게시글 1 내용");

		Comment c1 = new Comment();
		c1.setContent("코멘트 1 내용");

		Comment c2 = new Comment();
		c2.setContent("고멘트 2 내용");
		/*-------------------------------------------------------------------------------------------------*/
		// #2. Comment 2건만 persist
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: insert into Comment (content) values (?)

		// #3. 연결 없이, Post 1건, Comment 2건 persist
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: insert into Comment (content) values (?)

		// #4. Post에 Comment 연결
		// 연결 코드
//		p.setComments(List.of(c1,c2));
//		em.persist(p);
//		em.getTransaction().commit();
//		persistent instance references an unsaved transient instance of 'entity.Comment'
//		OneToMany Post에 연결된 Comment 객체들이 영속화 되지 않았다.

		// #5. Post에 Comment 연결, Post 1건, Comment 2건 persist
//		p.setComments(List.of(c1, c2));
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);

//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)

		// #6. Post에 Comment 연결, Post 1건, Comment 2건 persist
		// CascadeType.persist를 OneToMany에 추가
//		p.setComments(List.of(c1, c2));
//		em.persist(p);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)

		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
