package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ContactFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

public class InterestFacadeTest {

	private EntityManager em;
	private InterestFacade interestFacade;
	private SocialEntityFacade sef;
	private ContactFacade contactFacade;

	private Interest interest1, interest2, interest3, interest4;
	private SocialEntity s1, s2, s3;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		interestFacade = new InterestFacade(em);
		sef = new SocialEntityFacade(em);
		contactFacade = new ContactFacade(em);
	}

	@After
	public void tearDown() {
		em.close();
	}

	@Test
	public void testCreateInterest() {
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest("Sport");
		em.getTransaction().commit();

		Interest interestBis = em.find(Interest.class, interest.getId());
		assertEquals(interest, interestBis);
		assertSame(interest, interestBis);

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateInterestWithNull() {
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(null);
		em.getTransaction().commit();

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}

	@Test
	public void testCreateImpliesLowerCases() {
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest("_LoWeR CaSeS_");
		em.getTransaction().commit();

		assertEquals("_lower cases_", interest.getName());

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}
	
	@Test
	public void testModifyInterest() {
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest("java");
		em.getTransaction().commit();

		assertEquals("java", interest.getName());
		
		em.getTransaction().begin();
		interestFacade.modifyInterest("java6", interest);
		em.getTransaction().commit();

		assertEquals("java6", interest.getName());

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}
	
	@Ignore
	public void testModifyInterestImpliesLowerCases() {
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest("java");
		em.getTransaction().commit();

		assertEquals("java", interest.getName());
		
		em.getTransaction().begin();
		interestFacade.modifyInterest("JAVA6", interest);
		em.getTransaction().commit();

		assertEquals("java6", interest.getName());

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}
	
	@Test
	public void testGetInterest() {
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest("Musique");
		em.getTransaction().commit();

		Interest interestBis = interestFacade.getInterest(interest.getId());
		assertEquals(interest, interestBis);
		assertSame(interest, interestBis);

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}

	@Ignore
	public void testCreateWithExistingName() {
		em.getTransaction().begin();
		Interest interest1 = interestFacade.createInterest("Java");
		em.getTransaction().commit();

		try {
			em.getTransaction().begin();
			Interest interest2 = interestFacade.createInterest("Java");
			em.getTransaction().commit();

			em.getTransaction().begin();
			interestFacade.deleteInterest(interest2);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw e;
		} finally {
			// Not completed
			em.getTransaction().begin();
			interestFacade.deleteInterest(interest1);
			em.getTransaction().commit();
		}
	}

	@Test
	public void testDeleteInterest() {
		String interestName = "JEE";
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
		assertNull(em.find(SocialEntity.class, interest.getId()));
	}

	@Test
	public void testGetInterests() {
		em.getTransaction().begin();
		Interest interest1 = interestFacade.createInterest("Interest1");
		Interest interest2 = interestFacade.createInterest("Interest2");
		Interest interest3 = interestFacade.createInterest("Interest3");
		em.getTransaction().commit();

		List<Interest> interests = interestFacade.getInterests();
		assertTrue(interests.contains(interest1));
		assertTrue(interests.contains(interest2));
		assertTrue(interests.contains(interest3));

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest1);
		interestFacade.deleteInterest(interest2);
		interestFacade.deleteInterest(interest3);
		em.getTransaction().commit();
	}

	private void createInterestsAndSocialEntities() {
		em.getTransaction().begin();
		interest1 = interestFacade.createInterest("Interest1");
		interest2 = interestFacade.createInterest("Interest2");
		interest3 = interestFacade.createInterest("Interest3");
		interest4 = interestFacade.createInterest("Interest4");
		s1 = sef.createSocialEntity("entity1", "entity", "entity1@entities.com");
		s2 = sef.createSocialEntity("entity2", "entity", "entity2@entities.com");
		s3 = sef.createSocialEntity("entity3", "entity", "entity3@entities.com");
		em.getTransaction().commit();
	}

	private void removeInterest(Interest i) {
		try {
			em.getTransaction().begin();
			interestFacade.deleteInterest(i);
			em.getTransaction().commit();
		} catch (Exception e) {
		}
	}

	private void removeInterestsAndSocialEntities() {
		removeInterest(interest2);
		removeInterest(interest3);
		removeInterest(interest4);
	}

	@Test
	public void testGetOtherInterests() {
		createInterestsAndSocialEntities();
		em.getTransaction().begin();
		contactFacade.askContact(s1, s2);
		contactFacade.acceptContact(s2, s1);
		contactFacade.askContact(s1, s3);
		contactFacade.acceptContact(s3, s1);
		sef.addInterest(interest1, s2);
		sef.addInterest(interest2, s2);
		sef.addInterest(interest3, s2);
		sef.addInterest(interest4, s3);
		em.getTransaction().commit();

		List<Interest> result = interestFacade.getOtherInterests(s1);
		assertTrue(result.contains(interest1) && result.contains(interest2)
				&& result.contains(interest3) && result.contains(interest4));

		em.getTransaction().begin();
		interestFacade.deleteInterest(interest1);
		interestFacade.deleteInterest(interest2);
		interestFacade.deleteInterest(interest3);
		interestFacade.deleteInterest(interest4);
		em.getTransaction().commit();

		result = interestFacade.getOtherInterests(s1);
		assertFalse(result.contains(interest1));
		assertFalse(result.contains(interest2));
		assertFalse(result.contains(interest3));
		assertFalse(result.contains(interest4));

	}

	@Test
	public void testgetNonAssociatedInterests() {
		em.getTransaction().begin();
		SocialEntity s1 = sef.createSocialEntity("entity1", "entity",
				"entity4@entities.com");
		em.persist(s1);
		em.getTransaction().commit();
		assertNotNull(interestFacade.getNonAssociatedInterests(s1));

	}

}
