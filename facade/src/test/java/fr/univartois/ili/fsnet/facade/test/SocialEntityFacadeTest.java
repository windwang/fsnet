package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

public class SocialEntityFacadeTest {

	private EntityManager em;
	private SocialEntityFacade sef;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		sef = new SocialEntityFacade(em);
	}

	@Test
	public void testCreate() {
		String name = "name";
		String firstName = "first";
		String email = "email@email.com";
		em.getTransaction().begin();
		SocialEntity es = sef.createSocialEntity(name, firstName, email);
		em.getTransaction().commit();
		SocialEntity esp = em.find(SocialEntity.class, es.getId());
		assertEquals(es, esp);
	}

	@Test
	public void testGetSocialEntity() {
		String name = "aaaaa";
		String firstName = "bbbbbb";
		String email = "ab@email.com";
		
		em.getTransaction().begin();
		
		SocialEntity es = sef.createSocialEntity(name, firstName, email);
		
		em.getTransaction().commit();
		
		SocialEntity esp = sef.getSocialEntity(es.getId());
		
		assertEquals(es, esp);
	}
	
	@Test
	public void testSearchSocialEntity(){
		//TODO test the SerachSocialEntity method
	}

	@Test
	public void testSearchSocialEntitys(){
		//TODO test the SerachSocialEntitys method
	}

	@Test
	public void testAddInterest(){
		InterestFacade interestFacade = new InterestFacade(em);
		String interestName = "interest";
		String name = "ss";
		String firstName = "zz";
		String email = "sz@email.com";
		em.getTransaction().begin();
		SocialEntity es = sef.createSocialEntity(name, firstName, email);
		Interest interest = interestFacade.createInterest(interestName);
		sef.addInterest(interest, es);
		em.getTransaction().commit();
		assertEquals(1, es.getInterests().size());
		assertTrue(es.getInterests().contains(interest));
	}

	@Test
	public void testRemoveInterest(){
		InterestFacade interestFacade = new InterestFacade(em);
		String interestName = "fun";
		String name = "gg";
		String firstName = "kk";
		String email = "gk@email.com";
		em.getTransaction().begin();
		SocialEntity es = sef.createSocialEntity(name, firstName, email);
		Interest interest = interestFacade.createInterest(interestName);
		sef.addInterest(interest, es);
		sef.removeInterest(interest, es);
		em.getTransaction().commit();
		assertEquals(0, es.getInterests().size());
		assertTrue(!es.getInterests().contains(interest));
	}

	@Test
	public void testAddFavoriteInteraction(){
		//TODO test the SerachSocialEntitys method
	}

	@Test
	public void testRemoveFavoriteInteraction(){
		//TODO test the SerachSocialEntitys method
	}

}
