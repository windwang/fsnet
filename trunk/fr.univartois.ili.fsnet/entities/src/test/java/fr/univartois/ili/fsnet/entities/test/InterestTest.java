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
import java.util.HashSet;
import java.util.Set;


public class InterestTest {

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
		Set<SocialEntity> lesEntites = new HashSet<SocialEntity>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interest inte = new Interest("java");
                inte.setEntities(lesEntites);
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();
		Interest inte2 = em.find(Interest.class, inte.getId());
		assertEquals(inte2.getId(),inte.getId());
		assertEquals(inte2.getEntities(),inte.getEntities());
		assertEquals(inte2.getName(),inte.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	/**
	 * Check that the name of interet can not be null
	 */
	public void testNomInteretIsNotNull() {
		Interest inte = new Interest(null);
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
		Set<SocialEntity> lesEntites = new HashSet<SocialEntity>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interest inte = new Interest("java");
                inte.setEntities(lesEntites);
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();	
		Interest inte2 = new Interest("java");
                inte2.setEntities(lesEntites);
		em.getTransaction().begin();
		em.persist(inte2);
		em.getTransaction().commit();	
	}
}