package facultate.bdd.tema2.dao.interfaces;

import com.sun.org.apache.xpath.internal.operations.Or;
import facultate.bdd.tema2.entities.Order;
import facultate.bdd.tema2.util.EntityDAOImplFactory;

/** TODO: Implement this interface **/
public class OrderDAO extends GenericDAOAbstractImpl<Order> {

    public OrderDAO(String persistenceUnitName) {
        super(persistenceUnitName, Order.class, EntityDAOImplFactory.entityManagerFactory);
    }
}
