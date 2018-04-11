package facultate.bdd.tema2.tests.crud;

import facultate.bdd.tema2.dao.interfaces.GenreDAO;
import facultate.bdd.tema2.entities.Genre;
import facultate.bdd.tema2.util.EntityDAOImplFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GenreTest {
	private static GenreDAO genreDao = EntityDAOImplFactory.createNewGenreDAOImpl(BookTest.PERSISTANCE_UNIT_NAME);

	@Test
	public void readAllGenresCreateGenreTest() {
		List<Genre> genres = genreDao.readAll();
		assertTrue(genres.isEmpty());

		Genre newGenre1 = new Genre("Drama");
		Genre newGenre2 = new Genre("Romance");

		genreDao.createOrUpdate(newGenre1);
		genreDao.createOrUpdate(newGenre2);

		genres = genreDao.readAll();

		assertEquals(2, genres.size());
		assertNotNull(genres.get(0));
		assertNotNull(genres.get(1));

		genres.sort((g1, g2) -> g2.getName().compareTo(g1.getName()));

		assertEquals("Romance", genres.get(0).getName());
		assertEquals("Drama", genres.get(1).getName());
	}

	@Test
	public void updateGenreTest() {
		Genre genre1 = new Genre("Drama");
		Genre badGenre = new Genre("Rmnc");

		assertTrue(genreDao.readAll().isEmpty());

		genreDao.createOrUpdate(genre1);
		genreDao.createOrUpdate(badGenre);

		List<Genre> genres = genreDao.readAll();
		genres.sort((g1, g2) -> g2.getName().compareTo(g1.getName()));

		Genre updatedGenre = genres.get(0);

		assertNotNull(updatedGenre);
		assertEquals("Rmnc", updatedGenre.getName());

		updatedGenre.setName("Romance");
		genreDao.createOrUpdate(updatedGenre);

		genres = genreDao.readAll();
		genres.sort((g1, g2) -> g1.getName().compareTo(g2.getName()));

		assertEquals(2, genres.size());
		assertNotNull(genres.get(1));
		assertEquals("Romance", genres.get(1).getName());

	}

	@Test
	public void deleteGenreTest() {
		assertTrue(genreDao.readAll().isEmpty());

		Genre newGenre1 = new Genre("Tragedy");
		Genre newGenre2 = new Genre("Drama");

		genreDao.createOrUpdate(newGenre1);
		genreDao.createOrUpdate(newGenre2);

		List<Genre> genres = genreDao.readAll();
		assertEquals(2, genres.size());
		assertNotNull(genres.get(0));
		assertNotNull(genres.get(1));

		genres.sort((g1, g2) -> g1.getName().compareTo(g2.getName()));
		assertEquals("Drama", genres.get(0).getName());
		assertEquals("Tragedy", genres.get(1).getName());

		genreDao.delete(genres.get(1));

		genres = genreDao.readAll();
		assertEquals(1, genres.size());
		assertNotNull(genres.get(0));

		assertEquals("Drama", genres.get(0).getName());

	}

	@Test
	public void deleteAllGenresTest() {

		genreDao.createOrUpdate(new Genre("Science Fiction"));
		genreDao.createOrUpdate(new Genre("Romance"));
		genreDao.createOrUpdate(new Genre("Drama"));

		assertFalse(genreDao.readAll().isEmpty());

		genreDao.deleteAll();

		assertTrue(genreDao.readAll().isEmpty());
	}

	@Before
	public void cleanTable() {
		genreDao.deleteAll();
	}

	@AfterClass
	public static void cleanup() {
		genreDao.close();
	}
}
