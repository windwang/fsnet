package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class SocialEntityTest {

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
		SocialEntity ent = new SocialEntity(lastName, firstName, mail);
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		SocialEntity ent2 = em.find(SocialEntity.class, ent.getId());
		assertEquals(ent2.getId(), ent.getId());
		assertEquals(ent2.getNom(), lastName);
		assertEquals(ent2.getPrenom(), firstName);
		assertEquals(ent2.getEmail(), mail);
	}

	@Test
	public void testUpdate() {
		SocialEntity ent = new SocialEntity("titi", "tata", "esupdate@gmail.com");
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		assertEquals(ent.getEmail(), "esupdate@gmail.com");
		ent.setEmail("esupdate2@gmail.com");
		em.getTransaction().begin();
		em.merge(ent);
		em.getTransaction().commit();
		ent = em.find(SocialEntity.class, ent.getId());
		assertEquals(ent.getEmail(), "esupdate2@gmail.com");
	}

	@Test
	public void testDelete() {
		SocialEntity ent1 = new SocialEntity("titi", "titi",
				"mail1@gmail.com");
		SocialEntity ent2 = new SocialEntity("tyty", "tyty",
				"mail2@gmail.com");
		SocialEntity ent3 = new SocialEntity("tutu", "tutu",
				"mail3@gmail.com");

		SocialEntity[] lesEntites = { ent1, ent2, ent3 };
		em.getTransaction().begin();
		for (SocialEntity ent : lesEntites) {
			em.persist(ent);
		}
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.remove(ent2);
		em.getTransaction().commit();
		assertNull(em.find(SocialEntity.class, ent2.getId()));

	}

	@Test(expected = RollbackException.class)
	public void testUniqueMail() {
		SocialEntity ent1 = new SocialEntity("zaza", "zaza", "zaza@gmail.com");
		SocialEntity ent2 = new SocialEntity("zozo", "zozo", "zaza@gmail.com");
		em.getTransaction().begin();
		em.persist(ent1);
		em.persist(ent2);
		em.getTransaction().commit();
	}

	@Test(expected = RollbackException.class)
	public void testRequieredMail() {
		SocialEntity ent = new SocialEntity("zaza", "zaza", null);
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
	}
}
