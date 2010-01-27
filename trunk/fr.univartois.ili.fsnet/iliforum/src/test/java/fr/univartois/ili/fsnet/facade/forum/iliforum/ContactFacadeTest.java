package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
*
* @author Aurelien Legrand
*/
public class ContactFacadeTest {

	private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("TestPU");
		em = entityManagerFactory.createEntityManager();

	}

	@Test(expected = IllegalStateException.class)
	public void acceptWithoutAskingTest() {
		ContactFacade cf = new ContactFacade(em);
		SocialEntityFacade sef= new SocialEntityFacade(em);
		SocialEntity se1 = sef.createSocialEntity("toto", "titi", "toto@gmail.com");
		SocialEntity se2 = sef.createSocialEntity("tata", "titi", "tata@gmail.com");
		em.getTransaction().begin();
		em.persist(se1);
		em.persist(se2);
		em.getTransaction().commit();
		cf.acceptContact(se1, se2);
	}

	@Test(expected = IllegalStateException.class)
	public void alreadyAskedTest() {
		ContactFacade cf = new ContactFacade(em);
		SocialEntityFacade sef= new SocialEntityFacade(em);
		SocialEntity se1 = sef.createSocialEntity("toto", "titi", "titi@gmail.com");
		SocialEntity se2 = sef.createSocialEntity("tata", "titi", "taytaygmail.com");
		em.getTransaction().begin();
		em.persist(se1);
		em.persist(se2);
		em.getTransaction().commit();
		cf.askContact(se1, se2);
		cf.askContact(se2, se1);
	}

	@Test
	public void deleteCascadeTest() {
		em.getTransaction().begin();
		ContactFacade cf = new ContactFacade(em);
		SocialEntityFacade sef= new SocialEntityFacade(em);
		SocialEntity se1 = sef.createSocialEntity("toto", "titi", "teitei@gmail.com");
		SocialEntity se2 = sef.createSocialEntity("tata", "titi", "teyteygmail.com");
		em.persist(se1);
		em.persist(se2);
		cf.askContact(se1, se2);
		cf.acceptContact(se2, se1);
		cf.deleteContact(se2.getId());
		em.getTransaction().commit();
		assertTrue(!se1.getContacts().contains(se2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void askHimselfTest(){
		em.getTransaction().begin();
		ContactFacade cf = new ContactFacade(em);
		SocialEntityFacade sef= new SocialEntityFacade(em);
		SocialEntity se1 = sef.createSocialEntity("toto", "titi", "teitei@gmail.com");
		em.persist(se1);
		cf.askContact(se1, se1);
		em.getTransaction().commit();
		
	}
	
	@Test
	public void removeContactTest() {
		em.getTransaction().begin();
		ContactFacade cf = new ContactFacade(em);
		SocialEntityFacade sef= new SocialEntityFacade(em);
		SocialEntity se1 = sef.createSocialEntity("toto", "titi", "toto1@gmail.com");
		SocialEntity se2 = sef.createSocialEntity("tata", "titi", "tata2gmail.com");
		em.persist(se1);
		em.persist(se2);
		cf.askContact(se1, se2);
		cf.acceptContact(se2, se1);
		cf.removeContact(se1, se2);
		em.getTransaction().commit();
		assertTrue(!se1.getContacts().contains(se2));
	}
	
	@Test(expected = IllegalStateException.class)
	public void alreadyInContactTest(){
		em.getTransaction().begin();
		ContactFacade cf = new ContactFacade(em);
		SocialEntityFacade sef= new SocialEntityFacade(em);
		SocialEntity se1 = sef.createSocialEntity("toitoi", "titi", "toitoi@gmail.com");
		SocialEntity se2 = sef.createSocialEntity("taitai", "titi", "taitaigmail.com");
		cf.askContact(se1, se2);
		cf.acceptContact(se2, se1);
		cf.askContact(se1, se2);
		em.getTransaction().commit();
	}
	
	@Test(expected = IllegalStateException.class)
	public void refuseSocialEntityAlreadyInContact(){
		em.getTransaction().begin();
		ContactFacade cf = new ContactFacade(em);
		SocialEntityFacade sef= new SocialEntityFacade(em);
		SocialEntity se1 = sef.createSocialEntity("bobo", "bibi", "bobo@gmail.com");
		SocialEntity se2 = sef.createSocialEntity("baba", "bibi", "babagmail.com");
		cf.askContact(se1, se2);
		cf.acceptContact(se2, se1);
		cf.refuseContact(se1, se2);
	}
}
