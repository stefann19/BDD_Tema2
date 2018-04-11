package facultate.bdd.tema2.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** TODO: Transform this class into a JPA entity **/
@Entity
@Table(name="\"Orders\"")
public class Order {
	
	//properties
	@Id
	@GeneratedValue
	private Integer id;
	private Date date;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
/*
	@JoinTable(name ="Buyer",joinColumns = {@JoinColumn(name="id")})//SUNT PROST
*/
	@JoinColumn(name="\"buyer_id\"")
	private Buyer buyer; //EAGER
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER , mappedBy = "order", orphanRemoval = true)
/*
	@JoinTable(name ="OrderEntry",joinColumns = {@JoinColumn(name="id")})
*/
	private List<OrderEntry> orderEntries = new ArrayList<OrderEntry>(); //EAGER
	
	//constructors
	public Order() {}

	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public List<OrderEntry> getOrderEntries() {
		return orderEntries;
	}

	public void setOrderEntries(List<OrderEntry> orderEntries) {
		this.orderEntries = orderEntries;
	}
	
	
	
	
}
