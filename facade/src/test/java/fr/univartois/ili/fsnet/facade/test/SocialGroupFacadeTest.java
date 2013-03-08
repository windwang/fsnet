package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Right;
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
	
	@Test
	public void testIsAuthorizedSuperAdmin() {

		SocialEntity masterGroup = sef.createSocialEntity("aaargrerjgcfvjaa",
				"bjhvgbhjbbdedbbb", "anjrererreklnmb@email.com");
			
		SocialGroup sg = sgf.createSocialGroup(masterGroup, "kjhrerkj", "hjrerekhkj",
				new ArrayList<SocialElement>());
		sg.addSocialElement(masterGroup);		
		
		assertTrue(sg.getSocialElements().contains(masterGroup));
		assertTrue(sg.getMasterGroup().equals(masterGroup));
		assertTrue(masterGroup.getGroup().equals(sg));
		assertNull(sg.getGroup());
		
		for(Right right:Right.values())
			assertTrue("super admin have all the rights", sgf.isAuthorized(masterGroup, right));
	}

	@Test
	public void testIsAuthorizedNoParent() {

		SocialEntity masterGroup = sef.createSocialEntity("aaajgcfvjaa",
				"bjhvgbhjbbbbb", "anjklnmb@email.com");

		for(Right right:Right.values())
			assertFalse("simple user have no rights", sgf.isAuthorized(masterGroup, right));
	}

	@Test
	public void testIsAuthorized() {

		SocialEntity masterGroup = sef.createSocialEntity("aaajgcfvjaa",
				"bjhvgbhjbbbbb", "anjklnmb@email.com");
		SocialEntity user = sef.createSocialEntity("aaajeegcfvjaa",
				"bjhvgbhejbbbbb", "anjkeelnmb@email.com");
			
		SocialGroup sg = sgf.createSocialGroup(masterGroup, "kjhkj", "hjkhkj",
				new ArrayList<SocialElement>());
		sg.addSocialElement(user);
		
		sg.addRight(Right.ADD_ANNOUNCE);
		sg.addRight(Right.ADD_CONSULTATION);
		
		for(Right right:Right.values())
			if(right!=Right.ADD_ANNOUNCE && right!=Right.ADD_CONSULTATION)
				assertFalse("user don't have this right", sgf.isAuthorized(user, right));
		assertTrue("user have this right", sgf.isAuthorized(user, Right.ADD_ANNOUNCE));
		assertTrue("user have this right", sgf.isAuthorized(user, Right.ADD_CONSULTATION));
	}

	@Test
	public void testIsAuthorizedWithRightFromParents() {

		SocialEntity masterGroup = sef.createSocialEntity("aaajgcfvjaa",
				"bjhvgbhjbbbbb", "anjklnmb@email.com");
		SocialEntity masterGroup2 = sef.createSocialEntity("aaajgcf",
				"bjhvgbhjbbb", "annmb@email.com");
		
		SocialEntity user = sef.createSocialEntity("aaajeegcfvjaa",
				"bjhvgbhejbbbbb", "anjkeelnmb@email.com");
			
		SocialGroup sg = sgf.createSocialGroup(masterGroup, "kjhkj", "hjkhkj",
				new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(masterGroup2, "kjdshkj", "ddhjkhkj",
				new ArrayList<SocialElement>());
		sg2.setGroup(sg);
		sg2.addSocialElement(user);
		
		sg.addRight(Right.ADD_ANNOUNCE);
		sg.addRight(Right.ADD_CONSULTATION);
		sg2.addRight(Right.ADD_CONTACT_GROUP);
		
		
		for(Right right:Right.values())
			if(right!=Right.ADD_ANNOUNCE && right!=Right.ADD_CONSULTATION && right!=Right.ADD_CONTACT_GROUP)
				assertFalse("user don't have this right", sgf.isAuthorized(user, right));
		assertTrue("user have this right", sgf.isAuthorized(user, Right.ADD_ANNOUNCE));
		assertTrue("user have this right", sgf.isAuthorized(user, Right.ADD_CONSULTATION));
		assertTrue("user have this right", sgf.isAuthorized(user, Right.ADD_CONTACT_GROUP));
	}

	
	
	@Test
	public void testIsAuthorizedWithNull(){
		assertFalse(sgf.isAuthorized(null, null));
	}
	
	@Test
	public void testIsAuthorizedWithNull2(){
		assertFalse(sgf.isAuthorized(new SocialEntity(), null));
	}
	
	@Test
	public void testGetAllSocialGroups(){
		String string = "a";
		String string2 = "a2";
		SocialEntity se = createSocialEntity(string);
		SocialEntity se2 = createSocialEntity(string2);
		
		List<SocialGroup> listOld = sgf.getAllSocialGroups();
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, string, string,new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(se2, string2, string2,new ArrayList<SocialElement>());
		em.getTransaction().commit();

		List<SocialGroup> list = sgf.getAllSocialGroups();
		
		assertEquals(listOld.size()+2, list.size());
		assertTrue(list.contains(sg1));
		assertTrue(list.contains(sg2));
	}
	
	@Test
	public void testGetSocialElementByFilter(){
		String s = "b";
		String s2 = "b2" ; 
		String s3 = "b3" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		SocialEntity se3 = createSocialEntity(s3);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(se2, s2, s2,new ArrayList<SocialElement>());
		sg1.addSocialElement(sg2);
		sg1.addSocialElement(se2);
		sg1.addSocialElement(se3);
		em.getTransaction().commit();
		
		List<SocialEntity> list = sgf.getAcceptedSocialElementsByFilter(sg1, SocialEntity.class) ;
		assertEquals(2,list.size());
		assertTrue(list.contains(se2));
		assertTrue(list.contains(se3));		
	}
	
	@Test
	public void testGetAllChildGroups(){
		String s = "c";
		String s2 = "c2" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(se2, s2, s2,new ArrayList<SocialElement>());
		sg1.addSocialElement(sg2);
		em.getTransaction().commit();
		
		List<SocialGroup> list = sgf.getAllChildGroups(sg1);
		assertTrue(list.contains(sg2));
	}
	
	
	@Test
	public void testGetAllChildMembers(){
		String s = "d";
		String s2 = "d2" ;
		String s3 = "d3" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		SocialEntity se3 = createSocialEntity(s3);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		sg1.addSocialElement(se2);
		sg1.addSocialElement(se3);
		em.getTransaction().commit();
		
		List<SocialEntity> list = sgf.getAllChildMembers(sg1);
		assertEquals(2, list.size());
		assertTrue(list.contains(se2));
		assertTrue(list.contains(se3));
	}
	
	@Test
	public void testSwitchState(){
		String s = "e";
		String s2 = "e2" ;
		String s3 = "e3" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		SocialEntity se3 = createSocialEntity(s3);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		sg1.addSocialElement(se2);
		sg1.addSocialElement(se3);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		sg1.setIsEnabled(true);
		sgf.switchState(sg1.getId());
		em.getTransaction().commit();
		
		assertFalse(sg1.getIsEnabled());
		assertFalse(se2.getIsEnabled());
		
		em.getTransaction().begin();
		sg1.setIsEnabled(false);
		sgf.switchState(sg1.getId());
		em.getTransaction().commit();
		
		assertTrue(sg1.getIsEnabled());
		assertTrue(se2.getIsEnabled());		
	}
	
	
	@Test
	public void testGetAllGroupsChildSelfInclude(){
		String s = "f";
		String s2 = "f2" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(se2, s2, s2,new ArrayList<SocialElement>());
		sg1.addSocialElement(sg2);
		em.getTransaction().commit();
		
		List<SocialGroup> list = sgf.getAllGroupsChildSelfInclude(sg1);
		assertTrue(list.contains(sg1));
		assertTrue(list.contains(sg2));
	}
	
	
	@Test
	public void testGetMembersFromGroup(){
		String s = "g";
		String s2 = "g2" ; 
		String s3 = "g3" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		SocialEntity se3 = createSocialEntity(s3);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(se2, s2, s2,new ArrayList<SocialElement>());
		sg1.addSocialElement(sg2);
		sg1.addSocialElement(se3);
		em.getTransaction().commit();
		
		List<SocialEntity> list = sgf.getMembersFromGroup(sg1);
		
		assertEquals(1,list.size());
		assertTrue(list.contains(se3));
		assertFalse(list.contains(sg2));		
	}
	
	
	@Test
	public void testGetAllAntecedentSocialGroups(){
		String s = "h";
		String s2 = "h2" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(se2, s2, s2,new ArrayList<SocialElement>());
		sg1.addSocialElement(sg2);
		em.getTransaction().commit();
		
		List<SocialGroup> list = sgf.getAllAntecedentSocialGroups(sg2);
		
		assertEquals(1,list.size());
		assertTrue(list.contains(sg1));
		
		list = sgf.getAllAnecedentSocialGroupsSelfInclude(sg2);
		assertEquals(2,list.size());
		assertTrue(list.contains(sg1));
		assertTrue(list.contains(sg2));
		
	}
	
	@Test
	public void testGetMastersFromGroupAndChildGroups(){
		String s = "hh";
		String s2 = "hh2" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		SocialGroup sg2 = sgf.createSocialGroup(se2, s2, s2,new ArrayList<SocialElement>());
		sg1.addSocialElement(sg2);
		em.getTransaction().commit();
		
		List<SocialEntity> list = sgf.getMastersFromGroupAndChildGroups(sg1);
		
		assertTrue(list.contains(se));
		assertTrue(list.contains(se2));		
	}
	

	@Test
	public void testGetPersonsWithWhoMemberCanInteract(){
		String s = "i";
		String s2 = "i2" ;
		String s3 = "i3" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		SocialEntity se3 = createSocialEntity(s3);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		sg1.addSocialElement(se2);
		sg1.addSocialElement(se3);
		em.getTransaction().commit();
		
		List<SocialEntity> list = sgf.getPersonsWithWhoMemberCanInteract(se2);	
		assertTrue(list.contains(se3));
	}
	
	@Test
	public void testGetIdOfThePersonsWithWhoMemberCanInteract(){
		String s = "j";
		String s2 = "j2" ;
		String s3 = "j3" ;
		
		SocialEntity se = createSocialEntity(s);
		SocialEntity se2 = createSocialEntity(s2);
		SocialEntity se3 = createSocialEntity(s3);
		
		em.getTransaction().begin();
		SocialGroup sg1 = sgf.createSocialGroup(se, s, s,new ArrayList<SocialElement>());
		sg1.addSocialElement(se2);
		sg1.addSocialElement(se3);
		em.getTransaction().commit();
		
		List<Integer> list = sgf.getIdOfThePersonsWithWhoMemberCanInteract(se2);	
		assertTrue(list.contains(se3.getId()));
	}
	
	@Test
	public void testGetMastersFromGroupAndChildGroupsWithNull(){
		List<SocialEntity> list = sgf.getMastersFromGroupAndChildGroups(null);
		assertEquals(0,list.size());
	}
	
	@Test
	public void testGetMembersFromGroupWithNull(){
		List<SocialEntity> list = sgf.getMembersFromGroup(null);
		assertEquals(0,list.size());
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testAddSocialElementWithNull(){
		sgf.addSocialElement(null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddSocialElementWithNull2(){
		SocialEntity se = sef.createSocialEntity("nameException", "firstNameException","emailException@mail.com");
		sgf.addSocialElement(se, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testrRemoveSocialElementWithNull(){
		sgf.removeSocialElement(null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveSocialElementWithNull2(){
		SocialEntity se = sef.createSocialEntity("nameException2", "firstNameException2","emailException2@mail.com");
		sgf.removeSocialElement(se, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindByNameWithNull(){
		sgf.findByName(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchGroupWithNull(){
		sgf.searchGroup(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAcceptedSocialElementsByFilterWithNull(){
		sgf.getAcceptedSocialElementsByFilter(null,SocialElement.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTreeParentNameWithNull(){
		sgf.treeParentName(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAllAntecedentSocialGroupsWithNull(){
		sgf.getAllAntecedentSocialGroups(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAllChildGroupsWithNull(){
		sgf.getAllChildGroups(null);
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAllChildMemberswithNull(){
		sgf.getAllChildMembers(null);
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAllGroupsChildSelfIncludeWithNull(){
		sgf.getAllGroupsChildSelfInclude(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetAllAnecedentSocialGroupsSelfIncludeWithNull(){
		sgf.getAllAnecedentSocialGroupsSelfInclude(null);
	}
	
	private SocialEntity createSocialEntity(String string) {
		em.getTransaction().begin();
		SocialEntity se = sef.createSocialEntity("name"+string,
				"firstName"+string, "Member"+string+"@mail.fr");
		em.getTransaction().commit();
		return se ;
		
	}

}
