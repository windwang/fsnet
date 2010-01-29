package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import java.lang.IllegalArgumentException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 
 * @author mickael watrelot - micgamers@gmail.com
 *
 */
public class AnnouncementTest {

    private EntityManager em;

    @Before
    public void setUp() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
        em = fact.createEntityManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPersist() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        final SocialEntity socialEntity = new SocialEntity("test", "test", "test@test.com");
        Announcement annonce = new Announcement(socialEntity, "test Announcement", "HEHEHEHEHE", new Date(), false);
        em.getTransaction().begin();
        em.persist(socialEntity);
        em.persist(annonce);
        em.getTransaction().commit();
        Announcement annonce2 = em.find(Announcement.class, annonce.getId());
        assertEquals(annonce.getId(), annonce2.getId());
        assertEquals(annonce.getEndDate(), annonce2.getEndDate());
    }

    @Test
    public void testPersistTwo() throws ParseException {
        String nom = "UserIli";
        String contenu = "content";
        String visible = "visible";
        SocialEntity es = new SocialEntity("name", "prenom", "mailannonce@mail.com");
        em.getTransaction().begin();
        em.persist(es);
        em.getTransaction().commit();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        Date dateFin = (Date) formatter.parse("29/03/02");
        Announcement annonce = new Announcement(es, "TITLE", "content", date, true);
        em.getTransaction().begin();
        em.persist(annonce);
        em.getTransaction().commit();
        Announcement annonce2 = em.find(Announcement.class, annonce.getId());
        assertEquals(annonce.getId(), annonce2.getId());
        assertEquals(annonce.getCreationDate(), annonce2.getCreationDate());
        assertEquals(annonce.getEndDate(), annonce2.getEndDate());
        assertEquals(annonce.getContent(), annonce2.getContent());
        assertEquals(annonce.getCreator(), annonce2.getCreator());
    }

    @Test
    public void testGeneratedValueId() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        final SocialEntity socialEntity = new SocialEntity("test2", "test2", "test2@test.com");
        Announcement annonce = new Announcement(socialEntity, "test2 Announcement", "HEHEHEHEHE2", new Date(), false);
        em.getTransaction().begin();
        em.persist(socialEntity);
        em.persist(annonce);
        em.getTransaction().commit();
        final SocialEntity socialEntity1 = new SocialEntity("test3", "test3", "test3@test.com");
        Announcement annonce2 = new Announcement(socialEntity1, "test 3Announcement", "HEHEHEHEHE3", new Date(), false);
        em.getTransaction().begin();
        em.persist(socialEntity1);
        em.persist(annonce2);
        em.getTransaction().commit();
        assertEquals(annonce.getId() + 1, annonce2.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDateIsNotNull() {
        Announcement annonce = new Announcement(null, null, null, null, true);
        em.getTransaction().begin();
        em.persist(annonce);
        em.getTransaction().commit();
    }
}
