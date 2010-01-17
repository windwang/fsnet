package fr.univartois.ili.fsnet.entities.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Manifestation;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class ManifestationTest {

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
        Manifestation manif = new Manifestation(date);
        em.getTransaction().begin();
        em.persist(manif);
        em.getTransaction().commit();
    }

    @Test(expected = javax.persistence.RollbackException.class)
    public void testManifestationDateNotNull() {
        Manifestation manif = new Manifestation();
        em.getTransaction().begin();
        em.persist(manif);
        em.getTransaction().commit();
    }
}
