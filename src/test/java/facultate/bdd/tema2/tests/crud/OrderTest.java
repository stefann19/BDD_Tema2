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

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class OrderTest {
	private static OrderDAO orderDao = EntityDAOImplFactory.createNewOrderDAOImpl(BookTest.PERSISTANCE_UNIT_NAME);
	private static OrderEntryDAO orderEntryDao = EntityDAOImplFactory
			.createNewOrderEntryDAOImpl(BookTest.PERSISTANCE_UNIT_NAME);

	@Test
	public void createOrderReadAllOrdersTest() {
		assertTrue(orderDao.readAll().isEmpty());

		Book newBook1 = new Book("Anna Karenina", "Leo Tolstoy", 19.99);
		Book newBook2 = new Book("Madame Bovary", "Gustave Flaubert", 23.99);
		Book newBook3 = new Book("Don Quixote", "Miguel de Cervantes", 39.99);

		Buyer robinJr = new Buyer("Robin Doe Junior", 28);
		Buyer robinSr = new Buyer("Robin Doe Senior", 42);

		Order newOrder1 = new Order();
		newOrder1.setDate((new Date(1521140001)));
		newOrder1.setBuyer(robinJr);
		newOrder1.getOrderEntries().add(new OrderEntry(newOrder1, newBook1));
		newOrder1.getOrderEntries().add(new OrderEntry(newOrder1, newBook2));

		Order newOrder2 = new Order();
		newOrder2.setDate((new Date(1521140022)));
		newOrder2.setBuyer(robinSr);
		newOrder2.getOrderEntries().add(new OrderEntry(newOrder2, newBook1));
		newOrder2.getOrderEntries().add(new OrderEntry(newOrder2, newBook2));
		newOrder2.getOrderEntries().add(new OrderEntry(newOrder2, newBook3));

		orderDao.createOrUpdate(newOrder1);
		orderDao.createOrUpdate(newOrder2);

		List<Order> orders = orderDao.readAll();
		assertEquals(2, orders.size());
		assertNotNull(orders.get(0));
		assertNotNull(orders.get(1));

		orders.sort((o1, o2) -> o1.getBuyer().getName().compareTo(o2.getBuyer().getName()));

		assertEquals("Robin Doe Junior", orders.get(0).getBuyer().getName());
		assertEquals(1521140001, orders.get(0).getDate().getTime());
		assertEquals(2, orders.get(0).getOrderEntries().size());

		assertEquals("Robin Doe Senior", orders.get(1).getBuyer().getName());
		assertEquals(1521140022, orders.get(1).getDate().getTime());
		assertEquals(3, orders.get(1).getOrderEntries().size());

	}

	@Test
	public void updateOrderTest() {
		Buyer robinJr = new Buyer("Robin Doe Junior", 28);
		Buyer robinSr = new Buyer("Robin Doe Senior", 42);
		Book newBook1 = new Book("Anna Karenina", "Leo Tolstoy", 19.99);

		Order initialOrder = new Order();
		initialOrder.setDate((new Date(1521140001)));
		initialOrder.setBuyer(robinJr);

		orderDao.createOrUpdate(initialOrder);

		List<Order> orders = orderDao.readAll();
		assertEquals(1, orders.size());
		assertNotNull(orders.get(0));

		Order updatedOrder = orders.get(0);
		assertEquals(1521140001, updatedOrder.getDate().getTime());
		assertEquals("Robin Doe Junior", updatedOrder.getBuyer().getName());

		updatedOrder.setDate(new Date(1521140022));
		updatedOrder.setBuyer(robinSr);
		updatedOrder.getOrderEntries().add(new OrderEntry(updatedOrder, newBook1));
		orderDao.update(updatedOrder);

		orders = orderDao.readAll();
		assertEquals(1, orders.size());
		assertNotNull(orders.get(0));
		assertEquals(1521140022, orders.get(0).getDate().getTime());
		assertEquals("Robin Doe Senior", orders.get(0).getBuyer().getName());

	}

	@Test
	public void deleteOrderTest() {
		assertTrue(orderDao.readAll().isEmpty());

		Buyer robinJr = new Buyer("Robin Doe Junior", 28);
		Buyer robinSr = new Buyer("Robin Doe Senior", 42);

		Order newOrder1 = new Order();
		newOrder1.setDate((new Date(1521140001)));
		newOrder1.setBuyer(robinJr);

		Order newOrder2 = new Order();
		newOrder2.setDate((new Date(1521140022)));
		newOrder2.setBuyer(robinSr);

		newOrder1 = orderDao.createOrUpdate(newOrder1);
		newOrder2 = orderDao.createOrUpdate(newOrder2);

		List<Order> orders = orderDao.readAll();
		assertEquals(2, orders.size());
		assertNotNull(orders.get(0));
		assertNotNull(orders.get(1));

		orders.sort((o1, o2) -> o1.getBuyer().getName().compareTo(o2.getBuyer().getName()));

		Order order1 = orders.get(0);
		orderDao.delete(order1);

		orders = orderDao.readAll();
		assertEquals(1, orders.size());
		assertNotNull(orders.get(0));

		assertEquals("Robin Doe Senior", orders.get(0).getBuyer().getName());
		assertEquals(1521140022, orders.get(0).getDate().getTime());
	}

	@Test
	public void deleteAllOrdersTest() {
		orderDao.createOrUpdate(new Order());
		orderDao.createOrUpdate(new Order());
		orderDao.createOrUpdate(new Order());

		assertFalse(orderDao.readAll().isEmpty());

		orderDao.deleteAll();

		assertTrue(orderDao.readAll().isEmpty());
	}

	@Before
	public void cleanTable() {
		orderEntryDao.deleteAll();
		orderDao.deleteAll();
	}

	@AfterClass
	public static void cleanup() {
		orderDao.close();
	}
}
