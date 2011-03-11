package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * @author SAID Mohamed <simo.said09 at gmail.com>
 */
public class SocialGroupFacadeTest {

	private EntityManager em;
	private SocialGroupFacade sgf;
	private SocialEntityFacade sef;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		sef = new SocialEntityFacade(em);
		sgf = new SocialGroupFacade(em);
	}

	@Test
	public void testCreateGroupWithListMember() {

		String name = "name";
		String description = "description";

		List<SocialElement> socialElements = new ArrayList<SocialElement>();

		em.getTransaction().begin();
		SocialEntity masterGroup = sef.createSocialEntity("nameMember",
				"firstNameMember", "Member@mail.fr");
		for (int i = 0; i < 5; i++) {
			socialElements.add(sef.createSocialEntity("name" + i, "firstName"
					+ i, "email" + i + "@mail.fr"));
		}

		em.getTransaction().commit();
		assertEquals(socialElements.size(), 5);
		assertNotNull(socialElements.get(4));
		assertEquals(((SocialEntity) socialElements.get(3)).getEmail(),
				"email3@mail.fr");
		em.getTransaction().begin();
		SocialGroup sg = sgf.createSocialGroup(masterGroup, name, description,
				socialElements);
		em.getTransaction().commit();

		SocialGroup sgp = em.find(SocialGroup.class, sg.getId());
		assertEquals(sg, sgp);
	}

	@Test
	public void testCreateGroupWithListOfGroups() {

		String name = "name2";
		String description = "description2";

		List<SocialElement> socialElements = new ArrayList<SocialElement>();

		em.getTransaction().begin();
		SocialEntity masterGroup = sef.createSocialEntity("nameMember2",
				"firstNameMember2", "Member2@mail.fr");
		for (int i = 0; i < 3; i++) {
			SocialEntity se = sef.createSocialEntity("name2" + i, "firstName2"
					+ i, "email2" + i + "@mail.fr");
			socialElements.add(sgf.createSocialGroup(se, "name2" + i,
					"description2" + i, new ArrayList<SocialElement>()));
		}
		em.getTransaction().commit();

		em.getTransaction().begin();
		SocialGroup sg = sgf.createSocialGroup(masterGroup, name, description,
				socialElements);
		em.getTransaction().commit();

		SocialGroup sgp = em.find(SocialGroup.class, sg.getId());
		assertEquals(sg, sgp);
	}

	@Test
	public void testCreateGroupWithListOfGroupsAndEntity() {

		String name = "name3";
		String description = "description3";

		List<SocialElement> socialElements = new ArrayList<SocialElement>();

		em.getTransaction().begin();
		SocialEntity masterGroup = sef.createSocialEntity("nameMember3",
				"firstNameMember3", "Member3@mail.fr");
		for (int i = 0; i < 2; i++) {
			SocialEntity se = sef.createSocialEntity("name3" + i, "firstName3"
					+ i, "email3" + i + "@mail.fr");
			socialElements.add(sgf.createSocialGroup(se, "name3" + i,
					"description3" + i, new ArrayList<SocialElement>()));
			socialElements.add(sef.createSocialEntity("name4" + i, "firstName4"
					+ i, "email4" + i + "@mail.fr"));
		}

		em.getTransaction().commit();

		em.getTransaction().begin();
		SocialGroup sg = sgf.createSocialGroup(masterGroup, name, description,
				socialElements);
		em.getTransaction().commit();

		SocialGroup sgp = em.find(SocialGroup.class, sg.getId());
		assertEquals(sg, sgp);
	}

	@Test
	public void testGetSocialGroup() {

		String name = "nnname4";
		String description = "description";
		em.getTransaction().begin();

		SocialEntity masterGroup = sef.createSocialEntity("aaajgcfvjaa",
				"bjhvgbhjbbbbb", "anjklnmb@email.com");

		em.getTransaction().commit();

		em.getTransaction().begin();

		SocialGroup sg = sgf.createSocialGroup(masterGroup, name, description,
				new ArrayList<SocialElement>());

		em.getTransaction().commit();

		assertNotNull(sg);

		SocialGroup sgp = sgf.getSocialGroup(sg.getId());

		assertNotNull(sgp);
		assertEquals(sg, sgp);
	}

	@Test
	public void testGetSocialGroupByName() {

		String name = "ngdfgdnname4";
		String description = "defgdgscription";
		em.getTransaction().begin();

		SocialEntity masterGroup = sef.createSocialEntity("aaajgcfgsdgdffgvjaa",
				"bjhvgbhjbbgsdgdbgdfbb", "anjfgdfghsdklnmb@email.com");

		em.getTransaction().commit();

		em.getTransaction().begin();

		SocialGroup sg = sgf.createSocialGroup(masterGroup, name, description,
				new ArrayList<SocialElement>());

		em.getTransaction().commit();

		assertNotNull(sg);

		SocialGroup sgp = sgf.findByName(name);

		assertNotNull(sgp);
		assertEquals(sg, sgp);
	}

	@Test
	public void testAddAndRemoveSocialElement() {

		String name = "name5";
		String description = "description5";
		List<SocialElement> socialElements = new ArrayList<SocialElement>();

		em.getTransaction().begin();
		SocialEntity masterGroup = sef.createSocialEntity("nameMember5",
				"firstNameMember5", "Member5@mail.fr");
		SocialGroup sg = sgf.createSocialGroup(masterGroup, name, description,
				new ArrayList<SocialElement>());
		em.getTransaction().commit();

		assertNotNull(sg);

		assertEquals(sg.getSocialElements().size(), 0);

		em.getTransaction().begin();
		for (int i = 0; i < 2; i++) {
			SocialEntity se = sef.createSocialEntity("nameC" + i, "firstNameC"
					+ i, "emailC" + i + "@mail.fr");
			sgf.addSocialElement(sgf.createSocialGroup(se, "name5" + i,
					"description5" + i, new ArrayList<SocialElement>()), sg);
			SocialEntity se2 = sef.createSocialEntity("name5" + i, "firstName5"
					+ i, "email5" + i + "@mail.fr");
			sgf.addSocialElement(se2, sg);
			socialElements.add(se2);

		}
		em.getTransaction().commit();

		assertEquals(socialElements.size(), 2);
		assertEquals(sg.getSocialElements().size(), 4);

		em.getTransaction().begin();
		for (SocialElement socialElement : socialElements) {
			sgf.addSocialElement(socialElement, sg);
		}
		SocialGroup sgp = em.find(SocialGroup.class, sg.getId());
		em.getTransaction().commit();

		assertEquals(sg.getSocialElements().size(), 4);
		assertEquals(sg, sgp);

		// suppression

		em.getTransaction().begin();
		for (SocialElement socialElement : socialElements) {
			sgf.removeSocialElement(socialElement, sg);
		}
		sgp = em.find(SocialGroup.class, sg.getId());
		em.getTransaction().commit();

		assertEquals(sg.getSocialElements().size(), 2);
		assertEquals(sg, sgp);
	}

	@Test
	public void testSearchSocialGroup() {
		String name = "name6";
		String description = "description6";
		String name2 = "name7";
		String description2 = "description7";
		Set<SocialGroup> result1;
		Set<SocialGroup> result2;
		Set<SocialGroup> result3;
		em.getTransaction().begin();

		SocialEntity masterGroup = sef.createSocialEntity("ABC", "BCA",
				"abc@email.com");
		SocialGroup sg = sgf.createSocialGroup(masterGroup, name, description,
				new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(masterGroup, name2,
				description2, new ArrayList<SocialElement>());
		em.getTransaction().commit();

		em.getTransaction().begin();
		result1 = sgf.searchGroup(name);
		result2 = sgf.searchGroup(name2);
		result3 = sgf.searchGroup("name");
		em.getTransaction().commit();
		assertNotNull(result1);
		assertEquals(result1.size(), 1);
		assertTrue(result1.contains(sg));
		assertNotNull(result2);
		assertEquals(result2.size(), 1);
		assertTrue(result2.contains(sg2));
		assertNotNull(result3);
		assert (result3.size() > 1);
		assertTrue(result3.contains(sg));
		assertTrue(result3.contains(sg2));

	}

}
