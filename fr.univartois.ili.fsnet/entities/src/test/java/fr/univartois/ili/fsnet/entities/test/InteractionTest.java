package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class InteractionTest {

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
		Interaction inter = new Interaction(true, null, null, null);
		em.getTransaction().begin();
		em.persist(inter);
		em.getTransaction().commit();
		int monId = inter.getId();
		assertNotNull("id not null", monId);
	}
}
