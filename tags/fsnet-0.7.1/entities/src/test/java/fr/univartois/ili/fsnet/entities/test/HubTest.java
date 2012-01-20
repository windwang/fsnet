package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class HubTest {

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
        final SocialEntity socialEntity = new SocialEntity("ktest6", "test6", "test6d@test.com");
        final Community community = new Community(socialEntity, "Ma comm");
        Hub hub = new Hub(community, socialEntity, "mon hub");
        em.getTransaction().begin();
        em.persist(socialEntity);
        em.persist(community);
        em.persist(hub);
        em.getTransaction().commit();
        Hub hub2 = em.find(Hub.class, hub.getId());

        assertEquals(hub.getId(), hub2.getId());
        assertEquals(hub.getCreationDate(), hub2.getCreationDate());
    }
}
