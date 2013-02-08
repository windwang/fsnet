package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

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
	public void testSetByMethodsAndGet() {

		SocialEntity es = new SocialEntity("Ragoût", "Mouton",
				"RagoûtMouton@toiaussitafaim.com");
		es.setName("Théophile");
		es.setFirstname("Gautier");
		final Community community = new Community(es, "macom");
		Hub hub = new Hub(community, es, "mon hub");
		Topic top = new Topic(hub, es, "mon topic");
		TopicMessage firstmessage = new TopicMessage();
		firstmessage.setBody("kiiiii");
		firstmessage.setTopic(top);

		assertEquals(top, firstmessage.getTopic());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullTopic() {
		SocialEntity es = new SocialEntity("Ragoût", "Mouton",
				"RagoûtMouton@toiaussitafaim.com");
		es.setName("Théophile");
		es.setFirstname("Gautier");
		new TopicMessage("kiiiii", es, null);
	}

}
