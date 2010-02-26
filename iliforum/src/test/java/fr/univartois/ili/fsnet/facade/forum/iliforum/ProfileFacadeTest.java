package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ProfileFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;


/**
 * 
 * Change password and change profile tests
 * 
 * @author Geoffrey Boulay
 *
 */
public class ProfileFacadeTest {
	
	
	private EntityManager em;
	private SocialEntityFacade sef;
	private ProfileFacade pf;
	private SocialEntity toto;
	private Address addr = new Address("Rue Jean souvraz", "Lens");
	private Date birthDay = new Date();
	private String genaratePassword;
	
	/**
	 * inisialisation
	 */
	@Before
	public void setUp() {
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("TestPU");
			em = emf.createEntityManager();
			sef = new SocialEntityFacade(em);
			pf = new ProfileFacade(em);
			em.getTransaction().begin();
			toto=sef.createSocialEntity("toto", "titi", "toto@titi.fr");
			em.getTransaction().commit();
			genaratePassword = Encryption.generateRandomPassword();
			Encryption.getEncodedPassword(genaratePassword);
			em.getTransaction().begin();
			toto.setPassword(Encryption.getEncodedPassword(genaratePassword));
			em.merge(toto);
			em.getTransaction().commit();
	}
	
	/**
	 * delete social entity test
	 */
	@After
	public void delete(){
		em.getTransaction().begin();
		sef.deleteSocialEntity(toto);
		em.getTransaction().commit();
	}
	
	/**
	 * change profile Test
	 */
	@Test
	public void changeProfileTest(){
		em.getTransaction().begin();
		pf.editProfile(toto, "tutu", "titi", addr, birthDay, "male", "jobless", "titi@toto.fr", "0123456789");
		em.getTransaction().commit();
		em.getTransaction().begin();
		SocialEntity totoSearch = sef.getSocialEntity(toto.getId());
		em.getTransaction().commit();
		assertEquals(toto, totoSearch);
		assertEquals(toto.getId(), totoSearch.getId());
		assertEquals("tutu", totoSearch.getName());
		assertEquals("titi",totoSearch.getFirstName());
		assertEquals(addr,totoSearch.getAddress());
		assertEquals(birthDay,totoSearch.getBirthDate());
		assertEquals("male",totoSearch.getSex());
		assertEquals("jobless", totoSearch.getProfession());
		assertEquals("titi@toto.fr",totoSearch.getEmail());
		assertEquals("0123456789", totoSearch.getPhone());
	}
	
	/**
	 * correct change password test
	 */
	@Test
	public void changePasswordTest(){
		assertEquals(Encryption.getEncodedPassword(genaratePassword),toto.getPassword());
		em.getTransaction().begin();
		boolean ok = pf.changePassword(toto, genaratePassword, "totoPwd");
		em.getTransaction().commit();
		em.getTransaction().begin();
		SocialEntity totoSearch = sef.getSocialEntity(toto.getId());
		em.getTransaction().commit();
		assertEquals(totoSearch.getPassword(), Encryption.getEncodedPassword("totoPwd"));
		assertTrue(ok);
	}
	
	/**
	 * uncorrect change password test
	 */
	@Test
	public void uncorrectChangePasswordTest(){
		em.getTransaction().begin();
		boolean ok = pf.changePassword(toto, toto.getPassword()+"titititit", "totoPwd");
		em.getTransaction().commit();
		assertFalse(ok);
		em.getTransaction().begin();
		SocialEntity totoSearch = sef.getSocialEntity(toto.getId());
		em.getTransaction().commit();
		assertEquals(Encryption.getEncodedPassword(genaratePassword), totoSearch.getPassword());
	}
	
}
