package facultate.bdd.tema2.dao.interfaces;

import facultate.bdd.tema2.dao.impl.EntityDAOImpl;
import facultate.bdd.tema2.entities.Book;
import facultate.bdd.tema2.util.EntityDAOImplFactory;

/** TODO: Implement this interface **/
public class BookDAO extends GenericDAOAbstractImpl<Book> {

    public BookDAO(String persistenceUnitName) {
        super(persistenceUnitName, Book.class, EntityDAOImplFactory.entityManagerFactory);
    }
}
