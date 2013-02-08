package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * 
 * @author mickael watrelot - micgamers@gmail.com
 * 
 * 
 */

public class MessageTest {

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		fact.createEntityManager();
	}

	@After
	public void tearDown() {
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullBody() {
		SocialEntity form = new SocialEntity();

		new Message(null, form) {
			private static final long serialVersionUID = 1L;
		};

	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullForm() {

		new Message("body", null) {
			private static final long serialVersionUID = 1L;
		};

	}

	@Test
	public void testSetByMethodsAndGet() {
		Message m = new Message() {
			private static final long serialVersionUID = 1L;
		};
		Date creationDate = new Date();
		SocialEntity se = new SocialEntity();
		int id = 11;

		m.setId(id);
		m.setCreationDate(creationDate);
		m.setPropMsg(se);

		assertEquals(id, m.getId());
		assertEquals(creationDate, m.getCreationDate());
		assertEquals(se, m.getFrom());
	}

}
