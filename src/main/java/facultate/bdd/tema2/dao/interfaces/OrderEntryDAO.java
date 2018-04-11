package facultate.bdd.tema2.dao.interfaces;

import facultate.bdd.tema2.entities.OrderEntry;
import facultate.bdd.tema2.util.EntityDAOImplFactory;

/** TODO: Implement this interface **/
public class OrderEntryDAO extends GenericDAOAbstractImpl<OrderEntry> {

    public OrderEntryDAO(String persistenceUnitName) {
        super(persistenceUnitName, OrderEntry.class, EntityDAOImplFactory.entityManagerFactory);
    }
}
