
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Team;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// ManyToMany 양방향
// teams, users, teams_users 3개의 테이블 생성 확인
// persist
public class Test {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// #1
		// 테이블 생성 확인
		
		// #2 User 객체 2개만 persist
//		User u1 = new User();
//		u1.setName("김도영");
//		
//		User u2 = new User();
//		u2.setName("나성범");
//		
//		em.persist(u1);
//		em.persist(u2);
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
		
		
		// #3. Team 객체 2개만 persist
//		Team t1 = new Team();
//		t1.setName("팀 1");
//		
//		Team t2 = new Team();
//		t2.setName("팀 2");
//		
//		em.persist(t1);
//		em.persist(t2);
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams (name) values (?)
		
		// #4. Team 객체 2개, User 객체 2개 persist
//		Team t1 = new Team();
//		t1.setName("팀 1");
//		
//		Team t2 = new Team();
//		t2.setName("팀 2");
//		
//		User u1 = new User();	
//		u1.setName("김도영");
//		
//		User u2 = new User();
//		u2.setName("나성범");
//		
//		em.persist(u1);
//		em.persist(u2);
//		em.persist(t1);
//		em.persist(t2);
	
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams (name) values (?)
		
		// #5. Team에 User 객체 2개 연결, 모두 persist
//		Team t1 = new Team();
//		t1.setName("팀 1");
//		
//		Team t2 = new Team();
//		t2.setName("팀 2");
//		
//		User u1 = new User();	
//		u1.setName("김도영");
//		
//		User u2 = new User();
//		u2.setName("이정후");
//		
//		// 연결
//		t1.setUsers(List.of(u1,u2));
//		t2.setUsers(List.of(u2));
//		
//		em.persist(t1);
//		em.persist(t2);
//		
//		em.persist(u1);
//		em.persist(u2);
		
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
		
		// #6. Team에 User 객체 2개만 연결, Team만 persist
//		Team t1 = new Team();
//		t1.setName("팀 1");
//		
//		Team t2 = new Team();
//		t2.setName("팀 2");
//		
//		User u1 = new User();	
//		u1.setName("김도영");
//		
//		User u2 = new User();
//		u2.setName("이정후");
//		
//		// 연결
//		t1.setUsers(List.of(u1,u2));
//		t2.setUsers(List.of(u2));
//		
//		em.persist(t1);
//		em.persist(t2);
//		persistent instance references an unsaved transient instance of 'entity.User'
		
		// #7. Team에 User 객체 2개만 연결, Team만 persist, Team에 CascadeType.PERSIST
//		Team t1 = new Team();
//		t1.setName("팀 1");
//		
//		Team t2 = new Team();
//		t2.setName("팀 2");
//		
//		User u1 = new User();	
//		u1.setName("김도영");
//		
//		User u2 = new User();
//		u2.setName("이정후");
//		
//		// 연결
//		t1.setUsers(List.of(u1,u2));
//		t2.setUsers(List.of(u2));
//		
//		em.persist(t1);
//		em.persist(t2);
		
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
		
		// #8 User에 Team 객체 2개 연결, Team,user 모두 persist
		// cascadeType default
//		Team t1 = new Team();
//		t1.setName("팀 1");
//		
//		Team t2 = new Team();
//		t2.setName("팀 2");
//		
//		User u1 = new User();	
//		u1.setName("김도영");
//		
//		
//		// 연결
//		u1.setTeams(List.of(t1,t2));
//		
//		em.persist(t1);
//		em.persist(t2);	
//		em.persist(u1);
		
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into users (name) values (?)
			
//		#9. Team 과 User 객체 상호 연결, Team, User 모두 persist ( cascadeType=default )
		// 객체 생성 및 연결
		Team t1 = new Team();
		t1.setName("팀 1");

		Team t2 = new Team();
		t2.setName("팀 2");

		User u1 = new User();
		u1.setName("김도영");

		u1.setTeams(List.of(t1, t2));

		t1.setUsers(List.of(u1));
		t2.setUsers(List.of(u1));

		em.persist(t1);  
		em.persist(t2);  
		em.persist(u1);  
		
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into teams (name) values (?)
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
		
		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
