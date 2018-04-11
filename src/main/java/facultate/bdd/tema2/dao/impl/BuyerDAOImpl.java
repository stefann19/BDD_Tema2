package facultate.bdd.tema2.dao.impl;

import facultate.bdd.tema2.dao.interfaces.BuyerDAO;
import facultate.bdd.tema2.entities.Buyer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BuyerDAOImpl extends BuyerDAO {
    public EntityManagerFactory emFactory;
    public EntityManager entityManager;

    public BuyerDAOImpl(String persistenceUnitName) {
        super(persistenceUnitName);
        emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public void close() {
        emFactory.close();
    }

    @Override
    public Buyer createOrUpdate(Buyer entity) {
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
    public Buyer findById(int id) {
        try {
            entityManager = emFactory.createEntityManager();
            return entityManager.find(Buyer.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Buyer update(Buyer entity) {
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
    public void delete(Buyer entity) {
        try {
            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity=entityManager.find(Buyer.class, entity.getId());
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
            for(Buyer b: readAll()) {
                delete(b);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Buyer> readAll() {
        try {
            entityManager = emFactory.createEntityManager();
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Buyer> cq = cb.createQuery(Buyer.class);
            Root<Buyer> rootEntry = cq.from(Buyer.class);
            CriteriaQuery<Buyer> all = cq.select(rootEntry);
            TypedQuery<Buyer> allQuery = entityManager.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }
}
