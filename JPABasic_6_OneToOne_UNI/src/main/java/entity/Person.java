package entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

// Person -> passport(단방향)
@Entity
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	// cascade 설정 전(기본설정은 연관 관계 table은 영속화를 자동으로 해주지 않는다)
//	@OneToOne
//	@JoinColumn(name= "passport")
//	private Passport passport;
	
//	@OneToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name= "passport")
//	private Passport passport;
	// 연관 관계 자동으로 영속화
	// fetch 설정 (LAZY: 연관관계의 데이터를 즉시가 아닌 필요한 시점에 가져온다)
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name= "passport")
	private Passport passport;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", passport=" + passport + "]";
	}
	
	
	
}
