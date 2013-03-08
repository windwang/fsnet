package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.PrivateMessageFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

public class PrivateMessageFacadeTest {

	private EntityManager em;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("TestPU");
		em = entityManagerFactory.createEntityManager();
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test the sendPrivateMessage methode
	 */
	@Test
	public void testSendPrivateMessage() {
		String body = "Test message privé 1";
		em.getTransaction().begin();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity from = sef.createSocialEntity("TestName 1",
				"TestFirstName1", "sendmessagetest1@gmail.com");
		String subject = "The subject test 1";
		SocialEntity to = sef.createSocialEntity("TestName 12",
				"TestFirstName12", "sendmessagetest12@gmail.com");

		PrivateMessageFacade instance = new PrivateMessageFacade(em);
		PrivateMessage result = instance.sendPrivateMessage(body, from,
				subject, to);
		em.getTransaction().commit();
		PrivateMessage pmfound = em.find(PrivateMessage.class, result.getId());

		assertSame(from.getSentPrivateMessages().get(0), to
				.getReceivedPrivateMessages().get(0));
		assertSame(result, pmfound);
	}

	/**
	 * Test the PrivateMessage removing feature
	 */
	@Test
	public void testDeletePrivateMessage() {
		String body = "Test message privé 2";
		em.getTransaction().begin();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity from = sef.createSocialEntity("TestName 1",
				"TestFirstName1", "deletemessagetest1@gmail.com");
		String subject = "The subject test 2";
		SocialEntity to = sef.createSocialEntity("TestName 12",
				"TestFirstName12", "deletemessagetest12@gmail.com");

		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		PrivateMessage message = pmf
				.sendPrivateMessage(body, from, subject, to);

		assertEquals(1, from.getSentPrivateMessages().size());
		assertEquals(message, from.getSentPrivateMessages().get(0));
		assertEquals(1, to.getReceivedPrivateMessages().size());
		assertEquals(message, to.getReceivedPrivateMessages().get(0));

		pmf.deletePrivateMessage(from, message);
		assertEquals(0, from.getSentPrivateMessages().size());

		pmf.deletePrivateMessage(to, message);
		assertEquals(0, to.getReceivedPrivateMessages().size());
		em.getTransaction().commit();

	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeletePrivateMessageWithEntityIsNotNullAndMessageIsNull() {
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity entity = sef.createSocialEntity("lol", "gogo",
				"truc@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.deletePrivateMessage(entity, null);
		fail("null message is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeletePrivateMessageWithEntityIsNullAndMessageIsNotNull() {
		String body = "How old are you ?";
		em.getTransaction().begin();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity from = sef
				.createSocialEntity("name", "firstName", "email");
		String subject = "hey, this is franklin ?";
		SocialEntity to = sef.createSocialEntity("dlb", "teacher",
				"dlbMAn@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		PrivateMessage message = pmf
				.sendPrivateMessage(body, from, subject, to);
		pmf.deletePrivateMessage(null, message);
		fail("null entity is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeletePrivateMessageWithEntityIsNullAndMessageIsNotNullAndFromSourceIsNotNull() {
		String body = "Are you beautiful ?";
		em.getTransaction().begin();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity from = sef.createSocialEntity("morgan", "clara",
				"oui@gmail.com");
		String subject = "hey, this is franklin ?";
		SocialEntity to = sef.createSocialEntity("dlb2", "teacher2",
				"dlbBOSS@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		PrivateMessage message = pmf
				.sendPrivateMessage(body, from, subject, to);
		pmf.deletePrivateMessage(null, message, "fromSource");
		fail("null entity is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeletePrivateMessageWithEntityIsNotNullAndMessageIsNullAndFromSourceIsNotNull() {
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity entity = sef.createSocialEntity("tatatuche", "tototuche",
				"muche@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.deletePrivateMessage(entity, null, "fromSource");
		fail("null message is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testsearchReceivedPrivateMessageWithEntityIsNotNullAndpatternIsNull() {
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity entity = sef.createSocialEntity("lol", "gogo",
				"truc@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.searchReceivedPrivateMessage(entity, null);
		fail("null pattern is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testsearchReceivedPrivateMessageWithEntityIsNullAndpatternIsNotNull() {
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.searchReceivedPrivateMessage(null, "pattern");
		fail("null entity is forbidden");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testsearchReceivedPrivateMessageWithEntityIsNullAndpatternIsNull() {
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.searchReceivedPrivateMessage(null, null);
		fail("null entity and null patern is forbidden");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testsearchSentPrivateMessageWithEntityIsNotNullAndpatternIsNull() {
		SocialEntityFacade sef = new SocialEntityFacade(em);
		SocialEntity entity = sef.createSocialEntity("lol", "gogo",
				"truc@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.searchSentPrivateMessage(entity, null);
		fail("null pattern is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testsearchSentPrivateMessageWithEntityIsNullAndpatternIsNotNull() {
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.searchSentPrivateMessage(null, "pattern");
		fail("null entity is forbidden");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testsearchSentPrivateMessageWithEntityIsNullAndpatternIsNull() {
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.searchSentPrivateMessage(null, null);
		fail("null entity and null patern is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConversationWithSocialEntityFromIsNullAndOthersIsNotNull() {

		SocialEntityFacade sef = new SocialEntityFacade(em);

		SocialEntity to = sef.createSocialEntity("dlb3", "teacher3",
				"toto123@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.getConversation(null, "subject test", to);
		fail("null entity from is forbidden");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConversationWithSocialEntityToIsNullAndOthersIsNotNull() {
		SocialEntityFacade sef = new SocialEntityFacade(em);

		SocialEntity from = sef.createSocialEntity("fromName2",
				"fromFirstName2", "fromfrom@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.getConversation(from, "subject test to null", null);
		fail("null entity to is forbidden");

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetConversationWithSubjectIsNullAndOthersIsNotNull() {
		SocialEntityFacade sef = new SocialEntityFacade(em);

		SocialEntity from = sef.createSocialEntity("fromName", "fromFirstName",
				"fromfrom@gmail.com");
		SocialEntity to = sef.createSocialEntity("dlb3", "teacher3",
				"toto123@gmail.com");
		PrivateMessageFacade pmf = new PrivateMessageFacade(em);
		pmf.getConversation(from, null, to);
		fail("null subject is forbidden");
	}
}
