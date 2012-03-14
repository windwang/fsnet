package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
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
	
	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		interestFacade = new InterestFacade(em);
		sef = new SocialEntityFacade(em);
		contactFacade = new ContactFacade(em);
	}

	@Test
	public void testCreateInterest() {
		String interestName = "Sport";
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		em.getTransaction().commit();
		Interest interestBis = em.find(Interest.class , interest.getId());
		assertTrue(interest.equals(interestBis));
		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}

	@Test
	public void testGetInterest(){
		String interestName = "Musique";
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		em.getTransaction().commit();
		Interest interestBis = interestFacade.getInterest(interest.getId());
		assertTrue(interest.equals(interestBis));
		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}

	@Test
	public void testModifyInterest(){
		String interestName = "Java";
		
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		interestFacade.modifyInterest("Java6", interest);
		em.getTransaction().commit();
		
		Interest interestBis = interestFacade.getInterest(interest.getId());
		assertEquals("Java6", interestBis.getName());
		
		em.getTransaction().begin();
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
	}

	@Test
	public void testDeleteInterest(){
		String interestName = "JEE";
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		interestFacade.deleteInterest(interest);
		em.getTransaction().commit();
		assertNull(em.find(SocialEntity.class, interest.getId()));
	}

	@Test
	public void testGetInterests(){
		/*String interestName1 = "Interest1";
		String interestName2 = "Interest2";
		String interestName3 = "Interest3";

		em.getTransaction().begin();
		Interest interest1 = interestFacade.createInterest(interestName1);
		Interest interest2 = interestFacade.createInterest(interestName2);
		Interest interest3 = interestFacade.createInterest(interestName3);
		List<Interest> interests = interestFacade.getInterests();
		em.getTransaction().commit();
		assertTrue(interests.contains(interest1) && interests.contains(interest2) && interests.contains(interest3));
	*/
     }
	
	
	@Test
	public void testGetOtherInterests(){
		
		String interestName1 = "Interest1";
		String interestName2 = "Interest2";
		String interestName3 = "Interest3";
		String interestName4 = "Interest4";
		
		em.getTransaction().begin();
		Interest interest1 = interestFacade.createInterest(interestName1);
		Interest interest2 = interestFacade.createInterest(interestName2);
		Interest interest3 = interestFacade.createInterest(interestName3);
		Interest interest4 = interestFacade.createInterest(interestName4);
		SocialEntity s1 = sef.createSocialEntity("entity1","entity", "entity1@entities.com");
		SocialEntity s2 = sef.createSocialEntity("entity2","entity", "entity2@entities.com");
		SocialEntity s3 = sef.createSocialEntity("entity3","entity", "entity3@entities.com");
		contactFacade.askContact(s1,s2);
		contactFacade.acceptContact(s2,s1);
		contactFacade.askContact(s1,s3);
		contactFacade.acceptContact(s3,s1);
		sef.addInterest(interest1, s2);
		sef.addInterest(interest2, s2);
		sef.addInterest(interest3, s2);
		sef.addInterest(interest4, s3);
		em.getTransaction().commit();
		List<Interest> result = interestFacade.getOtherInterests(s1);
		assertTrue(result.contains(interest1) && result.contains(interest2) && result.contains(interest3) && result.contains(interest4));
		
	}
	
	@Test
	public void testgetNonAssociatedInterests(){
		em.getTransaction().begin();
		SocialEntity s1 = sef.createSocialEntity("entity1","entity", "entity4@entities.com");
		em.persist(s1);
		em.getTransaction().commit();
		assertNotNull(interestFacade.getNonAssociatedInterests(s1));
		
	}
	
	
}
