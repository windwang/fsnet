package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;

public class TopicTest {

	private static final String TITLE = "Titre";
	private EntityManager em;

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
	public void testCreateEmptyTopic() {
		Topic topic = new Topic();
		assertNull(topic.getHub());
		assertNull(topic.getCreator());
		assertNull(topic.getTitle());

	}
	
	@Test
	public void testCreateTopic() {
		Hub hub = new Hub();
		SocialEntity entity = new SocialEntity();
		String titre = new String("titre");
		Topic topic = new Topic(hub, entity, titre);
		assertEquals(entity, topic.getCreator());
		assertEquals(hub, topic.getHub());
		assertEquals(titre, topic.getTitle());

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullHub() {
		SocialEntity entity = new SocialEntity();
		new Topic(null,entity,"titre");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullEntity() {
		Hub hub = new Hub();
		new Topic(hub,null,"titre");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullTitle() {
		Hub hub = new Hub();
		SocialEntity entity = new SocialEntity();
		new Topic(hub,entity,null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullHubAndEntity() {
		new Topic(null,null,"titre");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullTitleAndHub() {
		SocialEntity entity = new SocialEntity();
		new Topic(null,entity,null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullTitleAndEntity() {
		Hub hub = new Hub();
		new Topic(hub,null,null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithEverythingNull() {
		new Topic(null,null,null);
	}

    @Test
    public void testSetByMethodsAndGetHub() {
    	Topic topic = new Topic();
    	Hub hub= new Hub();
		topic.setHub(hub);
    	assertEquals(hub, topic.getHub());
    	
    }
    
    @Test
    public void testonTopicRemove() {
    	Topic topic = new Topic();
    	Hub hub= new Hub();
		topic.setHub(hub);
		topic.onTopicRemove();
		assertNull(topic.getHub());
    }
    
    @Test
    public void testMessages() {
    		
    	ArrayList<TopicMessage> messages = new ArrayList<TopicMessage>();
    	
    	TopicMessage e1 = new TopicMessage();
    	TopicMessage e2 = new TopicMessage();
		messages.add(e1);
		messages.add(e2);
		Topic topic = new Topic();
		topic.setMessages(messages);
		
    }
	/**
	 * Test to check if it's possible to persist a Topic
	 */
	@Test
	public void testPersist() {
		SocialEntity es = new SocialEntity("Ragoût", "Mouton",
				"RagoûtMouton@toiaussitafaim.com");
		es.setName("Théophile");
		es.setFirstname("Gautier");
		final Community community = new Community(es, "macom");
		Hub hub = new Hub(community, es, "mon hub");
		Topic top = new Topic(hub, es, "mon topic");
		TopicMessage firstmessage = new TopicMessage("kiiiii", es, top);
		top.getMessages().add(firstmessage);
		em.getTransaction().begin();
		em.persist(es);
		em.persist(community);
		em.persist(hub);
		em.persist(top);
		em.getTransaction().commit();
	}
	
	 
	 @Test
	 public void testSetAndGetforHub() {
		 Topic top = new Topic();
		 Hub hub = new Hub();
		 top.setHub(hub);
		 assertEquals(top.getHub(), hub);
	 }
	 
	 @Test
	 public void testSetMessages(){
		 Topic top = new Topic();
		 List<TopicMessage> messages = new ArrayList<>();
		 messages.add(new TopicMessage());
		 top.setMessages(messages);
		 assertEquals(messages, top.getMessages());
		 assertEquals(messages.size(), top.getMessages().size());
	 }
	 
	 @Test
	 public void testTopicRemove(){
		 Topic top = new Topic();
		 Hub hub = new Hub() ;
		 top.setHub(hub);
		 top.onTopicRemove();
		 assertNull(top.getHub());
	 }
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void testHubNull(){
		 Topic top = new Topic(null, new SocialEntity(), TITLE);
	 }
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void testEntityNull(){
		new Topic(new Hub(),null, TITLE);
	 }
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void testHubNotNull(){
		new Topic(new Hub(),null, null);
	 }	 
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void testTitleNull(){
		 new Topic(new Hub(),new SocialEntity(), null);
	 }
	 
	 @Test(expected=IllegalArgumentException.class)
	 public void testAllNull(){
		 new Topic(null,null, null);
	 }
	 
	 @Test
	 public void testNewTopicWithParameters(){
		 Topic top = new Topic(new Hub(),new SocialEntity(),TITLE);
		 assertNotNull(top.getCreator());
		 assertNotNull(top.getTitle());
		 assertNotNull(top.getHub());
		 assertNotNull(top);
	 }

	
}
