package fr.univartois.ili.fsnet.facade.forum.iliforum;

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
		SocialEntity creatorCommunity = sef.createSocialEntity(
				"creatorcreateCommCreate", "communnautecreateCommCreate",
		"creatorCommunitycreateCommCreate@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
		"nameCommunitycreateCommCreate");
		Community compare = em.find(Community.class, community.getId());
		assertEquals(community.getCreator(), compare.getCreator());
		assertEquals(community.getTitle(), compare.getTitle());
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
	public void testDelete() {
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
}
