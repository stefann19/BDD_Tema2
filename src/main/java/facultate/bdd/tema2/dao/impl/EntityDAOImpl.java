package facultate.bdd.tema2.dao.impl;

import facultate.bdd.tema2.dao.interfaces.GenericDAO;
import facultate.bdd.tema2.entities.Book;
import org.jboss.jandex.ClassType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

public class EntityDAOImpl<T> implements GenericDAO<T> {
    public EntityManagerFactory emFactory;
    public EntityManager entityManager;
    public Class<T> type;
    public EntityDAOImpl(String persistenceUnitName,Class<T> type) {
        emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        type = type;
    }


    @Override
    public void close() {
        emFactory.close();
    }


    @Override
    public T createOrUpdate(T entity) {
        try {
            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
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
    public T findById(int id) {
        try {
            entityManager = emFactory.createEntityManager();
            return entityManager.find(type,id) ;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public T update(T entity) {
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
        }    }

    @Override
    public void delete(T entity) {
        try {
            Method method = type.getDeclaredMethod("getId");


            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity=entityManager.find(type,  method.invoke(entity));
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
            for(T b: readAll()) {
                delete(b);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<T> readAll() {
        try {
            entityManager.getTransaction().begin();
            String entityName = type.toString();
            List<T> result = entityManager.createQuery( "from " + entityName, type ).getResultList();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }
}
