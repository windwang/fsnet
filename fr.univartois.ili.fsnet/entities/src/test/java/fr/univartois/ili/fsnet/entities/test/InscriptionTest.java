/**
 * 
 */
package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

/**
 * @author romuald druelle
 * 
 */
public class InscriptionTest {

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
		EntiteSociale entite = new EntiteSociale();
		entite.setNom("entiteInscription");
		em.getTransaction().begin();
		em.persist(entite);
		em.getTransaction().commit();
		Inscription insc = new Inscription(entite);
		em.getTransaction().begin();
		em.persist(insc);
		em.getTransaction().commit();
		int monId = insc.getEntite().getId();
		assertNotNull("id not null", monId);
		assertEquals(insc.getEtat(), "En attente d'inscription");
	}

}
