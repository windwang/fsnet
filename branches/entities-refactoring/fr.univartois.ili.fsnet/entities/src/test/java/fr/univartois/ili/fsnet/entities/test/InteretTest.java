package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;


public class InteretTest {

	private EntityManager em;
	
	@Before
	public void setUp() {
		em = TestEntityManagerProvider.getInstance().getEntityManager();
	}

	@After
	public void tearDown() {

	}

	@Test
	/**
	 * Check that if we can persiste un entity interet
	 */
	public void testPersist() {
		SocialEntity ent1 = new SocialEntity("toto", "tutu", "interestpersist@gmail.com");
		SocialEntity ent2 = new SocialEntity("toto2", "tutu2", "totu2@gmail.com");
		List<SocialEntity> lesEntites = new ArrayList<SocialEntity>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interest inte = new Interest(lesEntites,"java");
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();
		Interest inte2 = em.find(Interest.class, inte.getId());
		assertEquals(inte2.getId(),inte.getId());
		assertEquals(inte2.getLesEntites(),inte.getLesEntites());
		assertEquals(inte2.getNomInteret(),inte.getNomInteret());
	}
	
	@Test(expected = RollbackException.class)
	/**
	 * Check that the name of interet can not be null
	 */
	public void testNomInteretIsNotNull() {
		SocialEntity ent1 = new SocialEntity("toto", "tutu", "interestNom1totu@gmail.com");
		SocialEntity ent2 = new SocialEntity("toto2", "tutu2", "interestNom2@gmail.com");
		List<SocialEntity> lesEntites = new ArrayList<SocialEntity>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interest inte = new Interest(lesEntites,null);
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();	
	}
	
	@Test(expected = RollbackException.class)
	/**
	 * Check that the name of interet should be unique
	 */
	public void testNomInteretIsUnique() {
		SocialEntity ent1 = new SocialEntity("toto", "tutu", "interestUnic1@gmail.com");
		SocialEntity ent2 = new SocialEntity("toto2", "tutu2", "interestUnic2@gmail.com");
		List<SocialEntity> lesEntites = new ArrayList<SocialEntity>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interest inte = new Interest(lesEntites,"java");
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();	
		Interest inte2 = new Interest(lesEntites,"java");
		em.getTransaction().begin();
		em.persist(inte2);
		em.getTransaction().commit();	
	}
}