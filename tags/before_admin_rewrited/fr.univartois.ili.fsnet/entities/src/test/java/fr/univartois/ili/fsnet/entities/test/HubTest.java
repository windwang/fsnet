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

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class HubTest {

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
        Hub hub = new Hub("ma communaute", date);
        em.getTransaction().begin();
        em.persist(hub);
        em.getTransaction().commit();
        Hub hub2 = em.find(Hub.class, hub.getId());

        assertEquals(hub.getId(), hub2.getId());
        assertEquals(hub.getDateCreation(), hub2.getDateCreation());
    }
    
    @Test(expected = RollbackException.class)
    public void testDateCreationIsNotNull() {
        Hub hub = new Hub("ma communaute", null);
        em.getTransaction().begin();
        em.persist(hub);
        em.getTransaction().commit();
    }
    
}
