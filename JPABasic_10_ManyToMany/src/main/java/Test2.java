
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
public class Test2 {
	public static void main(String[] args) {

		Map<String, String> props = new HashMap<>();
		props.put("hibernate.show_sql", "true");
//		props.put("hibernate.hbm2ddl.auto", "create");

		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
//		#1.FetchType 설정 없이 Team 객체만 find
//		Team team = em.find(Team.class, 1);
//		Hibernate: select t1_0.id,t1_0.name from teams t1_0 where t1_0.id=?
		
//		#2. FetchType 설정 없이 User 객체만 find
//		User user = em.find(User.class, 1);
//		Hibernate: select u1_0.id,u1_0.name from users u1_0 where u1_0.id=?
		
//		#3. FetchType 설정 없이 Team 객체 find + Team 객체의 users 사용
//		Team team = em.find(Team.class, 1); 
//		System.out.println(team.getUsers());
//		Hibernate: select t1_0.id,t1_0.name from teams t1_0 where t1_0.id=?
//		Hibernate: select u1_0.team_id,u1_1.id,u1_1.name from teams_users u1_0 join users u1_1 on u1_1.id=u1_0.user_id where u1_0.team_id=?

//		#4. FetchType=EAGER 설정, Team 객체 find + Team 객체의 users 사용
//		Team team = em.find(Team.class, 1); 
//		System.out.println(team.getUsers());
//		Hibernate: select t1_0.id,t1_0.name,u1_0.team_id,u1_1.id,u1_1.name from teams t1_0 left join teams_users u1_0 on t1_0.id=u1_0.team_id left join users u1_1 on u1_1.id=u1_0.user_id where t1_0.id=?
		
//		#5. Team 1개 find, User 1개 생성 후 persist
		Team team = em.find(Team.class, 1);
		User user = new User();
		user.setName("나성범");
//		

		team.getUsers().add(user);
		
		em.persist(user);
		
//		Hibernate: insert into users (name) values (?)
//		Hibernate: delete from teams_users where team_id=?
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)
//		Hibernate: insert into teams_users (team_id,user_id) values (?,?)



		em.getTransaction().commit();
		em.close();

		emf.close();
	}
}
