package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;

public class TopicMessageTest {

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		fact.createEntityManager();
	}

	@Test
	public void testSetByMethodsAndGet() {
		TopicMessage tpm = new TopicMessage();
		Topic t = new Topic();
		tpm.setTopic(t);
		assertEquals(t, tpm.getTopic());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullTopic() {
		String body = "body";
		SocialEntity from = new SocialEntity();
		new TopicMessage(body, from, null);
	}
}
