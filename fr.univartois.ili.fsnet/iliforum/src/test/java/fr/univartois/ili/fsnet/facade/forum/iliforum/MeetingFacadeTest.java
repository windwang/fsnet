package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.MeetingFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade.SearchResult;

public class MeetingFacadeTest {
	private EntityManager em;
	private MeetingFacade mf;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		mf = new MeetingFacade(em);
	}

	@Test
	public void testCreate() {
		SocialEntity member = new SocialEntity("zaza", "zaza", "zaza@gmail.com");
		String eventName = "eventName";
		String eventDescription = "eventDescription";
		Date endDate = new Date();
		Boolean isPrivate = false;
		Date startDate = new Date();
		String address = "address";
		String city = "city";

		Meeting es = mf.createMeeting(member, eventName, eventDescription,
				endDate, isPrivate, startDate, address, city);
		Meeting esp = em.find(Meeting.class, es.getId());
		assertEquals(esp.getCreator(), es.getCreator());
		assertEquals(esp.getTitle(), es.getTitle());
		assertEquals(esp.getContent(), es.getContent());
		assertEquals(esp.getEndDate(), es.getEndDate());
		assertEquals(esp.getStartDate(), es.getStartDate());
		assertEquals(esp.getAddress(), es.getAddress());
		// TODO manque getCity
	}

	@Test
	public void testSearch() {

		Date start = new Date();
		Date end = new Date();
		em.getTransaction().begin();
		SocialEntity member3 = new SocialEntity("zaza", "zaza",
				"zaza1@gmail.com");
		em.persist(member3);
		Meeting es1 = mf.createMeeting(member3, "tata", "tete", end, false, start,
				"address", "city");
		SocialEntity member2 = new SocialEntity("zaza", "zaza",
				"zaza2@gmail.com");
		em.persist(member2);
		Meeting es2 = mf.createMeeting(member2, "titi", "toto", end, false, start,
				"address", "city");
		SocialEntity member = new SocialEntity("zaza", "zaza",
				"zaza3@gmail.com");
		em.persist(member);
		Meeting es3 = mf.createMeeting(member, "tutu", "tyty", end, false, start,
				"address", "city");
		em.getTransaction().commit();
		String searchText = "titi";
		List<Meeting> results = mf.searchMeeting(searchText);
		Meeting mRes = results.get(0);
		assertEquals(es2.getTitle(), mRes.getTitle());
		assertEquals(es2.getContent(), mRes.getContent());
	}

	@Test 
	public void testDelete() {
		Date start = new Date();
		Date end = new Date();
		em.getTransaction().begin();
		SocialEntity member = new SocialEntity("zaza", "zaza",
				"zaza4@gmail.com");
		em.persist(member);
		Meeting es1 = mf.createMeeting(member, "tata", "tete", end, false, start,
				"address", "city");
		SocialEntity member2 = new SocialEntity("zaza", "zaza",
				"zaza5@gmail.com");
		em.persist(member2);
		Meeting es2 = mf.createMeeting(member2, "titi", "toto", end, false, start,
				"address", "city");
		SocialEntity member3 = new SocialEntity("zaza", "zaza",
				"zaza6@gmail.com");
		em.persist(member3);
		Meeting es3 = mf.createMeeting(member3, "tutu", "tyty", end, false, start,
				"address", "city");

		em.getTransaction().commit();

		em.getTransaction().begin();
		
		mf.deleteMeeting(es2.getId());
		em.getTransaction().commit();
		assertNull(em.find(Meeting.class, es2.getId()));

	}
}
