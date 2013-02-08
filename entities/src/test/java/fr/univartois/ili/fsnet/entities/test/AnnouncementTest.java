package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * 
 * @author mickael watrelot - micgamers@gmail.com
 * 
 */
public class AnnouncementTest {

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
	public void testPersist() throws ParseException {
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		Announcement annonce = new Announcement(socialEntity,
				"test Announcement", "HEHEHEHEHE", new Date(), false);
		em.getTransaction().begin();
		em.persist(socialEntity);
		em.persist(annonce);
		em.getTransaction().commit();
		Announcement annonce2 = em.find(Announcement.class, annonce.getId());
		assertEquals(annonce.getId(), annonce2.getId());
		assertEquals(annonce.getEndDate(), annonce2.getEndDate());
	}

	@Test
	public void testPersistTwo() throws ParseException {
		SocialEntity es = new SocialEntity("name", "prenom",
				"mailannonce@mail.com");
		em.getTransaction().begin();
		em.persist(es);
		em.getTransaction().commit();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		Announcement annonce = new Announcement(es, "TITLE", "content", date,
				true);
		em.getTransaction().begin();
		em.persist(annonce);
		em.getTransaction().commit();
		Announcement annonce2 = em.find(Announcement.class, annonce.getId());
		assertEquals(annonce.getId(), annonce2.getId());
		assertEquals(annonce.getCreationDate(), annonce2.getCreationDate());
		assertEquals(annonce.getEndDate(), annonce2.getEndDate());
		assertEquals(annonce.getContent(), annonce2.getContent());
		assertEquals(annonce.getCreator(), annonce2.getCreator());
	}

	@Test
	public void testGeneratedValueId() throws ParseException {
		final SocialEntity socialEntity = new SocialEntity("test2", "test2",
				"test2@test.com");
		Announcement annonce = new Announcement(socialEntity,
				"test2 Announcement", "HEHEHEHEHE2", new Date(), false);
		em.getTransaction().begin();
		em.persist(socialEntity);
		em.persist(annonce);
		em.getTransaction().commit();
		final SocialEntity socialEntity1 = new SocialEntity("test3", "test3",
				"test3@test.com");
		Announcement annonce2 = new Announcement(socialEntity1,
				"test 3Announcement", "HEHEHEHEHE3", new Date(), false);
		em.getTransaction().begin();
		em.persist(socialEntity1);
		em.persist(annonce2);
		em.getTransaction().commit();
		assertEquals(annonce.getId() + 1, annonce2.getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDateIsNotNull() {
		Announcement annonce = new Announcement(null, null, null, null, true);
		em.getTransaction().begin();
		em.persist(annonce);
		em.getTransaction().commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullContent() {
		final SocialEntity creator = new SocialEntity("test", "test",
				"test@test.com");
		final String title = "titreeeeee";
		boolean bool = true;
		Date endDate = new Date();
		new Announcement(creator, title, null, endDate, bool);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullEndDate() {
		final SocialEntity creator = new SocialEntity("test", "test",
				"test@test.com");
		final String title = "titreeeeee";
		boolean bool = true;
		String content = "content";

		new Announcement(creator, title, content, null, bool);
	}

	@Test
	public void testSetByMethodsAndGet() {
		Announcement ann = new Announcement();
		Date endDate = new Date();
		String content = "content";
		ann.setEndDate(endDate);
		ann.setContent(content);
		assertEquals(endDate, ann.getEndDate());
		assertEquals(content, ann.getContent());
	}

}
