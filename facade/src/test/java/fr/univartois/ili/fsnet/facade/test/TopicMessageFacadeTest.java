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
import fr.univartois.ili.fsnet.entities.TopicMessage;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.HubFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.TopicFacade;
import fr.univartois.ili.fsnet.facade.TopicMessageFacade;

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
		
		em.getTransaction().begin();
		
		SocialEntity from = sef.createSocialEntity("topic", "message",
				"topicmess1@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creator",
				"communnaute", "creatorCommunity1@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity");
		SocialEntity creatorHub = sef.createSocialEntity("creator", "hub",
				"creatorHub1@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub");
		SocialEntity creatorTopic = sef.createSocialEntity("creator", "Topic",
				"creatorTopic2@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic");
		TopicMessage message = tmf.createTopicMessage(body, from, topic);
		
		em.getTransaction().commit();
		
		TopicMessage compare = em.find(TopicMessage.class, message.getId());
		assertEquals(message.getBody(), compare.getBody());
		assertEquals(message.getFrom(), message.getFrom());
		assertEquals(message.getTopic(), message.getTopic());

	}
	
	
	@Test
	public void testCreateAndGet() {
		String body = "body";
		
		em.getTransaction().begin();
		
		SocialEntity from = sef.createSocialEntity("topic2-1", "message2-1",
				"topicmess2-1@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creator2-1",
				"communnaute2-1", "creatorCommunity2-1@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity2-1");
		SocialEntity creatorHub = sef.createSocialEntity("creator2-1", "hub2-1",
				"creatorHub2-1@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub2-1");
		SocialEntity creatorTopic = sef.createSocialEntity("creator2-1", "Topic2-1",
				"creatorTopic2-1@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic2-1");
		TopicMessage message = tmf.createTopicMessage(body, from, topic);
		
		em.getTransaction().commit();
		
		TopicMessage compare = tmf.getTopicMessage(message.getId());
		assertEquals(message.getBody(), compare.getBody());
		assertEquals(message.getFrom(), message.getFrom());
		assertEquals(message.getTopic(), message.getTopic());

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithAllNullParameters() {
		tmf.createTopicMessage(null, null, null);
	}
	

	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithNullBody() {
		SocialEntity from = sef.createSocialEntity("topic1-1", "message1-1",
				"topicmess1-1@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creator1-1",
				"communnaute1-1", "creatorCommunity1-1@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity1-1");
		SocialEntity creatorHub = sef.createSocialEntity("creator1-1", "hub1-1",
				"creatorHub1-1@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub1-1");
		SocialEntity creatorTopic = sef.createSocialEntity("creator1-1", "Topic1-1",
				"creatorTopic1-1@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic1-1");
		tmf.createTopicMessage(null, from, topic);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithNullSocialEntity() {
		String body = "body";
		
		SocialEntity creatorCommunity = sef.createSocialEntity("creator1-2",
				"communnaute1-2", "creatorCommunity1-2@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity1-2");
		SocialEntity creatorHub = sef.createSocialEntity("creator1-2", "hub1-2",
				"creatorHub1-2@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub1-2");
		SocialEntity creatorTopic = sef.createSocialEntity("creator1-2", "Topic1-2",
				"creatorTopic1-2@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic1-2");
		tmf.createTopicMessage(body, null, topic);

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateWithNullTopic() {
		String body = "body";
		
		SocialEntity from = sef.createSocialEntity("topic-1", "message-1",
				"topicmess-1@gmail.com");
		tmf.createTopicMessage(body, from, null);
	}
	
	
	 @Test
     public void testSearchBody() {
             String body = "YYY ABody YYY";
            
             em.getTransaction().begin();
            
             SocialEntity from = sef.createSocialEntity("topic", "message",
                             "topicmess12@gmail.com");
             SocialEntity creatorCommunity = sef.createSocialEntity("creator",
                             "communnaute", "creatorCommunity2@gmail.com");
             Community community = cf.createCommunity(creatorCommunity,
                             "nameCommunity");
             SocialEntity creatorHub = sef.createSocialEntity("creator", "hub",
                             "creatorHub2@gmail.com");
             Hub hub = hf.createHub(community, creatorHub, "nameHub");
             SocialEntity creatorTopic = sef.createSocialEntity("creator", "Topic",
                             "creatorTopic1@gmail.com");
             Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic");
             TopicMessage message = tmf.createTopicMessage(body, from, topic);
             body = "test";
             from = sef.createSocialEntity("topic2", "message2",
             "topicmessbis@gmail.com");
             Topic topicbis = tf.createTopic(hub, creatorTopic, "titleTopicbis");
             tmf.createTopicMessage(body, from, topicbis);
            
             em.getTransaction().commit();
            
             String pattern = "ABody";
            
             List<TopicMessage> results = tmf.searchTopic(pattern);
             TopicMessage tmRes = results.get(0);
            
             assertEquals(1, results.size());
             assertEquals(message.getBody(), tmRes.getBody());
             assertEquals(message.getFrom(), tmRes.getFrom());
             assertEquals(message.getTopic(), tmRes.getTopic());
     }
	 
	 
	 @Test(expected=IllegalArgumentException.class)
     public void testSearchTopicWithNullPattern() {            
             tmf.searchTopic(null);

     }
	 
	 
     @Test
     public void testSearchBodyAndTopic() {
             em.getTransaction().begin();
             String body = "YYY SpecialBody YYY";
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
            
             String pattern = "SpecialBody";
             List<TopicMessage> results = tmf.searchTopic(pattern,topic);
             TopicMessage tmRes = results.get(0);
             assertEquals(message.getBody(), tmRes.getBody());
             assertEquals(message.getFrom(), tmRes.getFrom());
             assertEquals(message.getTopic(), tmRes.getTopic());
     }
     
     @Test(expected=IllegalArgumentException.class)
     public void testSearchWithNullPatternAndNullTopic() {
             tmf.searchTopic(null,null);
     }

     @Test(expected=IllegalArgumentException.class)
     public void testSearchWithNullPatternAndNonNullTopic() {
             em.getTransaction().begin();

             SocialEntity creatorCommunity = sef.createSocialEntity("creator3-1",
                             "communnaute3-1", "creatorCommunitybis3-1@gmail.com");
             Community community = cf.createCommunity(creatorCommunity,
                             "nameCommunity3-1");
             SocialEntity creatorHub = sef.createSocialEntity("creator3-1", "hub3-1",
                             "creatorHubbis3-1@gmail.com");
             Hub hub = hf.createHub(community, creatorHub, "nameHub3-1");
             SocialEntity creatorTopic = sef.createSocialEntity("creator3-1", "Topic3-1",
                             "creatorTopicbis3-1@gmail.com");
             Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic3-1");
            
             em.getTransaction().commit();
            

             tmf.searchTopic(null,topic);
     }
     
     @Test(expected=IllegalArgumentException.class)
     public void testSearchWithNonNullPatternAndNullTopic() {          
             String pattern = "SpecialBody2";
             tmf.searchTopic(pattern,null);
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
	
	@Test
	public void testDeleteWithZeroId() {		
		em.getTransaction().begin();
		tmf.deleteTopicMessage(0);
		em.getTransaction().commit();
	}
	
	@Test
	public void testGetLastPageId() {
		String body = "bodyForTopic";
		
		em.getTransaction().begin();
		
		SocialEntity from = sef.createSocialEntity("topic36", "message36",
				"topicmess36@gmail.com");
		SocialEntity creatorCommunity = sef.createSocialEntity("creator36",
				"communnaute36", "creatorCommunity36@gmail.com");
		Community community = cf.createCommunity(creatorCommunity,
				"nameCommunity36");
		SocialEntity creatorHub = sef.createSocialEntity("creator36", "hub36",
				"creatorHub36@gmail.com");
		Hub hub = hf.createHub(community, creatorHub, "nameHub36");
		SocialEntity creatorTopic = sef.createSocialEntity("creator36", "Topic36",
				"creatorTopic36-2@gmail.com");
		Topic topic = tf.createTopic(hub, creatorTopic, "titleTopic36");
		TopicMessage message = tmf.createTopicMessage(body, from, topic);
		
		em.getTransaction().commit();
		
		int numMsg = tmf.getLastPageId(topic.getId(), 2) ;
		assertEquals(numMsg, 0) ;
	}
	
	@Test
	public void testGetLastPageIdWithNonExistingId() {
		
		em.close() ;
		
		int numMsg = tmf.getLastPageId(1, 2) ;
		assertEquals(0,numMsg) ;
	}
	

}
