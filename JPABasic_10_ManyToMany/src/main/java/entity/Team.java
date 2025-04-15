package entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

// ManyToMany Owing Entity, 양방향
// 관계 테이블이 생긴다.
// Owing Entity
@Entity
@Table(name = "teams")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@ManyToMany
	@JoinTable(
			name="teams_users",
			joinColumns=@JoinColumn(name="team_id"),
			inverseJoinColumns = @JoinColumn(name="user_id")
			)
	private List<User> users;
	
//	@ManyToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(
//			name="teams_users",
//			joinColumns=@JoinColumn(name="team_id"),
//			inverseJoinColumns = @JoinColumn(name="user_id")
//			)
//	private List<User> users;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(
//			name="teams_users",
//			joinColumns=@JoinColumn(name="team_id"),
//			inverseJoinColumns = @JoinColumn(name="user_id")
//			)
//	private List<User> users;

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + "]";
	}

}
