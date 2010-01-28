package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class MeetingFacadeTest {

    private EntityManager em;
    private MeetingFacade mf;
    private SocialEntityFacade sef;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        mf = new MeetingFacade(em);
        sef = new SocialEntityFacade(em);
    }

    @Test
    public void testCreate() {
        SocialEntity member = sef.createSocialEntity("zaza", "zaza",
                "zaza@gmail.com");
        String eventName = "eventName";
        String eventDescription = "eventDescription";
        Date endDate = new Date();
        Boolean isPrivate = false;
        Date startDate = new Date();
        String address = "address";
        String city = "city";

        Meeting es = mf.createMeeting(member, eventName, eventDescription,
                endDate, isPrivate, startDate, address, city);
        Meeting esp = em.find(Meeting.class, es.getId());
        assertEquals(esp.getCreator(), es.getCreator());
        assertEquals(esp.getTitle(), es.getTitle());
        assertEquals(esp.getContent(), es.getContent());
        assertEquals(esp.getEndDate(), es.getEndDate());
        assertEquals(esp.getStartDate(), es.getStartDate());
        assertEquals(esp.getAddress(), es.getAddress());
        // TODO manque getCity
    }

    @Test
    public void testSearch() {

        Date start = new Date();
        Date end = new Date();
        em.getTransaction().begin();
        SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer1@gmail.com");
        Meeting m1 = mf.createMeeting(member3, "tata", "tete", end, false,
                start, "address", "city");

        SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer2@gmail.com");
        Meeting m2 = mf.createMeeting(member2, "titi", "toto", end, false,
                start, "address", "city");

        SocialEntity member = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer3@gmail.com");
        Meeting m3 = mf.createMeeting(member, "tutu", "tyty", end, false,
                start, "address", "city");

        em.getTransaction().commit();
        String searchText = "titi";
        List<Meeting> results = mf.searchMeeting(searchText);
        Meeting mRes = results.get(0);
        assertEquals(m2.getTitle(), mRes.getTitle());
        assertEquals(m2.getContent(), mRes.getContent());
    }

    @Test
    public void testDelete() {
        Date start = new Date();
        Date end = new Date();
        em.getTransaction().begin();
        SocialEntity member = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer4@gmail.com");
        Meeting m1 = mf.createMeeting(member, "tata", "tete", end, false,
                start, "address", "city");
        SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer5@gmail.com");
        Meeting m2 = mf.createMeeting(member2, "titi", "toto", end, false,
                start, "address", "city");
        SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer6@gmail.com");
        Meeting m3 = mf.createMeeting(member3, "tutu", "tyty", end, false,
                start, "address", "city");

        em.getTransaction().commit();

        em.getTransaction().begin();

        mf.deleteMeeting(m2.getId());
        em.getTransaction().commit();
        assertNull(em.find(Meeting.class, m2.getId()));

    }
}
