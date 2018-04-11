package facultate.bdd.tema2.tests.crud;

import facultate.bdd.tema2.dao.interfaces.BookDAO;
import facultate.bdd.tema2.entities.Book;
import facultate.bdd.tema2.entities.Genre;
import facultate.bdd.tema2.util.EntityDAOImplFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BookTest {

	public static final String PERSISTANCE_UNIT_NAME = "bookstore";

	private static BookDAO bookDao = EntityDAOImplFactory.createNewBookDAOImpl(PERSISTANCE_UNIT_NAME);

	@Test
	public void readAllBooksCreateBookTest() {
		List<Book> books = bookDao.readAll();
		assertTrue(books.isEmpty());

		Genre genre1 = new Genre("Drama");
		Genre genre2 = new Genre("Romance");
		Genre genre3 = new Genre("Tragedy");

		Book newBook1 = new Book("Anna Karenina", "Leo Tolstoy", 19.99);
		Book newBook2 = new Book("Madame Bovary", "Gustave Flaubert", 23.99);

		newBook1.getGenres().add(genre1);
		newBook1.getGenres().add(genre2);
		newBook2.getGenres().add(genre1);
		newBook2.getGenres().add(genre3);

		newBook1 = bookDao.createOrUpdate(newBook1);
		newBook2 = bookDao.createOrUpdate(newBook2);

		books = bookDao.readAll();

		assertEquals(2, books.size());
		assertNotNull(books.get(0));
		assertNotNull(books.get(1));

		books.sort((b1, b2) -> b1.getAuthor().compareTo(b2.getAuthor()));

		assertFalse(books.get(0).getGenres().isEmpty());
		assertEquals(2, books.get(0).getGenres().size());
		assertFalse(books.get(1).getGenres().isEmpty());
		assertEquals(2, books.get(1).getGenres().size());

		assertNotNull(books.get(0).getGenres().get(0));
		assertNotNull(books.get(0).getGenres().get(1));
		assertNotNull(books.get(1).getGenres().get(0));
		assertNotNull(books.get(1).getGenres().get(1));

		books.get(0).getGenres().sort((g1, g2) -> g2.getName().compareTo(g1.getName()));
		books.get(1).getGenres().sort((g1, g2) -> g2.getName().compareTo(g1.getName()));

		assertNotNull(books.get(0).getId());
		assertEquals("Madame Bovary", books.get(0).getName());
		assertEquals("Gustave Flaubert", books.get(0).getAuthor());
		assertEquals((Double) 23.99, books.get(0).getPrice());
		assertEquals("Tragedy", books.get(0).getGenres().get(0).getName());
		assertEquals("Drama", books.get(0).getGenres().get(1).getName());

		assertNotNull(books.get(1).getId());
		assertEquals("Anna Karenina", books.get(1).getName());
		assertEquals("Leo Tolstoy", books.get(1).getAuthor());
		assertEquals((Double) 19.99, books.get(1).getPrice());
		assertEquals("Romance", books.get(1).getGenres().get(0).getName());
		assertEquals("Drama", books.get(1).getGenres().get(1).getName());
	}

	@Test
	public void updateBookTest() {
		Genre genre1 = new Genre("Drama");
		Genre genre2 = new Genre("Romance");
		Genre genre3 = new Genre("ScienceFiction");

		Book badBook = new Book("Ana Carennina", "Leeo Tolsttoi", 59.99);

		badBook.getGenres().add(genre1);
		badBook.getGenres().add(genre2);
		badBook.getGenres().add(genre3);

		bookDao.createOrUpdate(badBook);

		List<Book> books = bookDao.readAll();

		assertEquals(1, books.size());
		Book updatedBook = books.get(0);

		assertNotNull(updatedBook);
		assertFalse(updatedBook.getGenres().isEmpty());
		assertEquals(3, updatedBook.getGenres().size());
		assertNotNull(updatedBook.getGenres().get(0));
		assertNotNull(updatedBook.getGenres().get(1));
		assertNotNull(updatedBook.getGenres().get(2));

		updatedBook.getGenres().sort((g1, g2) -> g2.getName().compareTo(g1.getName()));
		assertEquals("Ana Carennina", updatedBook.getName());
		assertEquals("Leeo Tolsttoi", updatedBook.getAuthor());
		assertEquals((Double) 59.99, updatedBook.getPrice());
		assertEquals("ScienceFiction", updatedBook.getGenres().get(0).getName());
		assertEquals("Romance", updatedBook.getGenres().get(1).getName());
		assertEquals("Drama", updatedBook.getGenres().get(2).getName());

		updatedBook.setAuthor("Leo Tolstoy");
		updatedBook.setName("Anna Karenina");
		updatedBook.setPrice(19.99);
		updatedBook.getGenres().remove(0);

		bookDao.update(updatedBook);

		books = bookDao.readAll();

		assertEquals(1, books.size());
		updatedBook = books.get(0);

		assertNotNull(updatedBook);
		assertFalse(updatedBook.getGenres().isEmpty());
		assertEquals(2, updatedBook.getGenres().size());
		assertNotNull(updatedBook.getGenres().get(0));
		assertNotNull(updatedBook.getGenres().get(1));

		updatedBook.getGenres().sort((g1, g2) -> g2.getName().compareTo(g1.getName()));
		assertEquals("Anna Karenina", updatedBook.getName());
		assertEquals("Leo Tolstoy", updatedBook.getAuthor());
		assertEquals((Double) 19.99, updatedBook.getPrice());
		assertEquals("Romance", updatedBook.getGenres().get(0).getName());
		assertEquals("Drama", updatedBook.getGenres().get(1).getName());
	}

	@Test
	public void deleteBookTest() {
		assertTrue(bookDao.readAll().isEmpty());

		Book newBook1 = new Book("Anna Karenina", "Leo Tolstoy", 19.99);
		Book newBook2 = new Book("Madame Bovary", "Gustave Flaubert", 23.99);

		bookDao.createOrUpdate(newBook1);
		bookDao.createOrUpdate(newBook2);

		List<Book> books = bookDao.readAll();
		assertEquals(2, books.size());
		assertNotNull(books.get(0));
		assertNotNull(books.get(1));

		books.sort((b1, b2) -> b2.getName().compareTo(b1.getName()));
		assertEquals("Madame Bovary", books.get(0).getName());
		assertEquals("Anna Karenina", books.get(1).getName());

		bookDao.delete(books.get(0));

		books = bookDao.readAll();
		assertEquals(1, books.size());
		assertNotNull(books.get(0));

		assertEquals("Anna Karenina", books.get(0).getName());
		assertEquals("Leo Tolstoy", books.get(0).getAuthor());
		assertEquals((Double) 19.99, books.get(0).getPrice());

	}

	@Test
	public void deleteAllBooksTest() {
		Book newBook1 = new Book("Anna Karenina", "Leo Tolstoy", 19.99);
		Book newBook2 = new Book("Madame Bovary", "Gustave Flaubert", 23.99);
		Book newBook3 = new Book("Don Quixote", "Miguel de Cervantes", 39.99);

		bookDao.createOrUpdate(newBook1);
		bookDao.createOrUpdate(newBook2);
		bookDao.createOrUpdate(newBook3);

		assertFalse(bookDao.readAll().isEmpty());

		bookDao.deleteAll();

		assertTrue(bookDao.readAll().isEmpty());

	}

	@Before
	public void cleanTable() {
		bookDao.deleteAll();
	}

	@AfterClass
	public static void cleanup() {
		bookDao.close();
	}

}
