package fr.univartois.ili.fsnet.entities.test;


import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.GroupeDInteret;

public class GroupeDInteretTest {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@Before
	public void setUp() {
		System.err.println("Le before est execute");
		emf = Persistence.createEntityManagerFactory("fsnetjpa");
		em = emf.createEntityManager();
	}

	@After
	public void tearDown() {
		System.err.println("Le after est execute");
		if (em != null) {
			em.close();
		}
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersist() {
		System.err.println("Le test est execute");
		GroupeDInteret groupe = new GroupeDInteret("ma communaute",null,null);
		em.getTransaction().begin();
		em.persist(groupe);
		em.getTransaction().commit();
		int monId = groupe.getId();
		assertNotNull("id not null", monId);
	}
}
