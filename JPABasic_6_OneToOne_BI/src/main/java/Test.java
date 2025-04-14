import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Passport;
import entity.Person;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

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

		Person person = new Person();
		person.setName("홍길동");

		Passport passport = new Passport();
		passport.setNumber("KOR-1111");

		// #1. 각각 따로 persist하면 오류 없이 insert 된다.
//		em.persist(person);
//		Hibernate: insert into Person (name,passport) values (?,?)
		// passport는 null

//		em.persist(passport);
//		Hibernate: insert into Passport (number) values (?)

		// #2. 객체 연결, person만 persist한 경우
//		person.setPassport(passport);
//		em.persist(person);
//		persistent instance references an unsaved transient instance of 'entity.Passport' (save the transient instance before flushing)
// 		passport 영속화 되지 않은 상태 => 오류 발생

//		em.persist(passport);
//		Hibernate: insert into Passport (number) values (?)
//		정상적으로 insert

		// 3. 객체 연결, person, passport persist
//		person.setPassport(passport);
//		em.persist(person);
//		em.persist(passport);

//		Hibernate: insert into Person (name,passport) values (?,?)
//		Hibernate: insert into Passport (number) values (?)
//		Passport가 나중에 들어왔기 때문에 Person의 FK를 update 해줘야 함
//		Hibernate: update Person set name=?,passport=? where id=?
//		Person이 먼저 insert되는 과정에서 Passport의 id 값을 모르므로
//		Passport가 insert 되는 과정에서 획득한 AI key 값을 이용해서 다시 한번 update 수행
//		insert 과정에서 AI key를 반환하도록 수행(JPA)

		// 4. 객체 연결, passport-> person persist
//		person.setPassport(passport);
//
//		em.persist(passport);
//		em.persist(person);

//		Hibernate: insert into Passport (number) values (?)
//		Hibernate: insert into Person (name,passport) values (?,?)

		// 5. 객체 연결, Person의 @OneToOne에 cascade=CascadeType.PERSIST 추가
//		person.setPassport(passport);
//		em.persist(person);

//		Hibernate: insert into Passport (number) values (?)
//		Hibernate: insert into Person (name,passport) values (?,?)

		// 6. 객체 연결, Person의 @OneToOne에 cascade=CascadeType.PERSIST 추가
//		person.setPassport(passport);
//		em.persist(passport);
	
		// 7. 객체 연결 양방향, @OneToOne은 초기값
		person.setPassport(passport);
		passport.setPerson(person);
		
		// 7-1
//		em.persist(person);
//		persistent instance references an unsaved transient instance of 'entity.Passport' (save the transient instance before flushing)
		
		// 7-2
//		em.persist(passport);
		// 양방향일 경우, passport만 persist 하지 못한다.(단방향일 경우, 가능)
		
		// 7-3
		// 	@OneToOne(mappedBy = "passport", fetch= FetchType.LAZY, cascade = CascadeType.PERSIST)
		em.persist(passport);
		em.getTransaction().commit();

		em.close();

		emf.close();
	}
}
