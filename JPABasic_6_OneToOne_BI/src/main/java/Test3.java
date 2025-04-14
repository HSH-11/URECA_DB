import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Car;
import entity.Owner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test3 {
    public static void main(String[] args) {

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");

        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Car car = new Car("Audi");
        Owner owner = new Owner("HSH");

        car.setOwner(owner);
        owner.setCar(car);
        
        em.persist(owner);
        em.persist(car);   
        
//        System.out.println(owner); 스택 오버플로우

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
