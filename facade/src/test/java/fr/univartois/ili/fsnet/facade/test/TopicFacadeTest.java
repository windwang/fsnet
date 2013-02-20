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
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.HubFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.TopicFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

public class TopicFacadeTest {
	private EntityManager em;
	private SocialEntityFacade sef;
	private TopicFacade tf;
	private HubFacade hf;
	private CommunityFacade cf;
	private InteractionFacade interactionFacade;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		sef = new SocialEntityFacade(em);
		hf = new HubFacade(em);
		cf = new CommunityFacade(em);
		tf = new TopicFacade(em);
		interactionFacade = new InteractionFacade(em);
	}

	@Test
	public void testCreate() {
		em.getTransaction().begin();
		
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorcreate",
				"communnautecreate", "creatorCommunitycreate@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunitycreate");
		SocialEntity creatorHub = sef.createSocialEntity("creatorcreate",
				"hubcreate", "creatorHubcreate@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubcreate");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorcreate",
				"Topiccreate", "creatorTopiccreate@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopiccreate");
		
		em.getTransaction().commit();
		
		
		Topic compare = em.find(Topic.class, topic.getId());
		assertEquals(topic.getCreator(), compare.getCreator());
		assertEquals(topic.getHub(), compare.getHub());
		assertEquals(topic.getTitle(), compare.getTitle());
		
		em.getTransaction().begin();
		em.remove(creatorCommunity);
		em.remove(community);
		em.remove(creatorHub);
		em.remove(hub);
		em.remove(creatorTopic);
		em.remove(topic);
		em.getTransaction().commit();
	}
	
	
	@Test
	public void testCreateAndGetTopic() {
		em.getTransaction().begin();
		
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorcreate2",
				"communnautecreate2", "creatorCommunitycreate2@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunitycreate2");
		SocialEntity creatorHub = sef.createSocialEntity("creatorcreate2",
				"hubcreate2", "creatorHubcreate2@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubcreate2");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorcreate2",
				"Topiccreate2", "creatorTopiccreate2@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopiccreate2");
		
		em.getTransaction().commit();
		
		
		Topic compare = tf.getTopic(topic.getId());
		assertEquals(topic.getCreator(), compare.getCreator());
		assertEquals(topic.getHub(), compare.getHub());
		assertEquals(topic.getTitle(), compare.getTitle());
		
		em.getTransaction().begin();
		em.remove(creatorCommunity);
		em.remove(community);
		em.remove(creatorHub);
		em.remove(hub);
		em.remove(creatorTopic);
		em.remove(topic);
		em.getTransaction().commit();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithAllNullParameters() {
		tf.createTopic(null, null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithNullHub() {	
		SocialEntity creatorTopic = sef.createSocialEntity("creatorcreate",
				"Topiccreate", "creatorTopiccreate@gmail.com");
		tf.createTopic(null, creatorTopic, "titleTopiccreate");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithNullCreator() {
		
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorcreate",
				"communnautecreate", "creatorCommunitycreate@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunitycreate");
		SocialEntity creatorHub = sef.createSocialEntity("creatorcreate",
				"hubcreate", "creatorHubcreate@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubcreate");
		tf.createTopic(hub, null, "titleTopiccreate");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithNullTitle() {
		
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorcreate",
				"communnautecreate", "creatorCommunitycreate@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunitycreate");
		SocialEntity creatorHub = sef.createSocialEntity("creatorcreate",
				"hubcreate", "creatorHubcreate@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubcreate");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorcreate",
				"Topiccreate", "creatorTopiccreate@gmail.com");
		tf.createTopic(hub, creatorTopic, null);
	}

	@Test
	public void testSearchTitle() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorTitle2",
				"communnauteTitle2", "creatorCommunityTitle2@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunityTitle2");
		SocialEntity creatorHub = sef.createSocialEntity("creatorTitle2",
				"hubTitle2", "creatorHubTitle2@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubTitle2");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorTitle2",
				"TopicTitle2", "creatorTopicTitle2@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopicTitle2");
		tf.createTopic(hub, creatorTopic, "titleTopicbisTitle2");
		em.getTransaction().commit();
		String pattern = "titleTopicTitle2";
		List<Topic> results = tf.searchTopic(pattern);
		Topic tmRes = results.get(0);
		assertEquals(topic.getCreator(), tmRes.getCreator());
		assertEquals(topic.getHub(), tmRes.getHub());
		assertEquals(topic.getTitle(), tmRes.getTitle());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchTitleAndNullHub() {

		String pattern = "titleTopicTitleHubTest";
		tf.searchTopic(pattern,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchWithNullTitleAndHub() {

		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorTitleHub2",
				"communnauteTitleHub2", "creatorCommunityTitleHub2@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunityTitleHub2");
		SocialEntity creatorHub = sef.createSocialEntity("creatorTitle2",
				"hubTitleHub2", "creatorHubTitleHub2@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubTitleHub2");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorTitleHub2",
				"TopicTitleHub2", "creatorTopicTitleHub2@gmail.com");
		tf.createTopic(hub, creatorTopic, "titleTopicTitleHub2");
		Hub hubbis = hf.createHub(community, creatorHub, "nameHubTitleHubbis2");
		tf.createTopic(hubbis, creatorTopic, "titleTopicbisTitleHub2");
		em.getTransaction().commit();
		
		tf.searchTopic(null,hub);

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchWithNullPattern() {
		tf.searchTopic(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchWithNullPatternAndNullHub() {
		tf.searchTopic(null,null);
	}
	
	@Test
	public void testSearchTitleAndHub() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorTitleHub",
				"communnauteTitleHub", "creatorCommunityTitleHub@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunityTitleHub");
		SocialEntity creatorHub = sef.createSocialEntity("creatorTitle",
				"hubTitleHub", "creatorHubTitleHub@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubTitleHub");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorTitleHub",
				"TopicTitleHub", "creatorTopicTitleHub@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopicTitleHub");
		Hub hubbis = hf.createHub(community, creatorHub, "nameHubTitleHubbis");
		tf.createTopic(hubbis, creatorTopic, "titleTopicbisTitleHub");
		em.getTransaction().commit();
		String pattern = "titleTopicTitleHub";
		List<Topic> results = tf.searchTopic(pattern,hub);
		Topic tmRes = results.get(0);
		assertEquals(topic.getCreator(), tmRes.getCreator());
		assertEquals(topic.getHub(), tmRes.getHub());
		assertEquals(topic.getTitle(), tmRes.getTitle());
		
		em.getTransaction().begin();
		em.remove(creatorCommunity);
		em.remove(community);
		em.remove(creatorHub);
		em.remove(hub);
		em.remove(creatorTopic);
		em.remove(topic);
		em.remove(hubbis);
		em.getTransaction().commit();
	}
	
	@Test
	public void testDelete1() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorDelete2",
				"communnauteDelete2", "creatorCommunityDelete2@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunityDelete2");
		SocialEntity creatorHub = sef.createSocialEntity("creatorDelete2",
				"hubDelete2", "creatorHubDelete2@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubDelete2");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorDelete2",
				"TopicDelete2", "creatorTopicDelete2@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopicDelete2");
		Hub hubbis = hf.createHub(community, creatorHub, "nameHubTitleHubbis2");
		tf.createTopic(hubbis, creatorTopic, "titleTopicbisTitleHub2");
		em.getTransaction().commit();
		em.getTransaction().begin();

		interactionFacade.deleteInteraction(creatorTopic, topic);
		em.getTransaction().commit();
		assertNull(em.find(Topic.class, topic.getId()));
	}
	
	@Test(expected=UnauthorizedOperationException.class)
	public void testDelete2() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorDelete2",
				"communnauteDelete2", "creatorCommunityDelete20@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunityDelete2");
		SocialEntity creatorHub = sef.createSocialEntity("creatorDelete2",
				"hubDelete2", "creatorHubDelete20@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubDelete2");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorDelete2",
				"TopicDelete2", "creatorTopicDelete20@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopicDelete2");
		Hub hubbis = hf.createHub(community, creatorHub, "nameHubTitleHubbis2");
		tf.createTopic(hubbis, creatorTopic, "titleTopicbisTitleHub2");
		em.getTransaction().commit();
		em.getTransaction().begin();

		interactionFacade.deleteInteraction(creatorCommunity, topic);
		em.getTransaction().commit();
	}
}
