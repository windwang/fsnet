package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class AnnouncementFacadeTest {

    private EntityManager em;
    private AnnouncementFacade af;
    private SocialEntityFacade sef;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        af = new AnnouncementFacade(em);
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

        Announcement ann = af.createAnnouncement(member, eventName,
                eventDescription, endDate, isPrivate);
        Announcement annp = em.find(Announcement.class, ann.getId());
        assertEquals(annp.getCreator(), ann.getCreator());
        assertEquals(annp.getTitle(), ann.getTitle());
        assertEquals(annp.getContent(), ann.getContent());
        assertEquals(annp.getEndDate(), ann.getEndDate());
    }

    @Test
    public void testSearch() {
    }
}
