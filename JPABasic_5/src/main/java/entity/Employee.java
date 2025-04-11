package entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @GeneratedValue(strategy = GenerationType.AUTO) // hibernate에 위임. (hibernate가 DB에 맞게 알아서 처리 - mysql:sequence)
// GenerationType.SEQUENCE, GenerationType.TABLE은 별도의 TABLE 또는 SEQUENCE를 생성한다. 
// GenerationType.UUID는 id를 String으로 줘야함
// private String id; bsgardgsg-afbhafaf-ad 이런식으로 생성됨


@Entity
@Table(name = "employee")
public class Employee {
    
	// Key 생성 방법
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
 
    private int id;
    private String name;
    private String address;
    
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
    }
}