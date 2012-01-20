package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class SocialEntityTest {

	private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
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
		assertEquals(ent2.getName(), lastName);
		assertEquals(ent2.getFirstName(), firstName);
		assertEquals(ent2.getEmail(), mail);
	}

	@Test
	public void testUpdate() {
		SocialEntity ent = new SocialEntity("titi", "tata",
				"esupdate@gmail.com");
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
		SocialEntity ent1 = new SocialEntity("titi", "titi", "mail1@gmail.com");
		SocialEntity ent2 = new SocialEntity("tyty", "tyty", "mail2@gmail.com");
		SocialEntity ent3 = new SocialEntity("tutu", "tutu", "mail3@gmail.com");

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

	@Test(expected = IllegalArgumentException.class)
	public void testRequieredMail() {
		SocialEntity ent = new SocialEntity("zaza", "zaza", null);
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
	}

	@Test
	public void testAddInteractionRead() {
		int nbInteraction = (Integer) em.createNativeQuery(
				"SELECT COUNT(*) FROM Interaction").getSingleResult();

		em.getTransaction().begin();
		SocialEntity user1 = new SocialEntity("name1", "firstName1",
				"email1@g.com");
		em.persist(user1);
		Interaction it1 = new Announcement(user1, "title1", "content1",
				new Date(), false);
		em.persist(it1);
		Interaction it2 = new Announcement(user1, "title2", "content2",
				new Date(), false);
		em.persist(it2);
		em.getTransaction().commit();

		em.getTransaction().begin();
		SocialEntity userLoad = em.find(SocialEntity.class, user1.getId());
		assertEquals(user1.getId(), userLoad.getId());
		assertEquals(0, userLoad.getInteractionsRead().size());
		Interaction itL1 = em.find(Interaction.class, it1.getId());
		assertEquals(it1.getId(), itL1.getId());
		assertEquals(0, itL1.getReaders().size());
		Interaction itL2 = em.find(Interaction.class, it2.getId());
		assertEquals(it2.getId(), itL2.getId());
		assertEquals(0, itL2.getReaders().size());
		em.getTransaction().commit();

		em.getTransaction().begin();
		userLoad = em.find(SocialEntity.class, user1.getId());
		itL1 = em.find(Interaction.class, it1.getId());
		itL2 = em.find(Interaction.class, it2.getId());
		userLoad.addInteractionRead(itL1);
		em.getTransaction().commit();

		em.getTransaction().begin();
		userLoad = em.find(SocialEntity.class, user1.getId());
		itL1 = em.find(Interaction.class, it1.getId());
		itL2 = em.find(Interaction.class, it2.getId());
		assertEquals(1, userLoad.getInteractionsRead().size());
		assertTrue(userLoad.getInteractionsRead().contains(itL1));
		assertEquals(1, itL1.getReaders().size());
		assertTrue(itL1.getReaders().contains(user1));
		assertEquals(0, itL2.getReaders().size());
		em.getTransaction().commit();
	}
}
