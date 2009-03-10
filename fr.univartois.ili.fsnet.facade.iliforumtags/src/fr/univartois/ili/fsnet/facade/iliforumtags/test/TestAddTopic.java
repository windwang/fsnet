package fr.univartois.ili.fsnet.facade.iliforumtags.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

public class TestAddTopic {

	Hub hub;
	Topic topic;

	@Before
	public void setUp() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date;
		try {
			date = (Date) formatter.parse("29/01/02");

			hub = new Hub("TestAdd", date);
			topic = new Topic("test add", date, null, hub, null);
			hub.getLesTopics().add(topic);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() {
		IliForumFacade iff = IliForumFacade.getInstance();

		iff.removeHub(hub);
		iff.removeTopic(topic);
	}

	@Test
	public void testPersist() throws ParseException {
		IliForumFacade iff = IliForumFacade.getInstance();
		assertEquals(0, hub.getId());
		assertEquals(0, topic.getId());
		iff.addHub(hub);
		assertFalse(hub.getId() == 0);
		assertFalse(topic.getId() == 0);
		// iff.addTopic(topic);
		int nbT = hub.getLesTopics().size();
		assertTrue(nbT != 0);

	}
}
