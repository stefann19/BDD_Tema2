package facultate.bdd.tema2.tests.crud;

import facultate.bdd.tema2.dao.interfaces.BuyerDAO;
import facultate.bdd.tema2.dao.interfaces.OrderDAO;
import facultate.bdd.tema2.entities.Buyer;
import facultate.bdd.tema2.entities.Order;
import facultate.bdd.tema2.util.EntityDAOImplFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BuyerTest {

	private static BuyerDAO buyerDao = EntityDAOImplFactory.createNewBuyerDAOImpl(BookTest.PERSISTANCE_UNIT_NAME);
	private static OrderDAO orderDao = EntityDAOImplFactory.createNewOrderDAOImpl(BookTest.PERSISTANCE_UNIT_NAME);

	@Test
	public void readAllBuyersCreateBuyerTest() {
		List<Buyer> buyers = buyerDao.readAll();
		assertTrue(buyers.isEmpty());

		Buyer buyer1 = new Buyer("Jane Doe", 23);
		Buyer buyer2 = new Buyer("John Doe", 24);
		Buyer buyer3 = new Buyer("Robin Doe", 25);

		buyerDao.createOrUpdate(buyer1);
		buyerDao.createOrUpdate(buyer2);
		buyerDao.createOrUpdate(buyer3);

		buyers = buyerDao.readAll();

		assertEquals(3, buyers.size());
		assertNotNull(buyers.get(0));
		assertNotNull(buyers.get(1));
		assertNotNull(buyers.get(2));

		buyers.sort((b1, b2) -> b2.getName().compareTo(b1.getName()));

		assertNotNull(buyers.get(0).getId());
		assertEquals("Robin Doe", buyers.get(0).getName());
		assertEquals((Integer) 25, buyers.get(0).getAge());

		assertNotNull(buyers.get(1).getId());
		assertEquals("John Doe", buyers.get(1).getName());
		assertEquals((Integer) 24, buyers.get(1).getAge());

		assertNotNull(buyers.get(2).getId());
		assertEquals("Jane Doe", buyers.get(2).getName());
		assertEquals((Integer) 23, buyers.get(2).getAge());

	}

	@Test
	public void updateBuyerTest() {
		Buyer buyer1 = new Buyer("Robin Doe", 23);

		buyerDao.createOrUpdate(buyer1);

		List<Buyer> buyers = buyerDao.readAll();
		assertEquals(1, buyers.size());

		Buyer updatedBuyer = buyers.get(0);
		updatedBuyer.setName("Robin Doe Junior");
		updatedBuyer.setAge(18);

		Order newOrder = new Order();
		newOrder.setBuyer(updatedBuyer);
		updatedBuyer.getOrders().add(newOrder);

		orderDao.createOrUpdate(newOrder);

		buyerDao.update(updatedBuyer);

		buyers = buyerDao.readAll();
		assertEquals(1, buyers.size());
		assertNotNull(buyers.get(0));
		assertEquals("Robin Doe Junior", buyers.get(0).getName());
		assertEquals((Integer) 18, buyers.get(0).getAge());

		assertEquals(1, buyers.get(0).getOrders().size());
		assertNotNull(buyers.get(0).getOrders().get(0));

	}

	@Test
	public void deleteBuyerTest() {
		assertTrue(buyerDao.readAll().isEmpty());

		Buyer newBuyer1 = new Buyer("Robin Doe Junior", 23);
		Buyer newBuyer2 = new Buyer("Robin Doe Senior", 58);

		buyerDao.createOrUpdate(newBuyer1);
		buyerDao.createOrUpdate(newBuyer2);

		List<Buyer> buyers = buyerDao.readAll();
		assertEquals(2, buyers.size());
		assertNotNull(buyers.get(0));
		assertNotNull(buyers.get(1));

		buyers.sort((b1, b2) -> b1.getName().compareTo(b2.getName()));
		assertEquals("Robin Doe Junior", buyers.get(0).getName());
		assertEquals("Robin Doe Senior", buyers.get(1).getName());

		buyerDao.delete(buyers.get(1));

		buyers = buyerDao.readAll();
		assertEquals(1, buyers.size());
		assertNotNull(buyers.get(0));

		assertEquals("Robin Doe Junior", buyers.get(0).getName());
		assertEquals((Integer) 23, buyers.get(0).getAge());

	}

	@Test
	public void deleteAllBuyersTest() {
		buyerDao.createOrUpdate(new Buyer("Robin Unknown", 31));
		buyerDao.createOrUpdate(new Buyer("Robin Lessknown", 41));
		buyerDao.createOrUpdate(new Buyer("Robin Nobody", 51));

		buyerDao.deleteAll();

		List<Buyer> buyers = buyerDao.readAll();

		assertTrue(buyers.isEmpty());
	}

	@Before
	public void cleanTable() {
		buyerDao.deleteAll();
	}

	@AfterClass
	public static void cleanup() {
		buyerDao.close();
	}
}
