
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

		// #1. find Person
//		Person person = em.find(Person.class,1);
//		Hibernate: select p1_0.id,p1_0.name,p2_0.id,p2_0.number from Person p1_0 left join Passport p2_0 on p2_0.id=p1_0.passport where p1_0.id=?
		// OneToOne의 기본 fetch option이 EAGER (즉시 로딩)이므로 연관관계에 있는 Passport도 join으로 함께 가지고
		// 온다.
		// Person 객체의 출력 코드에서 passport 객체를 사용하지만, 다시 select 수행 X
//		System.out.println(person);

		// #2. find Passport
//		Passport passport = em.find(Passport.class, 1);
//		Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
		// Passport 객체만 select

		// #3. @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//		Person person = em.find(Person.class, 1);
//		Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.id=?
//		System.out.println(person); // person의 출력에 Passport 값을 읽어와야 해서 추가 조회 쿼리 실행
//		Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
//		Person [id=1, name=홍길동, passport=Passport [id=1, number=KOR-1111]]

		em.getTransaction().commit();

		em.close();

		emf.close();
	}
}
