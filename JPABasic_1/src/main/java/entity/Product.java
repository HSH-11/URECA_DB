package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity //이 클래스가 자동으로 데이터베이스의 테이블과 연결됨
public class Product {
	@Id //기본 키
	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
