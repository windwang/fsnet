package fr.univartois.ili.fsnet.entities.test;

import fr.univartois.ili.fsnet.entities.Address;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class MeetingTest {

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
        final SocialEntity socialEntity = new SocialEntity("ktest6", "test6", "test6@test.com");
        Meeting manif = new Meeting(socialEntity, "Meeting", "null", date, true, date, new Address());
        em.getTransaction().begin();
        em.persist(socialEntity);
        em.persist(manif);
        em.getTransaction().commit();
    }

}
