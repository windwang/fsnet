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

import fr.univartois.ili.fsnet.entities.Decideur;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.ActivityReport;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;
/**
 * 
 * @author mickael watrelot - micgamers@gmail.com
 *
 */
public class InteractionTest {

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
    	SocialEntity createur = new SocialEntity("testo", "bisas", "tosssss@gmail.com");
		em.getTransaction().begin();
		em.persist(createur);
		em.getTransaction().commit();
		Decideur decideur = new Decideur();
		em.getTransaction().begin();
        em.persist(decideur);
        em.getTransaction().commit();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        ActivityReport rapp = new ActivityReport(null, date);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
		Interaction inter = new Interaction(true, decideur, createur, rapp);
        em.getTransaction().begin();
        em.persist(inter);
        em.getTransaction().commit();
        Interaction inter2 = em.find(Interaction.class, inter.getId());
		assertEquals(inter.getId(), inter2.getId());
		assertEquals(inter.getDecideur(), inter2.getDecideur());
		assertEquals(inter.getCreator(), inter2.getCreator());
		assertEquals(inter.getReport(), inter2.getReport());
		assertEquals(inter.isValide(), inter2.isValide());
    }
    
    @Test
    public void testGeneratedValueId() throws ParseException {
    	SocialEntity createur = new SocialEntity("testos", "bisass", "tossissss@gmail.com");
		em.getTransaction().begin();
		em.persist(createur);
		em.getTransaction().commit();
		Decideur decideur = new Decideur();
		em.getTransaction().begin();
        em.persist(decideur);
        em.getTransaction().commit();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        ActivityReport rapp = new ActivityReport(null, date);
        em.getTransaction().begin();
        em.persist(rapp);
        em.getTransaction().commit();
		Interaction inter = new Interaction(true, decideur, createur, rapp);
        em.getTransaction().begin();
        em.persist(inter);
        em.getTransaction().commit();
        Interaction inter2 = new Interaction(true, decideur, createur, rapp);
        em.getTransaction().begin();
        em.persist(inter2);
        em.getTransaction().commit();
        assertEquals(inter.getId() + 1, inter2.getId());
    }
}
