/**
 * 
 */
package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Inscription;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

/**
 * @author romuald druelle
 * 
 */
public class InscriptionTest {

    private EntityManager em;

    @Before
    public void setUp() {
        em = TestEntityManagerProvider.getInstance().getEntityManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPersist() {
        SocialEntity entite = new SocialEntity("Blanquette", "Veau", "BlanquetteVeau@jaifaim.com");
        entite.setNom("entiteInscription");
        em.getTransaction().begin();
        em.persist(entite);
        em.getTransaction().commit();
        Inscription insc = new Inscription(entite);
        em.getTransaction().begin();
        em.persist(insc);
        em.getTransaction().commit();
        int monId = insc.getEntite().getId();
        assertNotNull("id not null", monId);
        assertEquals(insc.getEtat(), "En attente d'inscription");
    }

    @Test(expected = javax.persistence.RollbackException.class)
    public void testUniqueConstraints() {
        SocialEntity es = new SocialEntity();
        es.setPrenom("victor");
        es.setNom("hugo");
        es.setEmail("totoinscription@gmail.com");

        Inscription i1 = new Inscription();
        i1.setEntite(es);


        Inscription i2 = new Inscription();
        i2.setEntite(es);

        em.getTransaction().begin();
        em.persist(es);
        em.persist(i1);
        em.persist(i2);
        em.getTransaction().commit();
    }

    @Test(expected = javax.persistence.RollbackException.class)
    public void testEntiteSocialeNotNull() {
        Inscription inscription = new Inscription();
        em.getTransaction().begin();
        em.persist(inscription);
        em.getTransaction().commit();
    }
}
