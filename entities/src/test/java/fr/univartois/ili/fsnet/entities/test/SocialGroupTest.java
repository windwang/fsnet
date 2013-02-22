package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
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
		EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
	}

	@Test
	public void testSetAndGetMethodForDescription() {
		final String description = "FansDeFsnet";
		SocialGroup sg = new SocialGroup();
		sg.setDescription(description);
		assertEquals(description, sg.getDescription());
	}

	@Test
	public void testSetAndGetMethodForIsEnabled() {
		final boolean isEnabled = true;
		SocialGroup sg = new SocialGroup();
		sg.setIsEnabled(isEnabled);
		assertEquals(isEnabled, sg.getIsEnabled());
	}

	@Test
	public void testSetAndGetMethodsForColor() {
		final String color = "#40A497";
		SocialGroup sg = new SocialGroup();
		sg.setColor(color);
		assertEquals(color, sg.getColor());

	}

	@Test
	public void testSetAndGetMethodsForRights() {
		SocialGroup sg = new SocialGroup();
		Set<Right> rights = new HashSet<Right>();
		rights.add(Right.ADD_ANNOUNCE);
		sg.setRights(rights);
		assertEquals(rights, sg.getRights());
	}

	@Test
	public void testAddRights() {
		SocialGroup sg = new SocialGroup();
		Set<Right> rights = new HashSet<Right>();
		rights.add(Right.ADD_ANNOUNCE);
		rights.add(Right.ADD_CONSULTATION);
		sg.addRights(rights);
		assertEquals(rights, sg.getRights());
	}

	@Test
	public void testSetandGetMethodsForSocialElementsWithOnlySocialElementAtParameter() {
		final String lastName = "LeBerre";
		final String firstName = "Daniel";
		final String mail = "daniel.leberre@gmail.com";
		final String description = "pour ceux qui adorent ce projet";

		SocialEntity grp = new SocialEntity("grp", "grp", "grp@mail.fr");
		SocialGroup sgrp = new SocialGroup(grp, "fansDeFsNet", description);
		SocialElement se1 = new SocialEntity(lastName, firstName, mail);
		SocialElement se2 = new SocialEntity(lastName, firstName, mail);
		List<SocialElement> socialElements = new ArrayList<>();
		socialElements.add(se1);
		sgrp.setSocialElements(socialElements);
		assertEquals(socialElements, sgrp.getSocialElements());

		socialElements.add(se2);
		sgrp.setSocialElements(socialElements);
		assertEquals(socialElements, sgrp.getSocialElements());
	}

	@Test
	public void testSetandGetMethodsForSocialElements() {
		final String lastName = "LeBerre";
		final String firstName = "Daniel";
		final String mail = "daniel.leberre@gmail.com";
		final String descriptionfsnet = "pour ceux qui adorent le projet fsnet";
		final String descriptionsadoc = "pour ceux qui adorent le projet sadoc";
		SocialEntity masterGroup1 = new SocialEntity("toto", "titi", "manknil11@gmail.com");
		SocialEntity masterGroup2 = new SocialEntity("titi", "toto", "nilmank11@gmail.com");
		SocialGroup sg1 = new SocialGroup(masterGroup1, "fansDeFsNet", descriptionfsnet);
		SocialGroup sg2 = new SocialGroup(masterGroup2, "fansDeSadoc", descriptionsadoc);
		SocialElement se1 = new SocialEntity(lastName, firstName, mail);
		SocialElement se2 = new SocialEntity(lastName, firstName, mail);
		List<SocialElement> socialElements = new LinkedList<SocialElement>();
		socialElements.add(se1);
		sg1.setSocialElements(socialElements, sg2);
		assertEquals(socialElements, sg1.getSocialElements());

		socialElements.add(se2);
		sg1.setSocialElements(socialElements, sg2);
		assertEquals(socialElements, sg1.getSocialElements());

	}

	@Test
	public void testAddSocialElement() {
		SocialElement se1 = new SocialEntity("titi", "titi", "titi@mail.fr");

		SocialEntity masterGroup = new SocialEntity("toto", "titi", "master@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "fansDeFsNet", "pour les fans du projet");
		List<SocialElement> socialElements = new LinkedList<SocialElement>();
		socialElements.add(se1);
		sg.addSocialElement(se1);
		assertEquals(sg.getSocialElements(), socialElements);

		sg.addSocialElement(se1);
		assertEquals(sg.getSocialElements(), socialElements);
	}

	@Test
	public void testAddAllSocialElements() {
		SocialElement se1 = new SocialEntity("titi", "titi", "titi@mail.fr");
		SocialElement se2 = new SocialEntity("tutu", "tutu", "tutu@mail.fr");
		SocialEntity masterGroup = new SocialEntity("toto", "titi", "master@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "fansDeFsNet", "pour les fans du projet");
		List<SocialElement> socialElements = new LinkedList<SocialElement>();
		socialElements.add(se1);
		socialElements.add(se2);
		sg.addAllSocialElements(socialElements);
		assertEquals(sg.getSocialElements(), socialElements);
	}

	@Test
	public void testRemoveSocialElement() {
		SocialElement se1 = new SocialEntity("titi", "titi", "titi@mail.fr");
		SocialElement se2 = new SocialEntity("tutu", "tutu", "tutu@mail.fr");
		SocialEntity masterGroup = new SocialEntity("toto", "titi", "master@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "fansDeFsNet", "pour les fans du projet");

		List<SocialElement> socialElements = new LinkedList<SocialElement>();
		sg.addSocialElement(se2);
		socialElements.add(se1);
		sg.addAllSocialElements(socialElements);
		sg.removeSocialElement(se2);
		assertEquals(sg.getSocialElements(), socialElements);

	}

	@Test
	public void testRemoveAllSocialElements() {
		SocialElement se1 = new SocialEntity("titi", "titi", "titi@mail.fr");
		SocialElement se2 = new SocialEntity("tutu", "tutu", "tutu@mail.fr");
		SocialElement se3 = new SocialEntity("tata", "tata", "tata@mail.fr");
		SocialEntity masterGroup = new SocialEntity("toto", "titi", "master@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "fansDeFsNet", "pour les fans du projet");

		List<SocialElement> socialElements1 = new LinkedList<SocialElement>();
		List<SocialElement> socialElements2 = new LinkedList<SocialElement>();
		List<SocialElement> socialElements3 = new LinkedList<SocialElement>();

		socialElements1.add(se1);

		socialElements2.add(se1);
		socialElements2.add(se2);

		socialElements3.add(se2);
		socialElements3.add(se3);

		sg.addAllSocialElements(socialElements2);
		sg.removeAllSocialElements(socialElements3);
		assertEquals(socialElements1, sg.getSocialElements());
	}

	@Test
	public void testSetAndGetMethodsForName() {
		final String name = "les fans de FsNet";
		System.out.println(name);
		SocialGroup sg = new SocialGroup();
		sg.setName(name);
		System.out.println(sg.getName());
		assertEquals(name, sg.getName());
	}

	@Test
	public void testPersist() {
		final String groupName = "ATOS";
		final String lastName = "SAID";
		final String firstName = "Mohamed";
		final String mail = "simo.said09@gmail.com";
		SocialEntity ent = new SocialEntity(lastName, firstName, mail);
		SocialGroup sg = new SocialGroup(ent, groupName, "descrption");
		sg.addRight(Right.ADD_ANNOUNCE);
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
		assertTrue("right is not persisted correctly", sg2.isAuthorized(Right.ADD_ANNOUNCE));
		assertTrue("right is not persisted correctly", sg2.isAuthorized(Right.MODIFY_PROFIL));

	}

	@Test
	public void testUpdate() {
		SocialEntity masterGroup = new SocialEntity("titi", "tata", "esnkbupdate@gmail.com");
		SocialEntity masterGroup2 = new SocialEntity("simo", "said", "simkjbkosimo@gmail.com");

		SocialGroup sg = new SocialGroup(masterGroup, "ATOS2", "descrption");
		em.getTransaction().begin();
		em.persist(masterGroup);
		em.persist(sg);
		em.getTransaction().commit();
		assertEquals(sg.getName(), "ATOS2");
		assertEquals(sg.getMasterGroup(), masterGroup);
		assertFalse("no right actually", sg.isAuthorized(Right.ADD_ANNOUNCE));

		sg.setName("ATOS22");
		sg.setMasterGroup(masterGroup2);
		sg.addRight(Right.ADD_ANNOUNCE);
		em.getTransaction().begin();
		em.merge(sg);
		em.getTransaction().commit();

		sg = em.find(SocialGroup.class, sg.getId());
		assertNotNull(sg);
		assertEquals(sg.getName(), "ATOS22");
		assertEquals(sg.getMasterGroup(), masterGroup2);
		assertTrue("right not update", sg.isAuthorized(Right.ADD_ANNOUNCE));
	}

	@Test
	public void testDelete() {
		SocialEntity masterGroup1 = new SocialEntity("titi", "titi", "manknil11@gmail.com");
		SocialGroup sg1 = new SocialGroup(masterGroup1, "Group11", "descrption");
		SocialEntity masterGroup2 = new SocialEntity("tyty", "tyty", "mavbvjil22@gmail.com");
		SocialGroup sg2 = new SocialGroup(masterGroup2, "Group22", "descrption");
		SocialEntity masterGroup3 = new SocialEntity("tutu", "tutu", "maijbkl33@gmail.com");
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

		SocialEntity masterGroup1 = new SocialEntity("titi", "titi", "makjbgk,khhikl1@gmail.com");
		SocialEntity masterGroup2 = new SocialEntity("tyty", "tyty", "mailjonljnbh2@gmail.com");
		SocialGroup sg1 = new SocialGroup(masterGroup1, "NameGroup", "descrption");

		SocialGroup sg2 = new SocialGroup(masterGroup2, "NameGroup", "descrption");

		em.getTransaction().begin();
		em.persist(masterGroup1);
		em.persist(masterGroup2);
		em.persist(sg1);
		em.persist(sg2);
		em.getTransaction().commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequieredName() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi", "mailvbjkgc1@gmail.com");
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
		SocialEntity masterGroup = new SocialEntity("titi", "titi", "mailvbjkgc1@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");

		sg.addRight(Right.ADD_ANNOUNCE);
		sg.addRight(Right.ADD_EVENT);
		sg.addRight(Right.MODIFY_PICTURE);

		assertTrue("should be authorized", sg.isAuthorized(Right.ADD_ANNOUNCE));
		assertTrue("should be authorized", sg.isAuthorized(Right.ADD_EVENT));
		assertTrue("should be authorized", sg.isAuthorized(Right.MODIFY_PICTURE));
	}

	@Test
	public void testRemoveRight() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi", "mailvbjkgc1@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");

		sg.addRight(Right.ADD_ANNOUNCE);
		sg.addRight(Right.ADD_EVENT);
		sg.addRight(Right.MODIFY_PICTURE);
		sg.addRight(Right.RECEIVED_MESSAGE);
		sg.addRight(Right.ANSWER_MESSAGE);

		sg.removeRight(Right.ADD_ANNOUNCE);
		sg.removeRight(Right.MODIFY_PICTURE);

		assertFalse("should'nt be authorized", sg.isAuthorized(Right.ADD_ANNOUNCE));
		assertTrue("should be authorized", sg.isAuthorized(Right.ANSWER_MESSAGE));
		assertFalse("should'nt be authorized", sg.isAuthorized(Right.MODIFY_PICTURE));
	}

	@Test
	public void testIsAuthorized() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi", "mailvbjkgc1@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");
		SocialGroup father = new SocialGroup(masterGroup, "G", "descrption");

		sg.addRight(Right.ADD_ANNOUNCE);
		sg.addRight(Right.ADD_EVENT);
		sg.addRight(Right.MODIFY_PICTURE);
		father.addRight(Right.RECEIVED_MESSAGE);
		father.addRight(Right.ANSWER_MESSAGE);

		sg.setGroup(father);

		assertFalse("father is not authorized", father.isAuthorized(Right.ADD_ANNOUNCE));
		assertTrue("should be authorized", sg.isAuthorized(Right.ADD_ANNOUNCE));
		assertTrue("should be authorized, has the right of the father", sg.isAuthorized(Right.ANSWER_MESSAGE));

		assertTrue("should be authorized", father.isAuthorized(Right.RECEIVED_MESSAGE));
		assertTrue("should be authorized, has the right of the father", sg.isAuthorized(Right.RECEIVED_MESSAGE));

		assertFalse("should'nt be authorized", father.isAuthorized(Right.MODIFY_PICTURE));
	}

	@Test
	public void testchangeMasterGroup() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi", "mailvbjkgc3584@gmail.com");
		SocialEntity masterGroup2 = new SocialEntity("toto", "toto", "mailvbjkgc548481258@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "G", "descrption");

		sg.setMasterGroup(masterGroup2);
		assertSame(masterGroup2, sg.getMasterGroup());
	}

	@Test
	public void testEqualsMethod() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi", "mailvbjkgc3584@gmail.com");
		SocialEntity masterGroup2 = new SocialEntity("titi", "titi", "mailvbjkgc358@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "name", "description");
		SocialGroup sg2 = new SocialGroup(masterGroup, "name", "description");
		// Test objs
		assertFalse(sg.equals(""));
		// description
		sg.setDescription(null);
		assertFalse(sg.equals(sg2));
		sg.setDescription("description");
		// description2
		sg.setDescription("dodo");
		assertFalse(sg.equals(sg2));
		sg.setDescription("description");
		// enable
		sg.setIsEnabled(false);
		assertFalse(sg.equals(sg2));
		sg.setIsEnabled(true);
		sg2.setIsEnabled(true);

		// masterGroup
		sg.setMasterGroup(masterGroup2);
		assertFalse(sg.equals(sg2));
		sg.setMasterGroup(masterGroup);
		// name
		sg.setName(null);
		assertFalse(sg.equals(sg2));
		sg.setName("toto");
		assertFalse(sg.equals(sg2));
		sg.setName("name");
		// right
		
		sg.setRights(null);
		assertFalse(sg.equals(sg2));
		sg.setRights(new HashSet<Right>());
		sg.getRights().add(Right.ADD_ANNOUNCE);
		assertFalse(sg.equals(sg2));
		sg.getRights().clear();
		// social Element
		sg.setSocialElements(null);
		assertFalse(sg.equals(sg2));
		sg.setSocialElements(new ArrayList<SocialElement>());
		sg.getSocialElements().add(masterGroup2);
		assertFalse(sg.equals(sg2));
		sg.setSocialElements(sg2.getSocialElements());
		assertTrue(sg.equals(sg2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void nonNullMasterGroup() {
		SocialEntity masterGroup = new SocialEntity("titi", "titi", "mailvbjkgc3584@gmail.com");
		SocialGroup sg = new SocialGroup(masterGroup, "name", "description");
		sg.setMasterGroup(null);
	}

}
