import facultate.bdd.tema2.JPAUtility;

        import javax.persistence.EntityManager;
        import javax.persistence.EntityManagerFactory;
        import javax.persistence.Persistence;

public class Test {
    public static void main(String [] args){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test"); //name of persistence unit here
        EntityManager entityManager = factory.createEntityManager();
    }
}
