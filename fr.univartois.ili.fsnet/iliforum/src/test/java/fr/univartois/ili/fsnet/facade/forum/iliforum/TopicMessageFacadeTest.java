package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;

public class TopicMessageFacadeTest {
	private EntityManager em;
	private TopicMessageFacade tmf;
	private SocialEntityFacade sef;
	private TopicFacade tf;
	private HubFacade hf;
	private CommunityFacade cf;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		tmf = new TopicMessageFacade(em);
		sef = new SocialEntityFacade(em);
		hf = new HubFacade(em);
		cf = new CommunityFacade(em);
		tf = new TopicFacade(em);
	}

	@Test
	public void testCreate() {
		String body = "body";
		SocialEntity from = sef.createSocialEntity("topic", "message",
				"topicmess1@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creator",
				"communnaute", "creatorCommunity@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity");
		SocialEntity creatorHub = sef.createSocialEntity("creator", "hub",
				"creatorHub@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub");
		SocialEntity creatorTopic = sef.createSocialEntity("creator", "Topic",
				"creatorTopic@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic");
		TopicMessage message = tmf.createTopicMessage(body, from, topic);
		TopicMessage compare = em.find(TopicMessage.class, message.getId());
		assertEquals(message.getBody(), compare.getBody());
		assertEquals(message.getFrom(), message.getFrom());
		assertEquals(message.getTopic(), message.getTopic());

	}
	
	@Test
	public void testSearchBody() {
		em.getTransaction().begin();
		String body = "body";
		SocialEntity from = sef.createSocialEntity("topic", "message",
				"topicmess1@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creator",
				"communnaute", "creatorCommunity@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity");
		SocialEntity creatorHub = sef.createSocialEntity("creator", "hub",
				"creatorHub@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub");
		SocialEntity creatorTopic = sef.createSocialEntity("creator", "Topic",
				"creatorTopic@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic");
		TopicMessage message = tmf.createTopicMessage(body, from, topic);
		body = "test";
		from = sef.createSocialEntity("topic2", "message2",
		"topicmessbis@gmail.com");
		Topic topicbis = tf.createTopic(hub, creatorTopic, "titleTopicbis");
		tmf.createTopicMessage(body, from, topicbis);
		em.getTransaction().commit();
		String pattern = "body";
		List<TopicMessage> results = tmf.searchTopic(pattern);
		System.out.println("results = "+results);
		TopicMessage tmRes = results.get(0);
		assertEquals(message.getBody(), tmRes.getBody());
		assertEquals(message.getFrom(), tmRes.getFrom());
		assertEquals(message.getTopic(), tmRes.getTopic());
	}
	
	@Test
	public void testSearchBodyAndTopic() {
		em.getTransaction().begin();
		String body = "body3";
		SocialEntity from = sef.createSocialEntity("topic3", "message3",
				"topicmess2@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creator3",
				"communnaute3", "creatorCommunitybis@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity3");
		SocialEntity creatorHub = sef.createSocialEntity("creator3", "hub3",
				"creatorHubbis@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub3");
		SocialEntity creatorTopic = sef.createSocialEntity("creator3", "Topic3",
				"creatorTopicbis@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic3");
		TopicMessage message = tmf.createTopicMessage(body, from, topic);
		
		body = "test3";
		from = sef.createSocialEntity("topic23", "message23",
		"topicmessbiss3@gmail.com");
		Topic topicbis = tf.createTopic(hub, creatorTopic, "titleTopicbis3");
		tmf.createTopicMessage(body, from, topicbis);
		em.getTransaction().commit();
		
		String pattern = "body3";
		List<TopicMessage> results = tmf.searchTopic(pattern,topic);
		TopicMessage tmRes = results.get(0);
		assertEquals(message.getBody(), tmRes.getBody());
		assertEquals(message.getFrom(), tmRes.getFrom());
		assertEquals(message.getTopic(), tmRes.getTopic());
	}
	
	@Test
	public void testDelete() {
		em.getTransaction().begin();
		String body = "bodydelete";
		SocialEntity from = sef.createSocialEntity("topicdelete", "messagedelete",
				"topicmessdelete@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creatordelete",
				"communnautedelete", "creatorCommunitydelete@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunitydelete");
		SocialEntity creatorHub = sef.createSocialEntity("creatordelete", "hubdelete",
				"creatorHubdelete@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHubdelete");
		SocialEntity creatorTopic = sef.createSocialEntity("creatordelete", "Topicdelete",
				"creatorTopicdelete@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopicdelete");
		TopicMessage message = tmf.createTopicMessage(body, from, topic);
		
		body = "testdelete2";
		from = sef.createSocialEntity("topicdelete2", "messagedelete2",
		"topicmessdelete2@gmail.com");
		Topic topicbis = tf.createTopic(hub, creatorTopic, "titleTopicdelete2");
		tmf.createTopicMessage(body, from, topicbis);
		em.getTransaction().commit();
		
		em.getTransaction().begin();

		tmf.deleteTopicMessage(message.getId());
		em.getTransaction().commit();
		assertNull(em.find(TopicMessage.class, message.getId()));
	}
}
