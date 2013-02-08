package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.GroupLayout.Group;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionGroups;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialGroup;

public class InteractionGroupsTest {

	private EntityManager em;

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateEmptyInteractionGroups() {
		InteractionGroups interactionGroups = new InteractionGroups();
		assertNull(interactionGroups.getInteraction());
		assertNull(interactionGroups.getGroup());
	}

	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullGroup() {
		Announcement announ1 = new Announcement();
		announ1.setTitle("announ1");
		new InteractionGroups(announ1, null);
	}

	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullInteraction() {
		SocialGroup group = new SocialGroup();
		new InteractionGroups(null, group);
	}

	@Test
	public void testSetByMethodsAndGet() {
		Announcement announ1 = new Announcement();
		announ1.setTitle("announ1");
		SocialGroup group = new SocialGroup();
		InteractionGroups interactionGroups = new InteractionGroups();
		interactionGroups.setInteraction(announ1);
		interactionGroups.setGroup(group);
		assertEquals(announ1, interactionGroups.getInteraction());
		assertEquals(group, interactionGroups.getGroup());
	}

	@Test
	public void testSetByConstructorAndGet() {
		Announcement announ1 = new Announcement();
		announ1.setTitle("announ1");
		SocialGroup group = new SocialGroup();
		InteractionGroups interactionGroups = new InteractionGroups(announ1,
				group);
		assertEquals(announ1, interactionGroups.getInteraction());
		assertEquals(group, interactionGroups.getGroup());
	}
	
	@Ignore
	@Test
	public void testEqualsWithSameInteraction() {
		Announcement announ1 = new Announcement();
		announ1.setTitle("announ1");
		SocialGroup group = new SocialGroup();
		InteractionGroups interactionGroups1 = new InteractionGroups(announ1,
				group);
		InteractionGroups interactionGroups2 = new InteractionGroups(announ1,
				group);
		assertTrue(interactionGroups1.equals(interactionGroups2));
		assertTrue(interactionGroups2.equals(interactionGroups1));
	}
	
	
	@Test
	public void testGetAndSetId() {
		InteractionGroups interactionGroups = new InteractionGroups();
		interactionGroups.setId(1);
		assertEquals(1,interactionGroups.getId());
	}
}
