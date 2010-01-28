package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;
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

public class InterestFacadeTest {

	private EntityManager em;
	private InterestFacade interestFacade;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		interestFacade = new InterestFacade(em);
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
		interestFacade.deleteInterest(interest.getId());
		em.getTransaction().commit();
	}

	@Test
	public void testGetInterest(){
		String interestName = "Musique";
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		Interest interestBis = interestFacade.getInterest(interest.getId());
		em.getTransaction().commit();
		assertTrue(interest.equals(interestBis));
		em.getTransaction().begin();
		interestFacade.deleteInterest(interest.getId());
		em.getTransaction().commit();
	}

	@Test
	public void testModifyInterest(){
		String interestName = "Java";
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		interestFacade.modifyInterest("Java6", interest.getId());
		Interest interestBis = interestFacade.getInterest(interest.getId());
		em.getTransaction().commit();
		assertEquals("Java6", interestBis.getName());
		em.getTransaction().begin();
		interestFacade.deleteInterest(interest.getId());
		em.getTransaction().commit();
	}

	@Test
	public void testDeleteInterest(){
		String interestName = "JEE";
		em.getTransaction().begin();
		Interest interest = interestFacade.createInterest(interestName);
		interestFacade.deleteInterest(interest.getId());
		em.getTransaction().commit();
		assertNull(em.find(SocialEntity.class, interest.getId()));
	}

	@Test
	public void testGetInterests(){
		String interestName1 = "Interest1";
		String interestName2 = "Interest2";
		String interestName3 = "Interest3";

		em.getTransaction().begin();
		Interest interest1 = interestFacade.createInterest(interestName1);
		Interest interest2 = interestFacade.createInterest(interestName2);
		Interest interest3 = interestFacade.createInterest(interestName3);
		List<Interest> interests = interestFacade.getInterests();
		em.getTransaction().commit();
		assertTrue(interests.contains(interest1) && interests.contains(interest2) && interests.contains(interest3));
	}
}
