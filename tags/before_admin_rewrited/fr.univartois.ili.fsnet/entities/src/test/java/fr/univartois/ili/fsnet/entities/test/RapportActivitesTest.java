package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.RapportActivites;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class RapportActivitesTest {

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
        RapportActivites rapp = new RapportActivites(null, date);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
        RapportActivites rapp2 = em.find(RapportActivites.class, rapp.getId());

        assertEquals(rapp2.getId(), rapp.getId());
        assertEquals(rapp2.getDateRapport(), rapp.getDateRapport());
    }

    @Test
    public void testGeneratedValueId() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        RapportActivites rapp = new RapportActivites(null, date);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
        RapportActivites rapp2 = new RapportActivites(null, date);
        em.getTransaction().begin();
        em.persist(rapp2);
        em.getTransaction().commit();
        assertEquals(rapp.getId() + 1, rapp2.getId());
    }

    @Test(expected = RollbackException.class)
    public void testDateIsNotNull() {
        RapportActivites rapp = new RapportActivites(null, null);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
    }
}
