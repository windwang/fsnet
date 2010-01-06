package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertNotNull;

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

import fr.univartois.ili.fsnet.entities.Message;

public class MessageTest {
	private EntityManagerFactory emf;
	private EntityManager em;

	@Before
	public void setUp() {
		System.err.println("Le before est execute");
		emf = Persistence.createEntityManagerFactory("fsnetjpa");
		em = emf.createEntityManager();
	}

	@After
	public void tearDown() {
		System.err.println("Le after est execute");
		if (em != null) {
			em.close();
		}
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersist() throws ParseException {
		System.err.println("Le test est execute");
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		Message message = new Message("mon message", date, null, null);
		em.getTransaction().begin();
		em.persist(message);
		em.getTransaction().commit();
		int monId = message.getId();
		assertNotNull("id not null", monId);
	}
}
