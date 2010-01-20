package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Decideur;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Interaction;
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

    @Test
    public void testPersist() {
        Decideur decideur = new Decideur(null, null);
        em.getTransaction().begin();
        em.persist(decideur);
        em.getTransaction().commit();
        int monId = decideur.getId();
        assertNotNull("id not null", monId);
    }
    
    @Test(expected = RollbackException.class)
	public void testRequieredEntSociale() {
    	Interaction i= new Interaction();
		Decideur d = new Decideur(null,i);
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
	}
    
    @Test(expected = RollbackException.class)
	public void testRequieredInteraction() {
    	EntiteSociale es = new EntiteSociale("tata", "titi", "titi@gmai.com");
		Decideur d = new Decideur(es,null);
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
	}
}
