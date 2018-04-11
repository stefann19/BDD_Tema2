package facultate.bdd.tema2.dao.impl;

import facultate.bdd.tema2.dao.interfaces.OrderEntryDAO;
import facultate.bdd.tema2.entities.Genre;
import facultate.bdd.tema2.entities.OrderEntry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrderEntryDAOImpl extends OrderEntryDAO {
    public EntityManagerFactory emFactory;
    public EntityManager entityManager;

    public OrderEntryDAOImpl(String persistenceUnitName) {
        super(persistenceUnitName);
        emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public void close() {
        emFactory.close();
    }

    @Override
    public OrderEntry createOrUpdate(OrderEntry entity) {
        try {
            entityManager = emFactory.createEntityManager();
            try {
                entityManager.getTransaction().begin();
                entityManager.persist(entity);
                entityManager.getTransaction().commit();
            } catch(Exception ex) {
                entityManager.getTransaction().rollback();
                entityManager.getTransaction().begin();
                entity = entityManager.merge(entity);
                entityManager.getTransaction().commit();
            }
            return entity;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public OrderEntry findById(int id) {
        try {
            entityManager = emFactory.createEntityManager();
            return entityManager.find(OrderEntry.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public OrderEntry update(OrderEntry entity) {
        try {
            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return entity;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(OrderEntry entity) {
        try {
            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity=entityManager.find(OrderEntry.class, entity.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteAll() {
        try {
            for(OrderEntry oe: readAll()) {
                delete(oe);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<OrderEntry> readAll() {
        try {
            entityManager = emFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<OrderEntry> cq = cb.createQuery(OrderEntry.class);
            Root<OrderEntry> rootEntry = cq.from(OrderEntry.class);
            CriteriaQuery<OrderEntry> all = cq.select(rootEntry);
            TypedQuery<OrderEntry> allQuery = entityManager.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }
}
