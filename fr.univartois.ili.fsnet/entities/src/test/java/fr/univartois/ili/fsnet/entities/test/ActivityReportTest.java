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

import fr.univartois.ili.fsnet.entities.ActivityReport;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ActivityReportTest {

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
        ActivityReport rapp = new ActivityReport(null, date);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
        ActivityReport rapp2 = em.find(ActivityReport.class, rapp.getId());

        assertEquals(rapp2.getId(), rapp.getId());
        assertEquals(rapp2.getDateRapport(), rapp.getDateRapport());
    }

    @Test
    public void testGeneratedValueId() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        ActivityReport rapp = new ActivityReport(null, date);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
        ActivityReport rapp2 = new ActivityReport(null, date);
        em.getTransaction().begin();
        em.persist(rapp2);
        em.getTransaction().commit();
        assertEquals(rapp.getId() + 1, rapp2.getId());
    }

    @Test(expected = RollbackException.class)
    public void testDateIsNotNull() {
        ActivityReport rapp = new ActivityReport(null, null);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
    }
}
