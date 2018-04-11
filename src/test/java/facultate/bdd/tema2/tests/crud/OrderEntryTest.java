package facultate.bdd.tema2.tests.crud;

import facultate.bdd.tema2.dao.interfaces.OrderDAO;
import facultate.bdd.tema2.dao.interfaces.OrderEntryDAO;
import facultate.bdd.tema2.entities.Book;
import facultate.bdd.tema2.entities.Buyer;
import facultate.bdd.tema2.entities.Order;
import facultate.bdd.tema2.entities.OrderEntry;
import facultate.bdd.tema2.util.EntityDAOImplFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderEntryTest {

	private static OrderEntryDAO orderEntryDao = EntityDAOImplFactory
			.createNewOrderEntryDAOImpl(BookTest.PERSISTANCE_UNIT_NAME);
	private static OrderDAO orderDao = EntityDAOImplFactory.createNewOrderDAOImpl(BookTest.PERSISTANCE_UNIT_NAME);

	@Test
	public void createNewOrderEntryReadAllOrderEntriesTest() {
		Buyer robinJr = new Buyer("Robin Doe Junior", 28);
		Buyer robinSr = new Buyer("Robin Doe Senior", 42);

		Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 29.99);
		Order order1 = new Order();
		Order order2 = new Order();

		order1.setBuyer(robinJr);
		order2.setBuyer(robinSr);

		OrderEntry entry1 = new OrderEntry(order1, book1);
		entry1.setQuantity(2);
		OrderEntry entry2 = new OrderEntry(order2, book1);
		entry2.setQuantity(3);

		orderEntryDao.createOrUpdate(entry1);
		orderEntryDao.createOrUpdate(entry2);

		List<OrderEntry> orderEntries = orderEntryDao.readAll();

		assertFalse(orderEntries.isEmpty());
		orderEntries.sort((o1, o2) -> o2.getQuantity().compareTo(o1.getQuantity()));

		assertEquals("The Lord of the Rings", orderEntries.get(0).getBook().getName());
		assertEquals((Integer) 3, orderEntries.get(0).getQuantity());

		assertEquals("J.R.R. Tolkien", orderEntries.get(1).getBook().getAuthor());
		assertEquals((Integer) 2, orderEntries.get(1).getQuantity());

	}

	@Test
	public void updateOrderEntryTest() {
		Book book1 = new Book("The Harry Potter Collection", "J.K. Rowling", 29.99);
		Order order1 = new Order();

		OrderEntry entry1 = new OrderEntry(order1, book1);
		entry1.setQuantity(42);

		orderEntryDao.createOrUpdate(entry1);

		List<OrderEntry> entries = orderEntryDao.readAll();
		assertEquals(1, entries.size());
		assertNotNull(entries.get(0));

		OrderEntry entryToUpdate = entries.get(0);
		entryToUpdate.setQuantity(1);
		orderEntryDao.update(entryToUpdate);

		entries = orderEntryDao.readAll();
		assertEquals(1, entries.size());
		assertNotNull(entries.get(0));

		OrderEntry updatedEntry = entries.get(0);
		assertEquals("The Harry Potter Collection", updatedEntry.getBook().getName());
		assertEquals((Integer) 1, updatedEntry.getQuantity());
	}

	@Test
	public void deleteOrderEntryTest() {
		orderDao.deleteAll();
		Book book1 = new Book("The Hobbit", "J.R.R. Tolkien", 29.99);
		Book book2 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 29.99);

		Order order1 = new Order();

		OrderEntry orderEntry1 = new OrderEntry(order1, book1);
		orderEntry1.setQuantity(1);
		OrderEntry orderEntry2 = new OrderEntry(order1, book2);
		orderEntry2.setQuantity(1);

		orderEntryDao.createOrUpdate(orderEntry1);
		orderEntryDao.createOrUpdate(orderEntry2);

		List<OrderEntry> entries = orderEntryDao.readAll();
		assertEquals(2, entries.size());
		entries.sort((o1, o2) -> o1.getBook().getName().compareTo(o2.getBook().getName()));

		orderEntryDao.delete(entries.get(0));
		entries = orderEntryDao.readAll();
		assertEquals(1, entries.size());
		assertNotNull(entries.get(0));
		assertEquals("The Lord of the Rings", entries.get(0).getBook().getName());

		List<Order> orders = orderDao.readAll();
		assertEquals(1, orders.size());
	}

	@Test
	public void deleteAllOrderEntriesTest() {
		Book book1 = new Book("The Hobbit", "J.R.R. Tolkien", 29.99);
		Order order1 = new Order();

		orderEntryDao.createOrUpdate(new OrderEntry(order1, book1));
		orderEntryDao.createOrUpdate(new OrderEntry(order1, book1));
		orderEntryDao.createOrUpdate(new OrderEntry(order1, book1));

		assertFalse(orderEntryDao.readAll().isEmpty());

		orderEntryDao.deleteAll();

		boolean t =orderEntryDao.readAll().isEmpty();
/*
		assertTrue(t);
*/

	}

	@Before
	public void cleanTable() {
		orderEntryDao.deleteAll();
	}

	@AfterClass
	public static void cleanup() {
		orderEntryDao.close();
	}
}
