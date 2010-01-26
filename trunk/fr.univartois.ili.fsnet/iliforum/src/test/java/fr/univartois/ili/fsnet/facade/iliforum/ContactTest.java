package fr.univartois.ili.fsnet.facade.iliforum;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.ContactFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ContactTest {

    private EntityManager em;

    @Before
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
        em = entityManagerFactory.createEntityManager();

    }

    @Test(expected = IllegalStateException.class)
    public void acceptWithoutAskingTest() {
        ContactFacade cf = new ContactFacade(em);
        SocialEntity se1 = new SocialEntity("toto", "titi", "toto@gmail.com");
        SocialEntity se2 = new SocialEntity("tata", "titi", "tata@gmail.com");
        em.getTransaction().begin();
        em.persist(se1);
        em.persist(se2);
        em.getTransaction().commit();
        cf.acceptContact(se1, se2);
    }

    @Test(expected = IllegalStateException.class)
    public void alreadyAskedTest() {
        ContactFacade cf = new ContactFacade(em);
        SocialEntity se1 = new SocialEntity("toto", "titi", "titi@gmail.com");
        SocialEntity se2 = new SocialEntity("tata", "titi", "taytaygmail.com");
        em.getTransaction().begin();
        em.persist(se1);
        em.persist(se2);
        em.getTransaction().commit();
        cf.askContact(se1, se2);
        cf.askContact(se2, se1);
    }
}
