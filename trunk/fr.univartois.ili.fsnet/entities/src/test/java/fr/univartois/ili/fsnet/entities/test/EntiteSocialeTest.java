package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class EntiteSocialeTest {

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
		final String lastName = "Germain";
		final String firstName = "Tantoine";
		final String mail = "GermainTantoine@gmail.com";
		EntiteSociale ent = new EntiteSociale(lastName, firstName, mail);
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		EntiteSociale ent2 = em.find(EntiteSociale.class, ent.getId());
		assertEquals(ent2.getId(), ent.getId());
		assertEquals(ent2.getNom(), lastName);
		assertEquals(ent2.getPrenom(), firstName);
		assertEquals(ent2.getEmail(), mail);
	}

	@Test
	public void testUpdate() {
		EntiteSociale ent = new EntiteSociale("titi", "tata", "esupdate@gmail.com");
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		assertEquals(ent.getEmail(), "esupdate@gmail.com");
		ent.setEmail("esupdate2@gmail.com");
		em.getTransaction().begin();
		em.merge(ent);
		em.getTransaction().commit();
		ent = em.find(EntiteSociale.class, ent.getId());
		assertEquals(ent.getEmail(), "esupdate2@gmail.com");
	}

	@Test
	public void testDelete() {
		EntiteSociale ent1 = new EntiteSociale("titi", "titi",
				"mail1@gmail.com");
		EntiteSociale ent2 = new EntiteSociale("tyty", "tyty",
				"mail2@gmail.com");
		EntiteSociale ent3 = new EntiteSociale("tutu", "tutu",
				"mail3@gmail.com");

		EntiteSociale[] lesEntites = { ent1, ent2, ent3 };
		em.getTransaction().begin();
		for (EntiteSociale ent : lesEntites) {
			em.persist(ent);
		}
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.remove(ent2);
		em.getTransaction().commit();
		assertNull(em.find(EntiteSociale.class, ent2.getId()));

	}

	@Test(expected = RollbackException.class)
	public void testUniqueMail() {
		EntiteSociale ent1 = new EntiteSociale("zaza", "zaza", "zaza@gmail.com");
		EntiteSociale ent2 = new EntiteSociale("zozo", "zozo", "zaza@gmail.com");
		em.getTransaction().begin();
		em.persist(ent1);
		em.persist(ent2);
		em.getTransaction().commit();
	}

	@Test(expected = RollbackException.class)
	public void testRequieredMail() {
		EntiteSociale ent = new EntiteSociale("zaza", "zaza", null);
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
	}
}
