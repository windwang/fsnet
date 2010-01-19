package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class MessageTest {

	private EntityManager em;

	@Before
	public void setUp() {
		em = TestEntityManagerProvider.getInstance().getEntityManager();
	}

	@After
	public void tearDown() {

	}

	@Test
	/**
	 * Check that if we can persiste un entity Message
	 */
	public void testPersist() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		EntiteSociale ent1 = new EntiteSociale("toto", "tutu", "totu@gmail.com");
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		Message msg1 = new Message("mon message", date, ent1, null);
		em.getTransaction().begin();
		em.persist(msg1);
		em.getTransaction().commit();
		Message msg2 = em.find(Message.class, msg1.getId());
		assertEquals(msg2.getId(),msg1.getId());
		assertEquals(msg2.getContenu(),msg1.getContenu());
		assertEquals(msg2.getDateMessage(),msg1.getDateMessage());
		assertEquals(msg2.getPropMsg(),msg1.getPropMsg());
	}
	
	@Test(expected = RollbackException.class)
	/**
	 * Check that the contenu of the message can not be null
	 */
	public void testContenuMessageIsNotNull() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		EntiteSociale ent1 = new EntiteSociale("toto", "tutu", "totu@gmail.com");
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		Message msg1 = new Message(null, date, ent1, null);
		em.getTransaction().begin();
		em.persist(msg1);
		em.getTransaction().commit();
	}
	
	@Test(expected = RollbackException.class)
	/**
	 * Check that the date of the message can not be null
	 */
	public void testDateMessageIsNotNull() throws ParseException {
		EntiteSociale ent1 = new EntiteSociale("toto", "tutu", "totu@gmail.com");
		em.getTransaction().begin();
		em.persist(ent1);
		em.getTransaction().commit();
		Message msg1 = new Message("mon message", null, ent1, null);
		em.getTransaction().begin();
		em.persist(msg1);
		em.getTransaction().commit();
	}
	@Test(expected = RollbackException.class)
	/**
	 * Check that the creator of the message can not be null
	 */
	public void testCreatorMessageIsNotNull() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		Message msg1 = new Message("mon message", date, null, null);
		em.getTransaction().begin();
		em.persist(msg1);
		em.getTransaction().commit();
	}
}