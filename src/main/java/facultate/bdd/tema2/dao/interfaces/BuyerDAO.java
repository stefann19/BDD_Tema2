package facultate.bdd.tema2.dao.interfaces;

import facultate.bdd.tema2.entities.Buyer;
import facultate.bdd.tema2.util.EntityDAOImplFactory;

/** TODO: Implement this interface **/
public class BuyerDAO extends GenericDAOAbstractImpl<Buyer> {

    public BuyerDAO(String persistenceUnitName) {
        super(persistenceUnitName, Buyer.class, EntityDAOImplFactory.entityManagerFactory);
    }
}
