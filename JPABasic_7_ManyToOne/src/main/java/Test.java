
import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.ManyToOne;

public class Test {
	public static void main(String[] args) {

		// MyPersistenceUnitInfo는 persistence.xml을 대신
		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "create"); // drop & create

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		Post p = new Post();
		p.setTitle("제목 1");
		p.setContent("내용 1");
		
		// 1. Post만
//		em.persist(p);
//		Hibernate: insert into Post (content,title) values (?,?)
		
		Comment c1 = new Comment();
		c1.setContent("코멘트 1");
		
		Comment c2 = new Comment();
		c2.setContent("코멘트 2");
		
		// 2. Comment만
//		em.persist(c1);
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		
		// 3. 연결하고 Post만
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(p);
		
		// 4. 연결하고 Comment만
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(c1);
//		persistent instance references an unsaved transient instance of 'entity.Post' 
		
		// 5. 연결하고 Post, Comment 모두, c1 -> c2 -> p
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(c1);
//		em.persist(c2);
//		em.persist(p);
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: update Comment set content=?,post_id=? where id=?
//		Hibernate: update Comment set content=?,post_id=? where id=?
		
		// 6. 연결하고 Post, Comment 모두, p-> c1 -> c2 Update 필요 X
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		
		
		// 7. Comment에 @ManyToOne(cascade = CascadeType.PERSIST) 추가
		// c1,c2만 persist하면 Post도 추가
//		c1.setPost(p);
//		c2.setPost(p);
//
//		em.persist(c1);
//		em.persist(c2);
//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)
		em.getTransaction().commit();

		em.close();

		emf.close();
	}
}
