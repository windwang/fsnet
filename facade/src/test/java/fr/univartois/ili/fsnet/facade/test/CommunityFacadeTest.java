package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

public class CommunityFacadeTest {
	private EntityManager em;
	private SocialEntityFacade sef;
	private CommunityFacade cf;
	private InteractionFacade interactionFacade;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
		.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		sef = new SocialEntityFacade(em);
		cf = new CommunityFacade(em);
		interactionFacade = new InteractionFacade(em);
	}

	@Test
	public void testCreate() {
		em.getTransaction().begin();
		
		SocialEntity creatorCommunity = sef.createSocialEntity(
				"creatorcreateCommCreate", "communnautecreateCommCreate",
		"creatorCommunitycreateCommCreate@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameCommunitycreateCommCreate");
		
		em.getTransaction().commit();
		
		Community compare = em.find(Community.class, community.getId());
		assertEquals(community.getCreator(), compare.getCreator());
		assertEquals(community.getTitle(), compare.getTitle());
	}
	
	
	@Test
	public void testGetCommunity() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity(
				"creatorGetCommunity", "creatorGetCommunity",
		"creatorGetCommunity@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameGetCommunity");		
		em.getTransaction().commit();
		
		Community compare = cf.getCommunity(community.getId());
		assertEquals(community,compare);
	}
	
	@Test
	public void testGetCommunityByName(){
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity(
				"creatorGetCommunityName", "creatorGetCommunityName",
		"creatorGetCommunityName@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameGetCommunityName");		
		em.getTransaction().commit();
		
		Community compare = cf.getCommunityByName(community.getTitle());
		assertEquals(community,compare);
	}

	@Test
	public void testSearch() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity(
				"creatorCommSearch", "communnauteCommSearch",
		"creatorCommunityCommSearch@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameCommunitySearch");
		cf.createCommunity(creatorCommunity, "nameCommunity2");
		em.getTransaction().commit();
		String pattern = "nameCommunitySearch";
		List<Community> results = cf.searchCommunity(pattern);
		Community cRes = results.get(0);
		assertEquals(community.getCreator(), cRes.getCreator());
		assertEquals(community.getTitle(), cRes.getTitle());
	}
	

	@Test
	public void testModifyNameOfCommunity(){
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorModify",
				"communnauteModify", "creatorCommunityModify@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameCommunityModify");
		em.getTransaction().commit();
		em.getTransaction().begin();
		cf.modifyCommunity("newNameCommunityModify", community);
		em.getTransaction().commit();
		assertEquals("newNameCommunityModify",community.getTitle());
	}
	
	@Test
	public void testDelete1() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorDelete3",
				"communnauteDelete3", "creatorCommunityDelete3@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameCommunityDelete3");
		em.getTransaction().commit();
		em.getTransaction().begin();
		interactionFacade.deleteInteraction(creatorCommunity, community);
		em.getTransaction().commit();
		assertNull(em.find(Community.class, community.getId()));
	}
	
	@Test(expected=UnauthorizedOperationException.class)
	public void testDelete2() {
		em.getTransaction().begin();
		SocialEntity notcreator = sef.createSocialEntity("creator",
				"not", "notcreator@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorDelete3",
				"communnauteDelete3", "creatorCommunityDelete30@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameCommunityDelete3");
		em.getTransaction().commit();
		em.getTransaction().begin();
		interactionFacade.deleteInteraction(notcreator, community);
		em.getTransaction().commit();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchCommunityWithNullPattern(){
		cf.searchCommunity(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testModifyCommunityWithNullName(){
		cf.modifyCommunity(null, new Community());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testModifyCommunityWithNullCommunity(){
		cf.modifyCommunity("communaute", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetCommunityByNameWithNullName(){
		cf.getCommunityByName(null);
	}
	
	

}
