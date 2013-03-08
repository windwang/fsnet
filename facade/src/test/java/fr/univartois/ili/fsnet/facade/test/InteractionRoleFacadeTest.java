package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionRole;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionRoleFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

public class InteractionRoleFacadeTest {
	private EntityManager em;
	private InteractionRoleFacade interactionrolefacade;
	private SocialEntityFacade sef;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
		interactionrolefacade=new InteractionRoleFacade(em);
		sef=new SocialEntityFacade(em);
	}

	@After
	public void tearDown() {
		em.close();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSubscribeWithInteractionNull() {
		SocialEntity se=sef.createSocialEntity("nomentity", "prenomentity", "titi@test.com");
		Community com=null;
		interactionrolefacade.subscribe(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSubscribeWithSocialEntityNull() {
		SocialEntity se=null;
		Community com=new Community();
		interactionrolefacade.subscribe(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSubscribeWithInteractionAndSocialEntityNull() {
		SocialEntity se=null;
		Community com=null;
		interactionrolefacade.subscribe(se, com);
	}
	
	@Ignore
	@Test
	public void testSubscribe() {
		SocialEntity user=sef.createSocialEntity("user1", "user1", "toto@test.com");
		Interaction i=new Announcement(user, "announce1","content1",new Date(),false);
		interactionrolefacade.subscribe(user, i);
		InteractionRole ir=em.createQuery("SELECT iro FROM InteractionRole iro",InteractionRole.class).getSingleResult();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnsubscribeWithInteractionNull() {
		SocialEntity se=sef.createSocialEntity("nomentity", "prenomentity", "titi@test.com");
		Community com=null;
		interactionrolefacade.unsubscribe(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnsubscribeWithSocialEntityNull() {
		SocialEntity se=null;
		Community com=new Community();
		interactionrolefacade.unsubscribe(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnsubscribeWithInteractionAndSocialEntityNull() {
		SocialEntity se=null;
		Community com=null;
		interactionrolefacade.unsubscribe(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testUnsubscribeAllInteractionNull() {
		Community com=null;
		interactionrolefacade.unsubscribeAll(com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsSubscriberWithInteractionNull() {
		SocialEntity se=sef.createSocialEntity("nomentity", "prenomentity", "titi@test.com");
		Community com=null;
		interactionrolefacade.isSubsriber(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsSubscriberWithSocialEntityNull() {
		SocialEntity se=null;
		Community com=new Community();
		interactionrolefacade.isSubsriber(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsSubscriberWithInteractionAndSocialEntityNull() {
		SocialEntity se=null;
		Community com=null;
		interactionrolefacade.isSubsriber(se, com);
	}
	
	@Test
	public void testIsSubscriber() {
		SocialEntity se=sef.createSocialEntity("nomentity", "prenomentity", "titi@test.com");
		List<InteractionRole> lir1=new ArrayList<InteractionRole>();
		Interaction i1=new Announcement(se, "announce1","content1",new Date(),false);
		Interaction i2=new Announcement(se, "announce2","content2",new Date(),false);
		InteractionRole ir1=new InteractionRole(se, i1);
		ir1.setRole(InteractionRole.RoleName.SUBSCRIBER);
		lir1.add(ir1);
		se.setRolesInInteractions(lir1);
		assertTrue(interactionrolefacade.isSubsriber(se, i1));
		lir1.remove(ir1);
		ir1.setRole(InteractionRole.RoleName.DECISION_MAKER);
		lir1.add(ir1);
		se.setRolesInInteractions(lir1);
		assertFalse(interactionrolefacade.isSubsriber(se, i1));
		assertFalse(interactionrolefacade.isSubsriber(se, i2));
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetSubscribersWithInteractionNull() {
		Community com=null;
		interactionrolefacade.getSubscribers(com);
	}
	
	@Test
	public void testGetSubscribers() {
		Set<SocialEntity> subscribers = new HashSet<SocialEntity>();
		
		SocialEntity user1=sef.createSocialEntity("user1", "user1", "toto@test.com");
		SocialEntity user2=sef.createSocialEntity("user2", "user2", "titi@test.com");
		Interaction i=new Announcement(user1, "announce1","content1",new Date(),false);
		List<Interaction> lir1=new ArrayList<Interaction>();
		lir1.add(i);
		Set<InteractionRole> roles=new HashSet<InteractionRole>();
		InteractionRole ir1=new InteractionRole(user1, i);
		InteractionRole ir2=new InteractionRole(user2, i);
		ir1.setRole(InteractionRole.RoleName.DECISION_MAKER);
		ir2.setRole(InteractionRole.RoleName.SUBSCRIBER);
		
		roles.add(ir1);
		roles.add(ir2);
		i.setRoles(roles);
		user1.setInteractions(lir1);
		subscribers.add(user2);

		assertEquals(subscribers,interactionrolefacade.getSubscribers(i));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsDecisionMakerWithInteractionNull() {
		SocialEntity se=sef.createSocialEntity("nomentity", "prenomentity", "titi@test.com");
		Community com=null;
		interactionrolefacade.isDecisionMaker(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsDecisionMakerWithSocialEntityNull() {
		SocialEntity se=null;
		Community com=new Community();
		interactionrolefacade.isDecisionMaker(se, com);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIsDecisionMakerWithInteractionAndSocialEntityNull() {
		SocialEntity se=null;
		Community com=null;
		interactionrolefacade.isDecisionMaker(se, com);
	}
	
	@Test
	public void testIsDecisionMaker() {
		SocialEntity se=sef.createSocialEntity("nomentity", "prenomentity", "titi@test.com");
		List<InteractionRole> lir1=new ArrayList<InteractionRole>();
		Interaction i1=new Announcement(se, "announce1","content1",new Date(),false);
		Interaction i2=new Announcement(se, "announce2","content2",new Date(),false);
		InteractionRole ir1=new InteractionRole(se, i1);
		ir1.setRole(InteractionRole.RoleName.DECISION_MAKER);
		lir1.add(ir1);
		se.setRolesInInteractions(lir1);
		assertTrue(interactionrolefacade.isDecisionMaker(se, i1));
		lir1.remove(ir1);
		ir1.setRole(InteractionRole.RoleName.SUBSCRIBER);
		lir1.add(ir1);
		se.setRolesInInteractions(lir1);
		assertFalse(interactionrolefacade.isDecisionMaker(se, i1));
		assertFalse(interactionrolefacade.isDecisionMaker(se, i2));
		
	}
}
