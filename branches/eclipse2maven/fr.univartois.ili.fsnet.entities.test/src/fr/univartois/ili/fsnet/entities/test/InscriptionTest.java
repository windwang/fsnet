/**
 * 
 */
package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Inscription;

/**
 * @author romuald druelle
 * 
 */
public class InscriptionTest {

	private EntityManagerFactory emf;
	private EntityManager em;

	@Before
	public void setUp() {
		System.err.println("Le before est execute");
		emf = Persistence.createEntityManagerFactory("fsnetjpa");
		em = emf.createEntityManager();
	}

	@After
	public void tearDown() {
		System.err.println("Le after est execute");
		if (em != null) {
			em.close();
		}
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersist() {
		System.err.println("Le test est execute");
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
