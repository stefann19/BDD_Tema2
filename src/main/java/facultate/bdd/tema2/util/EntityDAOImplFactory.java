package facultate.bdd.tema2.util;

import facultate.bdd.tema2.dao.impl.*;
import facultate.bdd.tema2.dao.interfaces.*;
import facultate.bdd.tema2.entities.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityDAOImplFactory {

	public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bookstore");

	public static BookDAO createNewBookDAOImpl(String persistenceUnitName) {
		return new BookDAO(persistenceUnitName);
	}
	
	public static BuyerDAO createNewBuyerDAOImpl(String persistenceUnitName) {
        return new BuyerDAO(persistenceUnitName);
	}
	
	public static GenreDAO createNewGenreDAOImpl(String persistenceUnitName) {
/*
		* TODO: return a new instance of your GenreDAO implementation *
*/
        return new GenreDAO(persistenceUnitName);

	}
	
	public static OrderDAO createNewOrderDAOImpl(String persistenceUnitName) {
/*
		* TODO: return a new instance of your OrderDAO implementation *
*/
        return new OrderDAO(persistenceUnitName);
	}
	
	public static OrderEntryDAO createNewOrderEntryDAOImpl(String persistenceUnitName) {
		/** TODO: return a new instance of your OrderEntryDAO implementation **/

/*
		OrderEntryDAO orderEntryDAO= (GenericDAO<OrderEntry>) new EntityDAOImpl<OrderEntry>(persistenceUnitName, OrderEntry.class);
*/

        return new OrderEntryDAO(persistenceUnitName);
	}

	/*public static BookDAO createNewBookDAOImpl(String persistenceUnitName) {
		return new BookDAOImpl(persistenceUnitName);
	}

	public static BuyerDAO createNewBuyerDAOImpl(String persistenceUnitName) {
		return new BuyerDAOImpl(persistenceUnitName);
	}

	public static GenreDAO createNewGenreDAOImpl(String persistenceUnitName) {
		*//** TODO: return a new instance of your GenreDAO implementation **//*
		return new GenreDAOImpl(persistenceUnitName);

	}

	public static OrderDAO createNewOrderDAOImpl(String persistenceUnitName) {
		*//** TODO: return a new instance of your OrderDAO implementation **//*
		return new OrderDAOImpl(persistenceUnitName);
	}

	public static OrderEntryDAO createNewOrderEntryDAOImpl(String persistenceUnitName) {
		*//** TODO: return a new instance of your OrderEntryDAO implementation **//*
*//*
		OrderEntryDAO orderEntryDAO= (GenericDAO<OrderEntry>) new EntityDAOImpl<OrderEntry>(persistenceUnitName, OrderEntry.class);
*//*
		return new OrderEntryDAOImpl(persistenceUnitName);
	}*/
}
