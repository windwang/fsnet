package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Annonce;
import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class AnnonceTest {

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
		Annonce annonce = new Annonce(date);
		em.getTransaction().begin();
		em.persist(annonce);
		em.getTransaction().commit();
		Annonce annonce2 = em.find(Annonce.class, annonce.getId());
		assertEquals(annonce.getId(), annonce2.getId());
		assertEquals(annonce.getDateAnnonce(), annonce2.getDateAnnonce());
	}

	@Test
	public void testPersist2() throws ParseException {
		String nom = "UserIli";
		String contenu = "content";
		String visible = "visible";
		EntiteSociale es = new EntiteSociale("name", "prenom", "mail");
		em.getTransaction().begin();
		em.persist(es);
		em.getTransaction().commit();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		Date dateFin = (Date) formatter.parse("29/03/02");
		Annonce annonce = new Annonce(nom, date, contenu, dateFin, visible, es);
		em.getTransaction().begin();
		em.persist(annonce);
		em.getTransaction().commit();
		Annonce annonce2 = em.find(Annonce.class, annonce.getId());
		assertEquals(annonce.getId(), annonce2.getId());
		assertEquals(annonce.getDateAnnonce(), annonce2.getDateAnnonce());
		assertEquals(annonce.getDateFinAnnonce(), annonce2.getDateFinAnnonce());
	}
}
