package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.AnnouncementFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

public class AnnouncementFacadeTest {

    private EntityManager em;
    private AnnouncementFacade af;
    private InteractionFacade interactionFcade;
    private SocialEntityFacade sef;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        af = new AnnouncementFacade(em);
        sef = new SocialEntityFacade(em);
        interactionFcade = new InteractionFacade(em);
    }

    @Test
    public void testCreate() {
        SocialEntity member = sef.createSocialEntity("zaza", "zaza",
                "zaza1@gmail.com");
        String eventName = "eventName";
        String eventDescription = "eventDescription";
        Date endDate = new Date();
        Boolean isPrivate = false;
        
        em.getTransaction().begin();
        
		Announcement ann = af.createAnnouncement(member, eventName,
				eventDescription, endDate, isPrivate);
		
		em.getTransaction().commit();
		
		Announcement annp = em.find(Announcement.class, ann.getId());
		
		assertEquals(annp.getCreator(), ann.getCreator());
		assertEquals(annp.getTitle(), ann.getTitle());
		assertEquals(annp.getContent(), ann.getContent());
		assertEquals(annp.getEndDate(), ann.getEndDate());
	}

	@Test
	public void testSearch() {
		Date end = new Date();
		em.getTransaction().begin();
		SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
				"zaza2@gmail.com");
		af.createAnnouncement(member3, "tata", "tete", end,
				false);

		SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
				"zaza3@gmail.com");
		Announcement ann2 = af.createAnnouncement(member2, "titi", "toto", end,
				false);

		SocialEntity member = sef.createSocialEntity("zaza", "zaza",
				"zaza4@gmail.com");
		af.createAnnouncement(member, "tutu", "tyty", end,
				false);

		em.getTransaction().commit();
		String searchText = "titi";
		List<Announcement> results = af.searchAnnouncement(searchText);
		Announcement aRes = results.get(0);
		assertEquals(ann2.getTitle(), aRes.getTitle());
		assertEquals(ann2.getContent(), aRes.getContent());
	}
	
	@Test
	public void testDelete1() {
		Date end = new Date();
		em.getTransaction().begin();
		SocialEntity member = sef.createSocialEntity("zaza", "zaza",
				"zaza5@gmail.com");
		af.createAnnouncement(member, "tata", "tete", end, false);
		SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
				"zaza6@gmail.com");
		Announcement ann2 = af.createAnnouncement(member2, "titi", "toto", end, false);
		SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
				"zaza7@gmail.com");
		af.createAnnouncement(member3, "tutu", "tyty", end, false);

		em.getTransaction().commit();

		em.getTransaction().begin();

		interactionFcade.deleteInteraction(member2, ann2);
		em.getTransaction().commit();
		assertNull(em.find(Announcement.class, ann2.getId()));
	}
	
	@Test
	public void testModifiyAnnoucement(){
		em.getTransaction().begin();
		SocialEntity member = sef.createSocialEntity("tutu", "tutu","tutu@gmail.com");
		Announcement ann = af.createAnnouncement(member, "annonce", "annonce", new Date(), false);
		em.getTransaction().commit();
		
		assertEquals(ann.getTitle(), "annonce");
		assertEquals(ann.getContent(), "annonce");
				
		Date dateModif = new Date() ;
		
		em.getTransaction().begin();
		af.modifyAnnouncement(ann.getId(), "annonceModif", "annonceModif", dateModif);
		em.getTransaction().commit();
		
		assertEquals(ann.getTitle(), "annonceModif");
		assertEquals(ann.getContent(), "annonceModif");
		assertEquals(ann.getEndDate(), dateModif);		
	}
	
	@Test
	public void testGetLastAnnoun(){
		
		em.getTransaction().begin();
		SocialEntity member = sef.createSocialEntity("zozo", "zozo","zozo42@gmail.com");		
		Announcement ann1 = af.createAnnouncement(member, "annonce", "annonce", new Date(), false);
		SocialEntity member2 = sef.createSocialEntity("zuzu", "zuzu","zuzu62@gmail.com");
		member2.setLastConnection(addDaysToDate(new Date(),1));
		Announcement ann2 = af.createAnnouncement(member2, "annonce", "annonce", new Date(), false);
		ann2.setCreationDate(addDaysToDate(new Date(),2));
		em.getTransaction().commit();
		List<Announcement> annonces = af.getLastAnnouncementForTheLastUserConnexion(member2);
		assertEquals(1, annonces.size());
		assertTrue(annonces.contains(ann2));
		assertFalse(annonces.contains(ann1));
	}

    public static Date addDaysToDate(Date date, int nbDays){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, nbDays);
        return cal.getTime();
    }
	
	@Test
	public void testGetUserAnnoun(){
		em.getTransaction().begin();
		SocialEntity member = sef.createSocialEntity("zaza", "zaza","zaza12@gmail.com");
		Announcement ann1 = af.createAnnouncement(member, "annonce", "annonce", new Date(), false);
		Announcement ann2 = af.createAnnouncement(member, "annonce2", "annonce2", new Date(), false);
		em.getTransaction().commit();
		List<Announcement> annonces = af.getUserAnnouncements(member);
		assertEquals(2, annonces.size());
		assertTrue(annonces.contains(ann1));
		assertTrue(annonces.contains(ann2));		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testModifyWithExceptionAnnNull(){
		af.modifyAnnouncement(1, null, null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testModifyWithExceptionAnnDescrNull(){
		af.modifyAnnouncement(1, "test", null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testModifyWithExceptionEndDateNull(){
		af.modifyAnnouncement(1, "test", "test", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testModifyWithExceptionAnnounNull(){
		af.modifyAnnouncement(-1, "test", "test", new Date());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchWithNullText(){
		af.searchAnnouncement(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetLastAnnounWithNullEntity(){
		af.getLastAnnouncementForTheLastUserConnexion(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetUserAnnounWithNullEntity(){
		af.getUserAnnouncements(null);
	}
	
	
	@Test(expected=UnauthorizedOperationException.class)
	public void testDelete2() {
		Date end = new Date();
		em.getTransaction().begin();
		SocialEntity member = sef.createSocialEntity("zaza", "zaza",
				"zaza8@gmail.com");
		af.createAnnouncement(member, "tata", "tete", end, false);
		SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
				"zaza9@gmail.com");
		Announcement ann2 = af.createAnnouncement(member2, "titi", "toto", end, false);
		SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
				"zaza10@gmail.com");
		af.createAnnouncement(member3, "tutu", "tyty", end, false);

		em.getTransaction().commit();

		em.getTransaction().begin();
		interactionFcade.deleteInteraction(member, ann2);
		em.getTransaction().commit();
	}
}
