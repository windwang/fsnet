package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class TopicTest {

	private EntityManager em;

	@Before
	public void setUp() {
		em = TestEntityManagerProvider.getInstance().getEntityManager();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testPersist() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		Topic top = new Topic("master pro", date, null, null, null);
		em.getTransaction().begin();
		em.persist(top);
		em.getTransaction().commit();
		int monId = top.getId();
		assertNotNull("id not null", monId);
	}
}
