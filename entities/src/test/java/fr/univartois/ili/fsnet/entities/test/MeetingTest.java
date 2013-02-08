package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class MeetingTest {

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
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		final SocialEntity socialEntity = new SocialEntity("ktest6", "test6",
				"test6@test.com");
		Meeting manif = new Meeting(socialEntity, "Meeting", "null", date,
				true, date, new Address(), null);
		em.getTransaction().begin();
		em.persist(socialEntity);
		em.persist(manif);
		em.getTransaction().commit();
	}

	@Test
	public void testCreateEmptyPrivateMessage() {
		Meeting m = new Meeting();	
		assertNull(m.getCreator());
		assertNull(m.getTitle());
		assertNull(m.getContent());
		assertNull(m.getEndDate());
		assertNull(m.getStartDate());
		assertNull(m.getAddress());
		assertNull(m.getRecallDate());
	}
	
    @Test
    public void testSetByMethodsAndGet() throws ParseException {
    	DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		Address a = new Address();
		Meeting m = new Meeting();   
    	m.setAddress(a);
    	m.setRecallDate(date);
    	m.setStartDate(date);
    	assertEquals(date, m.getRecallDate());
    	assertEquals(date, m.getStartDate());
    	assertEquals(a, m.getAddress());
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullStartDate() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		final SocialEntity socialEntity = new SocialEntity("ktest6", "test6",
				"test6@test.com");
		new Meeting(socialEntity, "Meeting", "null", date, true, null,
				new Address(), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullAddress() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		final SocialEntity socialEntity = new SocialEntity("ktest6", "test6",
				"test6@test.com");
		new Meeting(socialEntity, "Meeting", "null", date, true, date, null,
				null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullAddressAndStartDate() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		final SocialEntity socialEntity = new SocialEntity("ktest6", "test6",
				"test6@test.com");
		new Meeting(socialEntity, "Meeting", "null", date, true, null, null,
				null);
	}
	
}
