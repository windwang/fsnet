package fr.univartois.ili.fsnet.entities.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class TopicTest {

    private EntityManager em;

    @Before
    public void setUp() {
        em = TestEntityManagerProvider.getInstance().getEntityManager();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test to check if it's possible to persist a Topic
     */
    @Test
    public void testPersist() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = null;
        try {
            date = (Date) formatter.parse("29/01/02");
        } catch (ParseException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
        }

        EntiteSociale es = new EntiteSociale();
        es.setNom("Théophile");
        es.setPrenom("Gautier");

        Topic top = new Topic("master pro", date, null, null, es);

        em.getTransaction().begin();
        em.persist(es);
        em.persist(top);
        em.getTransaction().commit();
    }

    /**
     * Check that topic's date cannot be null
     */
    @Test(expected = javax.persistence.RollbackException.class)
    public void testDateNotNull() {

        EntiteSociale es = new EntiteSociale();
        es.setNom("Baudelaire");
        es.setPrenom("Charles");

        // set the topic date to null should throw an exception
        Topic top = new Topic("master pro", null, null, null, es);

        em.getTransaction().begin();
        em.persist(es);
        em.persist(top);
        em.getTransaction().commit();
    }

    /**
     * Check that topic's owner cannot be null
     */
    @Test(expected = javax.persistence.RollbackException.class)
    public void testTitreNotNull() {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = null;
        try {
            date = (Date) formatter.parse("29/01/02");
        } catch (ParseException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
        }

        EntiteSociale es = new EntiteSociale();
        es.setNom("Voltaire");
        es.setPrenom("");

        // set the topic title to null should throw an exception
        Topic top = new Topic(null, date, null, null, es);

        em.getTransaction().begin();
        em.persist(es);
        em.persist(top);
        em.getTransaction().commit();
    }
}
