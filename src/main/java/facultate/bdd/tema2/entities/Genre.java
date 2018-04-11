package facultate.bdd.tema2.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** TODO: Transform this class into a JPA entity **/
@Entity
@Table(name="\"Genres\"")
public class Genre {
	
	//properties
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	@ManyToMany(fetch=FetchType.LAZY,mappedBy = "genres",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE})
/*	@JoinTable(name = "Book", joinColumns = { @JoinColumn(name = "id") },
			inverseJoinColumns = { @JoinColumn(name = "id") })*/
	private List<Book> books = new ArrayList<Book>(); //LAZY
	
	//constructors
	public Genre() {}
	
	public Genre(String name) {
		this.name = name;
	}

	//getters and setters
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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	
	
}
