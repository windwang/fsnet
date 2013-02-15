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
import fr.univartois.ili.fsnet.entities.DegreeCV;
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
