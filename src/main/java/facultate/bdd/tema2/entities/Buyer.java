package facultate.bdd.tema2.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** TODO: Transform this class into a JPA entity **/
@Entity
@Table(name="\"Buyers\"")
public class Buyer {

	// properties
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private Integer age;
	@OneToMany(fetch = FetchType.EAGER ,mappedBy = "buyer",orphanRemoval = true)
	private List<Order> orders = new ArrayList<Order>(); // EAGER

	// constructors
	public Buyer() {
	}

	public Buyer(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	// getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	
	
	
}
