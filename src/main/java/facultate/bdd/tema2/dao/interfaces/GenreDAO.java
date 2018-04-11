package facultate.bdd.tema2.dao.interfaces;

import facultate.bdd.tema2.entities.Genre;
import facultate.bdd.tema2.util.EntityDAOImplFactory;

/** TODO: Implement this interface **/
public class GenreDAO extends GenericDAOAbstractImpl<Genre> {

    public GenreDAO(String persistenceUnitName) {
        super(persistenceUnitName, Genre.class, EntityDAOImplFactory.entityManagerFactory);
    }
}
