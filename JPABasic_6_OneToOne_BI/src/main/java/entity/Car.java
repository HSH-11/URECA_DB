package entity;

import jakarta.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;

    @OneToOne(mappedBy = "car", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Owner owner;

    public Car() {}

    public Car(String model) {
        this.model = model;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Owner getOwner() {
        return owner;
    }

	@Override
	public String toString() {
		return "Car [id=" + id + ", model=" + model + ", owner=" + owner + "]";
	}
    
    
}
