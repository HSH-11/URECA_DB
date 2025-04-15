
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
// 2. Comment, Post 2개의 테이블이 생성
// 3. 연관관계를 Comment의 post_id 컬럼으로 처리
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
		p.setTitle("게시글 1 제목");
		p.setContent("게시글 1 내용");

		Comment c1 = new Comment();
		c1.setContent("댓글 1 내용");

		Comment c2 = new Comment();
		c2.setContent("댓글 2 내용");

		// #1. 연결 없이 Post 단 1건 persist
//		em.persist(p);
//		Hibernate: insert into Post (content,title) values (?,?)

		// #2. 연결 없이, Comment 만 2건 persist
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		// 연관 관계 컬럼난 post_id가 null

		// #3. 연결 없이, Post 1건 , Comment 2건 persist
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		
		
		// #4. Comment에만 Post 연결 (ManyToOne), c1, c2만 persist
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(c1);
//		em.persist(c2);
//		
		// Comment의 연관관계 칼럼인 post_id에 채워질 Post 객체가 연결되었으나, 영속화 되지 않아 에러
		//persistent instance references an unsaved transient instance of 'entity.Post'
		
		// #5. Comment에만 Post 연결 (ManyToOne), p, c1, c2 persist
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Comment의 연관 관계 컬림인 Post_id에 Post 객체의 id 값으로 사용됨
		
		// #6. Post에만 Comment 2개 연결 (OneToMany), c1, c2, p persist
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(c1);
//		em.persist(c2);

		// Post 1건 insert, comment insert X
		// ManyToOne, OneToMany 양방향의 Owing 관계는 ManyToOne
		
		// #7. Post에만 comment 2개 연결 (OneToMany), p만 persist
//		p.setComments(List.of(c1,c2));
//		
//		em.persist(p);
		// Hibernate: insert into Post (content,title) values (?,?)
		// Post 1건 insert, comment insert X
		// ManyToOne, OneToMany 양방향의 Owing 관계는 ManyToOne
		// OneToMany를 가진 Post는 연관관계 관련 컬럼 X <= Comment가 함계 영속화 되지 않아도 된다.
		
		// #8. Post에만 comment 2개 연결 (OneToMany), p, c1, c2 persist
		// @ManyToOne
//		p.setComments(List.of(c1,c2));
//		
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
		
		// #9. Post - Comment 모두 (ManyToOne + OneToMany)
		p.setComments(List.of(c1,c2));
		c1.setPost(p);
		c2.setPost(p);
		
		em.persist(p);
		em.persist(c1);
		em.persist(c2);
		
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		
		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
