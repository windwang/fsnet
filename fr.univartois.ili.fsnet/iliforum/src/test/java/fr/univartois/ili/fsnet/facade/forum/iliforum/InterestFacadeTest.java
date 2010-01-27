package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

public class InterestFacadeTest {

    private EntityManager em;
    private InterestFacade interestFacade;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        interestFacade = new InterestFacade(em);
    }

    @Test
    public void testCreateInterest() {
//    	String interestName = "Sport";
//    	Interest interest = interestFacade.createInterest(interestName);
//    	em.getTransaction().begin();
    }
}
