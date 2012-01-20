package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Decideur;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class DecideurTest {

    private EntityManager em;

    @Before
    public void setUp() {
        em = TestEntityManagerProvider.getInstance().getEntityManager();
    }

    @After
    public void tearDown() {
    }

    // TODO TEST-LOL galileo
    @Test
    public void testPersist() {
        Decideur decideur = new Decideur();
        em.getTransaction().begin();
        em.persist(decideur);
        em.getTransaction().commit();
        int monId = decideur.getId();
        assertNotNull("id not null", monId);
    }
  
}
