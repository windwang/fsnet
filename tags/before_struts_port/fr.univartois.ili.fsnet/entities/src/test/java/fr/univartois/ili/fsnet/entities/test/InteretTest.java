package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Interet;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class InteretTest {

    private EntityManager em;

    @Before
    public void setUp() {
        em = TestEntityManagerProvider.getInstance().getEntityManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPersist() {
        Interet inte = new Interet(null, "java");
        em.getTransaction().begin();
        em.persist(inte);
        em.getTransaction().commit();
        int monId = inte.getId();
        assertNotNull("id not null", monId);
    }
}
