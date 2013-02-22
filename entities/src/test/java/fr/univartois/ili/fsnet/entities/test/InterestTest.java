package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.eclipse.persistence.sessions.factories.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class InterestTest {

	private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
	}

	/**
	 * Check that we can persist an entity interest
	 */
	@Test
	public void testPersist() {
		// Create entities
		Interest inte = new Interest("java");
		SocialEntity ent1 = new SocialEntity("ent1", "test", "ent1@gmail.com");
		SocialEntity ent2 = new SocialEntity("ent2", "test", "ent2@gmail.com");
		inte.getEntities().add(ent1);
		inte.getEntities().add(ent2);
		Interest child1 = new Interest("java 1.5");
		Interest child2 = new Interest("java 1.7");
		inte.getChildrenInterests().add(child1);
		inte.getChildrenInterests().add(child2);

		// Persist entities
		em.getTransaction().begin();
		em.persist(ent1);
		em.persist(ent2);
		em.persist(child1);
		em.persist(child2);
		em.persist(inte);
		em.getTransaction().commit();

		// Tests
		Interest inte2 = em.find(Interest.class, inte.getId());
		assertEquals(inte2.getId(), inte.getId());
		assertEquals(inte2.getEntities(), inte.getEntities());
		assertEquals(inte2.getChildrenInterests(), inte.getChildrenInterests());
		assertEquals(inte2.getName(), inte.getName());

		// Remove entities
		em.getTransaction().begin();
		em.remove(ent1);
		em.remove(ent2);
		em.remove(child1);
		em.remove(child2);
		em.remove(inte);
		em.getTransaction().commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNameIsNotNullWithConstructor2() {
		new Interest(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNameIsNotNullWithConstructor3() {
		Interest inte = new Interest("java");
		new Interest(null, inte);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParentIsNotNullWithConstructor3() {
		new Interest("java", null);
	}

	@Test
	public void testCreateWithParent() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java", prog);

		// The children knows is parent
		assertEquals(prog, java.getParentInterest());

		// The parent knows is child
		assertTrue(prog.getChildrenInterests().contains(java));
	}

	@Test(expected = RollbackException.class)
	public void testNameInterestIsNotNullWhenPeristed() {
		Interest inte = new Interest();
		inte.setName(null);

		// Try to insert in the DB
		em.getTransaction().begin();
		em.persist(inte);
		em.getTransaction().commit();

		// Remove the entity (if it was inserted in the DB)
		em.getTransaction().begin();
		em.remove(inte);
		em.getTransaction().commit();
	}

	@Test(expected = RollbackException.class)
	public void testInterestNameIsUnique() {
		Interest inte1 = new Interest("java");
		Interest inte2 = new Interest("java");

		// Insert inte1 in the DB
		em.getTransaction().begin();
		em.persist(inte1);
		em.getTransaction().commit();

		try {
			// Try insert inte2 in the DB
			em.getTransaction().begin();
			em.persist(inte2);
			em.getTransaction().commit();

			// Remove the entity inte2 (if it was inserted in the DB)
			em.getTransaction().begin();
			em.remove(inte2);
			em.getTransaction().commit();
		} finally {

			// Remove the entity inte1 in all cases
			em.getTransaction().begin();
			inte1 = em.merge(inte1);
			em.remove(inte1);
			em.getTransaction().commit();
		}
	}

	@Test
	public void testAddParentMustModifyParentChildren() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java");
		java.setParentInterest(prog);
	}

	@Ignore
	@Test
	public void testAddChild() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java");
		prog.addChildrenInterest(java);

		// The children knows is new parent
		assertEquals(prog, java.getParentInterest());

		// The parent knows is new child
		assertTrue(prog.getChildrenInterests().contains(java));
	}

	
	@Ignore@Test
	public void testAddParent() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java");
		java.setParentInterest(prog);

		// The children knows is new parent
		assertEquals(prog, java.getParentInterest());

		// The parent knows is new child
		assertTrue(prog.getChildrenInterests().contains(java));
	}

	@Test
	public void testRemoveChild() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java");
		prog.addChildrenInterest(java);
		prog.removeChildrenInterest(java);

		// The children forgot is parent
		assertEquals(null, java.getParentInterest());

		// The parent forgot is child
		assertFalse(prog.getChildrenInterests().contains(java));
	}

	@Test
	public void testRemoveParent() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java");
		java.setParentInterest(prog);
		java.setParentInterest(null);

		// The children forgot is parent
		assertEquals(null, java.getParentInterest());

		// The parent forgot is child
		assertFalse(prog.getChildrenInterests().contains(java));
	}

	@Ignore
	@Test
	public void testChangeParent() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java");
		Interest classes = new Interest("classes");
		java.setParentInterest(prog);
		java.setParentInterest(classes);

		// The parent 'prog' forgot is child
		assertFalse(prog.getChildrenInterests().contains(java));

		// The parent 'classes' know is new child
		assertTrue(classes.getChildrenInterests().contains(java));
	}

	@Test
	public void testHashCode() {
		Interest test1 = new Interest("test");
		Interest test2 = new Interest("test");
		assertEquals(test1.hashCode(), test2.hashCode());
	}

	@Test
	public void testHashCodeWithNull() {
		Interest test1 = new Interest();
		Interest test2 = new Interest();
		assertEquals(test1.hashCode(), test2.hashCode());
	}

	@Test
	public void testEqualsTrueCases() {
		Interest test1 = new Interest();
		Interest test2 = new Interest();
		assertTrue(test1.equals(test2));
		assertTrue(test2.equals(test1));

		test1.setName("test");
		test2.setName("test");
		assertTrue(test1.equals(test2));
		assertTrue(test2.equals(test1));

		assertTrue(test1.equals(test1));
	}

	@Test
	public void testEqualsFalseCases() {
		Interest test1 = new Interest();
		Interest test2 = new Interest("java");

		// One name is null
		assertFalse(test1.equals(test2));
		assertFalse(test2.equals(test1));

		// The compared object is null or not an interest
		assertFalse(test1.equals(null));
		assertFalse(test1.equals(new String("test")));
	}

	@Test
	public void testRemoveParentAndReadd() {
		Interest prog = new Interest("prog");
		Interest java = new Interest("java");
		java.setParentInterest(prog);
		java.setParentInterest(null);

		// The children forgot is parent
		assertEquals(null, java.getParentInterest());

		// The parent forgot is child
		assertFalse(prog.getChildrenInterests().contains(java));
	}

	@Test
	public void testCreateWithParentAndPersist() {
		Interest prog = new Interest("prog2");
		Interest java = new Interest("java2", prog);

		// The children knows is parent
		assertEquals(prog, java.getParentInterest());
		// The parent knows is child
		assertTrue(prog.getChildrenInterests().contains(java));
		
		em.getTransaction().begin();
		em.persist(prog);
		em.persist(java);
		em.getTransaction().commit();
		em.clear();
		assertFalse(em.contains(prog));
		assertTrue(prog.getId()!=0);
		assertTrue(java.getId()!=0);
		Interest inte = em.createQuery("Select i from Interest i where i.id=:id",Interest.class).setParameter("id", prog.getId()).getSingleResult();
		assertNotNull(inte);
		inte.setName("toto");
		em.refresh(inte);
		assertEquals("prog2", inte.getName());
		assertEquals(1, inte.getChildrenInterests().size());
		assertTrue(inte.getChildrenInterests().contains(java));
	}
}
