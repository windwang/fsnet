package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionGroups;
import fr.univartois.ili.fsnet.entities.InteractionRole;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class InteractionTest {

	private EntityManager em;
	private InteractionGroups ig = new InteractionGroups();
	private List<InteractionGroups> igroups = new ArrayList<>();
	private SocialEntity creator = new SocialEntity("toto", "toto", "toto@mail.com");
	private Interest interest = new Interest();
	private List<Interest> interests = new ArrayList<>();	
	private InteractionRole role = new InteractionRole();
	private Set<InteractionRole> roles = new HashSet<>();
	private SocialEntity member = new SocialEntity();
	private Set<SocialEntity> members = new HashSet<>();
	private List<Interaction> interactions = new ArrayList<>();
	
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
	public void testFilter() {
		List<Interaction> list = new ArrayList<Interaction>();

		Announcement announ1 = new Announcement();
		announ1.setTitle("announ1");
		list.add(announ1);
		Announcement announ2 = new Announcement();
		announ2.setTitle("announ2");
		list.add(announ2);
		Announcement announ3 = new Announcement();
		announ3.setTitle("announ3");
		list.add(announ3);

		Meeting meet1 = new Meeting();
		meet1.setTitle("meet1");
		list.add(meet1);
		Meeting meet2 = new Meeting();
		meet2.setTitle("meet2");
		list.add(meet2);

		List<Interaction> onlyAnnouncement = (List<Interaction>) Interaction
				.filter(list, Announcement.class);
		assertEquals(3, onlyAnnouncement.size());

		List<Interaction> onlyMeeting = (List<Interaction>) Interaction.filter(
				list, Meeting.class);
		assertEquals(2, onlyMeeting.size());

	}
	
	@Test
	public void testGetSetId(){
		Announcement announ = new Announcement();
		announ.setId(1212);
		assertEquals(announ.getId(), 1212);
		Meeting meet = new Meeting();
		meet.setId(1214);
		assertEquals(meet.getId(), 1214);
	}
	
	@Test
	public void testGetSetInteractionGroups(){
		igroups.add(ig);
		Announcement announ = new Announcement();
		announ.setInteractionGroups(igroups);
		assertEquals(1, announ.getInteractionGroups().size());
		assertTrue(announ.getInteractionGroups().contains(ig));
		igroups.clear();
	}
	
	@Test
	public void testGetSetCreator(){
		Announcement announ = new Announcement();
		announ.setCreator(creator);
		assertEquals(creator, announ.getCreator());
	}
	
	@Test
	public void testGetSetInterest(){
		interests.add(interest);
		Announcement announ = new Announcement();
		announ.setInterests(interests);
		assertEquals(1, announ.getInterests().size());
		assertTrue(announ.getInterests().contains(interest));
		interests.clear();
	}

	@Test
	public void testSetCreationDate() throws ParseException{
		Announcement announ = new Announcement();
		announ.setCreationDate(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/2009"));
		assertEquals(announ.getCreationDate(), DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/2009"));
	}
	
	@Test
	public void testGetSetRoles(){
		roles.add(role);
		Announcement announ = new Announcement();
		announ.setRoles(roles);
		assertEquals(1, announ.getRoles().size());
		assertTrue(announ.getRoles().contains(role));
		roles.clear();
	}
	
	@Test
	public void testGetSetFollowingEntitys(){
		members.add(member);
		Announcement announ = new Announcement();
		announ.setFollowingEntitys(members);
		assertEquals(1, announ.getFollowingEntitys().size());
		assertTrue(announ.getFollowingEntitys().contains(member));
		members.clear();
	}

	@Test
	public void testGetSetLastModified() throws ParseException{
		Announcement announ = new Announcement();
		announ.setLastModified(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/2009"));
		assertEquals(announ.getLastModified(), DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/2009"));
	}
	
	@Test
	public void testOnInteractionRemoveWithEmptyFollowingEntitiesAndNullCreator(){
		Announcement announ = new Announcement();
		announ.setFollowingEntitys(members);
		announ.setInterests(interests);
		announ.onInteractionRemove();
		assertTrue(announ.getInterests().isEmpty());
	}
	
	@Test
	public void testOnInteractionRemoveWithEmptyFollowingEntitiesAndCreatorNotNull(){
		Announcement announ = new Announcement();
		announ.setFollowingEntitys(members);
		announ.setInterests(interests);
		announ.setCreator(creator);
		announ.onInteractionRemove();
		assertTrue(announ.getInterests().isEmpty());
	}
	
	@Test
	public void testOnInteractionRemoveWithFollowingEntitiesNotNullAndNullCreator(){
		Announcement announ = new Announcement();
		members.add(member);
		interactions.add(announ);
		member.setFavoriteInteractions(interactions);
		announ.setFollowingEntitys(members);
		announ.setInterests(interests);
		announ.onInteractionRemove();
		assertTrue(announ.getInterests().isEmpty());
		assertTrue(member.getFavoriteInteractions().isEmpty());
		interactions.clear();
		members.clear();
	}
	
	@Test
	public void testOnInteractionRemoveWithFollowingEntitiesNotNullAndCreatorNotNull(){
		Announcement announ = new Announcement();
		members.add(member);
		interactions.add(announ);
		member.setFavoriteInteractions(interactions);
		announ.setFollowingEntitys(members);
		announ.setInterests(interests);
		announ.setCreator(creator);
		announ.onInteractionRemove();
		assertTrue(announ.getInterests().isEmpty());
		assertTrue(member.getFavoriteInteractions().isEmpty());
		members.clear();
		interactions.clear();
	}
	
	@Test
	public void testOnLoadWithEmptyInteractionRole(){
		Announcement announ = new Announcement();
		announ.setRoles(roles);
		announ.setFollowingEntitys(members);
		announ.onLoad();
		assertEquals(announ.getNumSubscriber(), 0);
	}
	
	@Test
	public void testOnLoadWithInteractionRoleNotEmptyAndRoleIsNotSubScriber(){
		InteractionRole roleDM = new InteractionRole();
		roleDM.setRole(InteractionRole.RoleName.DECISION_MAKER);
		roles.add(roleDM);
		Announcement announ = new Announcement();
		announ.setRoles(roles);
		announ.setFollowingEntitys(members);
		announ.onLoad();
		assertEquals(announ.getNumSubscriber(), 0);
		roles.clear();
	}
	
	@Test
	public void testOnLoadWithInteractionRoleNotEmptyAndRoleIsSubScriber(){
		InteractionRole roleS = new InteractionRole();
		roleS.setRole(InteractionRole.RoleName.SUBSCRIBER);
		roles.add(roleS);
		Announcement announ = new Announcement();
		announ.setRoles(roles);
		announ.setFollowingEntitys(members);
		announ.onLoad();
		assertEquals(announ.getNumSubscriber(), 1);
		roles.clear();
	}
	
	@Test
	public void testGetSimpleClassName(){
		Announcement announ = new Announcement();
		assertEquals("Announcement",announ.getSimpleClassName());
		
	}
	
	@Test
	public void testGetNumFollowers(){
		Announcement announ = new Announcement();
		assertEquals(0,announ.getNumFollowers());
		members.add(member);
		announ.setFollowingEntitys(members);
		announ.setRoles(roles);
		announ.onLoad();
		assertEquals(1,announ.getNumFollowers());
		members.clear();
	}
	
	@Test
	public void testSetReaders(){
		Announcement announ = new Announcement();
		List<SocialEntity> members = new ArrayList<>();
		members.add(creator);
		announ.setReaders(members);
		assertEquals(1,announ.getReaders().size());
		assertTrue(announ.getReaders().contains(creator));
	}
	
	@Test
	public void testAddReader(){
		Announcement announ = new Announcement();
		List<SocialEntity> members = new ArrayList<>();
		members.add(creator);
		announ.setReaders(members);
		announ.addReader(creator);
		assertEquals(1,announ.getReaders().size());
		assertTrue(announ.getReaders().contains(creator));
	}
}
