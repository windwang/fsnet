package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class EntiteSocialeTest {

	private EntityManager em;

	@Before
	public void setUp() {
		em = TestEntityManagerProvider.getInstance().getEntityManager();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testPersist() {
		EntiteSociale ent = new EntiteSociale("toto", "tata", "toto@gmail.com");
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		int monId = ent.getId();
		assertNotNull("id not null", monId);
	}

	@Test
	public void testUpdate() {
		EntiteSociale ent = new EntiteSociale("titi", "tata", "toto@gmail.com");
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		assertEquals(ent.getEmail(), "toto@gmail.com");
		ent.setEmail("tata@gmail.com");
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		int monId = ent.getId();
		assertNotNull("id not null", monId);
		assertEquals(ent.getEmail(), "tata@gmail.com");
	}

	@Test
	public void testDelete() {
		EntiteSociale ent1 = new EntiteSociale("tata", "tata", "tata@gmail.com");
		EntiteSociale ent2 = new EntiteSociale("toto", "toto", "toto@gmail.com");
		EntiteSociale ent3 = new EntiteSociale("titi", "titi", "titi@gmail.com");

		EntiteSociale[] lesEntites = { ent1, ent2, ent3 };
		em.getTransaction().begin();
		for (EntiteSociale ent : lesEntites) {
			em.persist(ent);
		}
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.remove(ent2);
		em.getTransaction().commit();
		assertNull(em.find(EntiteSociale.class, ent2.getId()));

	}

	// @Test
	// public void testFindById(){
	//		
	// EntiteSociale ent = em.find(EntiteSociale.class, 1);
	//			
	// assertEquals(ent.getMail(), "toto@gmail.com");
	//           
	//		
	// }
}
