package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Meeting;

public class InteractionTest {

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
	public void testFilter() {
		List<Interaction> list = new ArrayList<Interaction>();

		Announcement announ1 = new Announcement();
		announ1.setTitle("announ1");
		list.add(announ1);
		Announcement announ2 = new Announcement();
		announ2.setTitle("announ2");
		list.add(announ2);
		Announcement announ3 = new Announcement();
		announ3.setTitle("announ3");
		list.add(announ3);

		Meeting meet1 = new Meeting();
		meet1.setTitle("meet1");
		list.add(meet1);
		Meeting meet2 = new Meeting();
		meet2.setTitle("meet2");
		list.add(meet2);

		List<Interaction> onlyAnnouncement = (List<Interaction>) Interaction
				.filter(list, Announcement.class);
		assertEquals(3, onlyAnnouncement.size());

		List<Interaction> onlyMeeting = (List<Interaction>) Interaction.filter(
				list, Meeting.class);
		assertEquals(2, onlyMeeting.size());

	}

}
