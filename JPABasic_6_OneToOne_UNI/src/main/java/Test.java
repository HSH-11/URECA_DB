
import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Passport;
import entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// JPA에서는 영속성 상태에 따라 객체를 관리하기에 영속 상태에 있을 때만 DB에 저장 조회 가능
// 연관된 객체들도 영속화를 해줘야 한다.
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
// 		passport가 영속화 되지 않은 상태에서 person 객체에 할당되어 저장하므로 오류 발생

//		em.persist(passport);
//		Hibernate: insert into Passport (number) values (?)
//		정상적으로 insert

		// 3. 객체 연결, person, passport persist
//		person.setPassport(passport);
//		em.persist(person);
//		em.persist(passport);

//		Hibernate: insert into Person (name,passport) values (?,?)
//		Hibernate: insert into Passport (number) values (?)
//		Hibernate: update Person set name=?,passport=? where id=?
		
//		Hibernate는 두 번째 persist()가 발생할 때, Hibernate는 passport 객체의 ID를 자동으로 Person 객체에 할당하고, 
//		이후 Person 객체를 업데이트할 때 이 값을 외래 키로 사용한다.
		
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
//
//		em.persist(person);
		
//		Hibernate: insert into Passport (number) values (?)
//		Hibernate: insert into Person (name,passport) values (?,?)

		em.getTransaction().commit();

		em.close();

		emf.close();
	}
}
