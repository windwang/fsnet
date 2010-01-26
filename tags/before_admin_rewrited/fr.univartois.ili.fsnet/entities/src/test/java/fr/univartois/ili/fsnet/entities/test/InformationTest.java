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

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Information;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class InformationTest {

    private EntityManager em;

    @Before
    public void setUp() {
        em = TestEntityManagerProvider.getInstance().getEntityManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPersist() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        EntiteSociale createur = new EntiteSociale("test", "bis", "tod@gmail.com");
        em.getTransaction().begin();
        em.persist(createur);
        em.getTransaction().commit();
        Information info = new Information("info", date, "blabla", "Y",
                createur);
        em.getTransaction().begin();
        em.persist(info);
        em.getTransaction().commit();
        Information info2 = em.find(Information.class, info.getId());
		assertEquals(info.getId(), info2.getId());
		assertEquals(info.getNom(), info2.getNom());
		assertEquals(info.getDateInformation(), info2.getDateInformation());
		assertEquals(info.getContenu(), info2.getContenu());
		assertEquals(info.getVisible(), info2.getVisible());
		assertEquals(info.getCreateur(), info2.getCreateur());
    }
    
    @Test
    public void testGeneratedValueId() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        EntiteSociale createur = new EntiteSociale("tests", "biss", "ted@gmail.com");
        em.getTransaction().begin();
        em.persist(createur);
        em.getTransaction().commit();
        Information info = new Information("info", date, "blabla", "Y",
                createur);
        em.getTransaction().begin();
        em.persist(info);
        em.getTransaction().commit();
        Information info2 = new Information("info", date, "blabla", "Y",
                createur);
        em.getTransaction().begin();
        em.persist(info2);
        em.getTransaction().commit();
        assertEquals(info.getId() + 1, info2.getId());
    }
}
