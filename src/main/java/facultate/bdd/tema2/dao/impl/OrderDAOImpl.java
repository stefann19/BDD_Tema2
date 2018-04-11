package facultate.bdd.tema2.dao.impl;

import facultate.bdd.tema2.dao.interfaces.OrderDAO;
import facultate.bdd.tema2.entities.Genre;
import facultate.bdd.tema2.entities.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrderDAOImpl extends OrderDAO {
    public EntityManagerFactory emFactory;
    public EntityManager entityManager;

    public OrderDAOImpl(String persistenceUnitName) {
        super(persistenceUnitName);
        emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public void close() {
        emFactory.close();
    }

    @Override
    public Order createOrUpdate(Order entity) {
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
    public Order findById(int id) {
        try {
            entityManager = emFactory.createEntityManager();
            return entityManager.find(Order.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Order update(Order entity) {
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
    public void delete(Order entity) {
        try {
            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity=entityManager.find(Order.class, entity.getId());
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
            for(Order o: readAll()) {
                delete(o);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Order> readAll() {
        try {
            entityManager = emFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> rootEntry = cq.from(Order.class);
            CriteriaQuery<Order> all = cq.select(rootEntry);
            TypedQuery<Order> allQuery = entityManager.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }
}
