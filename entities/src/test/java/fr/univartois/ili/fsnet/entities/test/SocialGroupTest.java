package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;

/**
 * @author SAID Mohamed <simo.said09 at gmail.com>
 * @author stephane gronowski
 */
public class SocialGroupTest {
	private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testPersist() {
		final String groupName = "ATOS";
		final String lastName = "SAID";
		final String firstName = "Mohamed";
		final String mail = "simo.said09@gmail.com";
		SocialEntity ent = new SocialEntity(lastName, firstName, mail);
		SocialGroup sg = new SocialGroup(ent, groupName, "descrption");
		sg.addRight(Right.ADD_ADMIN_GROUP);
		sg.addRight(Right.MODIFY_PROFIL);
		em.getTransaction().begin();
		em.persist(ent);
		em.persist(sg);
		em.getTransaction().commit();
		SocialGroup sg2 = em.find(SocialGroup.class, sg.getId());
		assertEquals(sg2.getId(), sg.getId());
		assertEquals(sg2.getName(), sg.getName());
		assertEquals(sg2, sg);
		assertEquals(sg2.getMasterGroup().getId(), ent.getId());
		assertEquals(sg2.getMasterGroup().getName(), lastName);
		assertEquals(sg2.getMasterGroup().getFirstName(), firstName);
		assertEquals(sg2.getMasterGroup().getEmail(), mail);
		assertTrue("right is not persisted correctly",
				sg2.isAuthorized(Right.ADD_ADMIN_GROUP));
		assertTrue("right is not persisted correctly",
				sg2.isAuthorized(Right.MODIFY_PROFIL));

	}

	@Test
	public void testUpdate() {
		SocialEntity masterGroup = new SocialEntity("titi", "tata",
				"esnkbupdate@gmail.com");
		SocialEntity masterGroup2 = new SocialEntity("simo", "said",
				"simkjbkosimo@gmail.com");

		SocialGroup sg = new SocialGroup(masterGroup, "ATOS2", "descrption");
		em.getTransaction().begin();
		em.persist(masterGroup);
		em.persist(sg);
		em.getTransaction().commit();
		assertEquals(sg.getName(), "ATOS2");
		assertEquals(sg.getMasterGroup(), masterGroup);
		assertFalse("no right actually", sg.isAuthorized(Right.ADD_ADMIN_GROUP));

		sg.setName("ATOS22");
		sg.setMasterGroup(masterGroup2);
		sg.addRight(Right.ADD_ADMIN_GROUP);
		em.getTransaction().begin();
		em.merge(sg);
		em.getTransaction().commit();

		sg = em.find(SocialGroup.class, sg.getId());
		assertNotNull(sg);
		assertEquals(sg.getName(), "ATOS22");
		assertEquals(sg.getMasterGroup(), masterGroup2);
		assertTrue("right not update", sg.isAuthorized(Right.ADD_ADMIN_GROUP));
	}

	@Test
	public void testDelete() {
		SocialEntity masterGroup1 = new SocialEntity("titi", "titi",
				"manknil11@gmail.com");
		SocialGroup sg1 = new SocialGroup(masterGroup1, "Group11", "descrption");
		SocialEntity masterGroup2 = new SocialEntity("tyty", "tyty",
				"mavbvjil22@gmail.com");
		SocialGroup sg2 = new SocialGroup(masterGroup2, "Group22", "descrption");
		SocialEntity masterGroup3 = new SocialEntity("tutu", "tutu",
				"maijbkl33@gmail.com");
		SocialGroup sg3 = new SocialGroup(masterGroup2, "Group33", "descrption");
		SocialEntity[] lesEntities = { masterGroup1, masterGroup2, masterGroup3 };
		SocialGroup[] lesGroups = { sg1, sg2, sg3 };
		em.getTransaction().begin();
		for (int i = 0; i < lesGroups.length; i++) {
			em.persist(lesEntities[i]);
			em.persist(lesGroups[i]);
		}
		em.getTransaction().commit();
		assertNotNull(em.find(SocialGroup.class, sg1.getId()));
		assertNotNull(em.find(SocialGroup.class, sg2.getId()));
		assertNotNull(em.find(SocialGroup.class, sg3.getId()));
		em.getTransaction().begin();
		em.remove(sg2);
		em.remove(sg1);
		em.getTransaction().commit();
		assertNull(em.find(SocialGroup.class, sg1.getId()));
		assertNull(em.find(SocialGroup.class, sg2.getId()));
		assertNotNull(em.find(SocialGroup.class, sg3.getId()));
	}

