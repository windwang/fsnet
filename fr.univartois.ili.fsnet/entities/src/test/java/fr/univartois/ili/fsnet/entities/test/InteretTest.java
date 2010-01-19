package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Interet;
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
		EntiteSociale ent1 = new EntiteSociale("toto", "tutu", "totu@gmail.com");
		EntiteSociale ent2 = new EntiteSociale("toto2", "tutu2", "totu2@gmail.com");
		List<EntiteSociale> lesEntites = new ArrayList<EntiteSociale>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interet inte = new Interet(lesEntites,"java");
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();
		Interet inte2 = em.find(Interet.class, inte.getId());
		assertEquals(inte2.getId(),inte.getId());
		assertEquals(inte2.getLesEntites(),inte.getLesEntites());
		assertEquals(inte2.getNomInteret(),inte.getNomInteret());
	}
	
	@Test(expected = RollbackException.class)
	/**
	 * Check that the name of interet can not be null
	 */
	public void testNomInteretIsNotNull() {
		EntiteSociale ent1 = new EntiteSociale("toto", "tutu", "totu@gmail.com");
		EntiteSociale ent2 = new EntiteSociale("toto2", "tutu2", "totu2@gmail.com");
		List<EntiteSociale> lesEntites = new ArrayList<EntiteSociale>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interet inte = new Interet(lesEntites,null);
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();	
	}
	
	@Test(expected = RollbackException.class)
	/**
	 * Check that the name of interet should be unique
	 */
	public void testNomInteretIsUnique() {
		EntiteSociale ent1 = new EntiteSociale("toto", "tutu", "totu@gmail.com");
		EntiteSociale ent2 = new EntiteSociale("toto2", "tutu2", "totu2@gmail.com");
		List<EntiteSociale> lesEntites = new ArrayList<EntiteSociale>();
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		lesEntites.add(ent1);
		em.getTransaction().begin();
		em.persist(ent2);
		em.getTransaction().commit();
		lesEntites.add(ent2);
		Interet inte = new Interet(lesEntites,"java");
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();	
		Interet inte2 = new Interet(lesEntites,"java");
		em.getTransaction().begin();
		em.persist(inte2);
		em.getTransaction().commit();	
	}
}