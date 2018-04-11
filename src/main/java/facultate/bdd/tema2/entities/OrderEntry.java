package facultate.bdd.tema2.entities;

import javax.persistence.*;

/** TODO: Transform this class into a JPA entity **/
@Entity
@Table(name="\"OrderEntries\"")
public class OrderEntry {
	//properties
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
/*
	@JoinTable(name = "Order", joinColumns = { @JoinColumn(name = "id")})
*/
	private Order order; //LAZY
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.EAGER)
/*
	@JoinTable(name = "Book", joinColumns = { @JoinColumn(name = "id")})
*/
	private Book book; //EAGER
	private Integer quantity;
	
	//constructors
	public OrderEntry() {
		super();
	}
	
	public OrderEntry(Order order, Book book) {
		this.order = order;
		this.book = book;
	}

	//getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	

	
}