	@Test(expected = RollbackException.class)
	public void testUniqueName() {

		SocialEntity masterGroup1 = new SocialEntity("titi", "titi",
				"makjbgk,khhikl1@gmail.com");
		SocialEntity masterGroup2 = new SocialEntity("tyty", "tyty",
				"mailjonljnbh2@gmail.com");
		SocialGroup sg1 = new SocialGroup(masterGroup1, "NameGroup",
				"descrption");

		SocialGroup sg2 = new SocialGroup(masterGroup2, "NameGroup",
				"descrption");

		em.getTransaction().begin();
		em.persist(masterGroup1);
		em.persist(masterGroup2);
		em.persist(sg1);
		em.persist(sg2);
		em.getTransaction().commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequieredName() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi",
				"mailvbjkgc1@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, null, "descrption");

		em.getTransaction().begin();
		em.persist(sg);
		em.getTransaction().commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequieredMasterGroup() {
		SocialEntity masterGroup = null;
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");

		em.getTransaction().begin();
		em.persist(sg);
		em.getTransaction().commit();
	}

	@Test
	public void testAddRight() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi",
				"mailvbjkgc1@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");

		sg.addRight(Right.ADD_ADMIN_GROUP);
		sg.addRight(Right.ADD_EVENT);
		sg.addRight(Right.MODIFY_PICTURE);

		assertTrue("should be authorized",
				sg.isAuthorized(Right.ADD_ADMIN_GROUP));
		assertTrue("should be authorized", sg.isAuthorized(Right.ADD_EVENT));
		assertTrue("should be authorized",
				sg.isAuthorized(Right.MODIFY_PICTURE));
	}

	@Test
	public void testRemoveRight() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi",
				"mailvbjkgc1@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");

		sg.addRight(Right.ADD_ADMIN_GROUP);
		sg.addRight(Right.ADD_EVENT);
		sg.addRight(Right.MODIFY_PICTURE);
		sg.addRight(Right.RECEIVED_MESSAGE);
		sg.addRight(Right.ANSWER_MESSAGE);

		sg.removeRight(Right.ADD_ADMIN_GROUP);
		sg.removeRight(Right.MODIFY_PICTURE);

		assertFalse("should'nt be authorized",
				sg.isAuthorized(Right.ADD_ADMIN_GROUP));
		assertTrue("should be authorized",
				sg.isAuthorized(Right.ANSWER_MESSAGE));
		assertFalse("should'nt be authorized",
				sg.isAuthorized(Right.MODIFY_PICTURE));
	}

	@Test
	public void testIsAuthorized() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi",
				"mailvbjkgc1@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");
		SocialGroup father = new SocialGroup(masterGroup, "G", "descrption");

		sg.addRight(Right.ADD_ADMIN_GROUP);
		sg.addRight(Right.ADD_EVENT);
		sg.addRight(Right.MODIFY_PICTURE);
		father.addRight(Right.RECEIVED_MESSAGE);
		father.addRight(Right.ANSWER_MESSAGE);

		sg.setGroup(father);

		assertFalse("father is not authorized",
				father.isAuthorized(Right.ADD_ADMIN_GROUP));
		assertTrue("should be authorized",
				sg.isAuthorized(Right.ADD_ADMIN_GROUP));
		assertTrue("should be authorized, has the right of the father",
				sg.isAuthorized(Right.ANSWER_MESSAGE));

		assertTrue("should be authorized",
				father.isAuthorized(Right.RECEIVED_MESSAGE));
		assertTrue("should be authorized, has the right of the father",
				sg.isAuthorized(Right.RECEIVED_MESSAGE));

		assertFalse("should'nt be authorized",
				father.isAuthorized(Right.MODIFY_PICTURE));
	}

}
