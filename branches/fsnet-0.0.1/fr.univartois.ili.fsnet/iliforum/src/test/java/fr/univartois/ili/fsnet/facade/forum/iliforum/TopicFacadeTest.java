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
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;

public class TopicFacadeTest {
	private EntityManager em;
	private SocialEntityFacade sef;
	private TopicFacade tf;
	private HubFacade hf;
	private CommunityFacade cf;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		sef = new SocialEntityFacade(em);
		hf = new HubFacade(em);
		cf = new CommunityFacade(em);
		tf = new TopicFacade(em);
	}

	@Test
	public void testCreate() {
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
		Topic compare = em.find(Topic.class, topic.getId());
		assertEquals(topic.getCreator(), compare.getCreator());
		assertEquals(topic.getHub(), compare.getHub());
		assertEquals(topic.getTitle(), compare.getTitle());
	}

	@Test
	public void testSearchTitle() {
		em.getTransaction().begin();
		SocialEntity creatorCommunity = sef.createSocialEntity("creatorTitle",
				"communnauteTitle", "creatorCommunityTitle@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunityTitle");
		SocialEntity creatorHub = sef.createSocialEntity("creatorTitle",
				"hubTitle", "creatorHubTitle@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubTitle");
		SocialEntity creatorTopic = sef.createSocialEntity("creatorTitle",
				"TopicTitle", "creatorTopicTitle@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopicTitle");
		tf.createTopic(hub, creatorTopic, "titleTopicbisTitle");
		em.getTransaction().commit();
		String pattern = "titleTopicTitle";
		List<Topic> results = tf.searchTopic(pattern);
		Topic tmRes = results.get(0);
		assertEquals(topic.getCreator(), tmRes.getCreator());
		assertEquals(topic.getHub(), tmRes.getHub());
		assertEquals(topic.getTitle(), tmRes.getTitle());
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
	}
	
	@Test
	public void testDelete() {
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

		tf.deleteTopic(topic.getId());
		em.getTransaction().commit();
		assertNull(em.find(Topic.class, topic.getId()));
	}
}
