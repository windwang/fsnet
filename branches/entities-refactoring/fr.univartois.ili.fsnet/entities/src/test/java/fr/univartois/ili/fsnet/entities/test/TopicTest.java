package fr.univartois.ili.fsnet.entities.test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
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

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
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

        SocialEntity es = new SocialEntity("Ragoût", "Mouton", "RagoûtMouton@toiaussitafaim.com");
        es.setNom("Théophile");
        es.setPrenom("Gautier");
        Hub h = new Hub(new Community(es, "macom"), es, "mon hub");
        Topic top = new Topic(h, es, "mon topic");
        TopicMessage firstmessage = new TopicMessage("kiiiii", es, top);
        top.getMessages().add(firstmessage);
        em.getTransaction().begin();
        em.persist(es);
        em.persist(top);
        em.getTransaction().commit();
    }
}
