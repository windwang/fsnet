package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;

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
		SocialEntity esp = em.find(SocialEntity.class, es.getId());
		em.getTransaction().commit();
		assertEquals(es, esp);
	}

	@Test
	public void testGetSocialEntity() {
		String name = "aaaaa";
		String firstName = "bbbbbb";
		String email = "ab@email.com";
		em.getTransaction().begin();
		SocialEntity es = sef.createSocialEntity(name, firstName, email);
		SocialEntity esp = sef.getSocialEntity(es.getId());
		em.getTransaction().commit();
		assertEquals(es, esp);
	}

	@Test
	public void testDeleteSocialEntity(){
		String name = "cc";
		String firstName = "dd";
		String email = "cd@email.com";
		em.getTransaction().begin();
		SocialEntity es = sef.createSocialEntity(name, firstName, email);
		sef.deleteSocialEntity(es.getId());
		em.getTransaction().commit();
		assertNull(em.find(SocialEntity.class, es.getId()));
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
		sef.addInterest(interest.getId(), es.getId());
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
		sef.addInterest(interest.getId(), es.getId());
		sef.removeInterest(interest.getId(), es.getId());
		em.getTransaction().commit();
		assertEquals(0, es.getInterests().size());
		assertTrue(!es.getInterests().contains(interest));
	}

}
