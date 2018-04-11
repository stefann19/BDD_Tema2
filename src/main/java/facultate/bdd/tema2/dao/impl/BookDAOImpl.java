package facultate.bdd.tema2.dao.impl;

import facultate.bdd.tema2.dao.interfaces.BookDAO;
import facultate.bdd.tema2.entities.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.text.html.parser.Entity;
import java.util.List;

public class BookDAOImpl extends BookDAO{


    public EntityManagerFactory emFactory;
    public EntityManager entityManager;

    public BookDAOImpl(String persistenceUnitName) {
        super(persistenceUnitName);
        emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public void close() {
        emFactory.close();
    }

    @Override
    public Book createOrUpdate(Book entity) {
        try{
            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return entity;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
            return null;
        }finally {
            entityManager.close();
        }
    }

    @Override
    public Book findById(int id) {
        try {
            entityManager = emFactory.createEntityManager();
            return entityManager.find(Book.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Book update(Book entity) {
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
    public void delete(Book entity) {
        try {
            entityManager = emFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity=entityManager.find(Book.class, entity.getId());
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
            for(Book b: readAll()) {
                delete(b);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

   /* @Override
    public List<Book> readAll() {
        try {
            entityManager = emFactory.createEntityManager();
            List<Book> result = entityManager.createQuery( "from Book ", Book.class ).getResultList();
            return result;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }*/
   @Override
   public List<Book> readAll() {
       try {
           entityManager = emFactory.createEntityManager();
           CriteriaBuilder cb = entityManager.getCriteriaBuilder();
           CriteriaQuery<Book> cq = cb.createQuery(Book.class);
           Root<Book> rootEntry = cq.from(Book.class);
           CriteriaQuery<Book> all = cq.select(rootEntry);
           TypedQuery<Book> allQuery = entityManager.createQuery(all);
           return allQuery.getResultList();
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
           return null;
       } finally {
           entityManager.close();
       }
   }
}
