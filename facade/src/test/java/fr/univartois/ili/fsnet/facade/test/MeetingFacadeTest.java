package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

public class MeetingFacadeTest {

    private EntityManager em;
    private MeetingFacade mf;
    private SocialEntityFacade sef;
    private InteractionFacade interactionFacade;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        mf = new MeetingFacade(em);
        sef = new SocialEntityFacade(em);
        interactionFacade = new InteractionFacade(em);
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

        em.getTransaction().begin();
        
        Meeting es = mf.createMeeting(member, eventName, eventDescription,
                endDate, isPrivate, startDate, address, city,null);
        
        em.getTransaction().commit();
        
        Meeting esp = em.find(Meeting.class, es.getId());
        assertEquals(esp.getCreator(), es.getCreator());
        assertEquals(esp.getTitle(), es.getTitle());
        assertEquals(esp.getContent(), es.getContent());
        assertEquals(esp.getEndDate(), es.getEndDate());
        assertEquals(esp.getStartDate(), es.getStartDate());
        assertEquals(esp.getAddress(), es.getAddress());
    }
    
    @Test
    public void testCreateAndGet() {
        SocialEntity member = sef.createSocialEntity("tata", "tata",
                "tata2@gmail.com");
        String eventName = "eventName2";
        String eventDescription = "eventDescription2";
        Date endDate = new Date();
        Boolean isPrivate = false;
        Date startDate = new Date();
        Date recallDate = new Date();
        String address = "address";
        String city = "city";

        em.getTransaction().begin();
        
        Meeting es = mf.createMeeting(member, eventName, eventDescription,
                endDate, isPrivate, startDate, address, city,recallDate);
        
        em.getTransaction().commit();
        
        Meeting esp = mf.getMeeting(es.getId());
        assertEquals(esp.getCreator(), es.getCreator());
        assertEquals(esp.getTitle(), es.getTitle());
        assertEquals(esp.getContent(), es.getContent());
        assertEquals(esp.getEndDate(), es.getEndDate());
        assertEquals(esp.getStartDate(), es.getStartDate());
        assertEquals(esp.getAddress(), es.getAddress());
        assertEquals(esp.getRecallDate(), es.getRecallDate());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullMember() {
    	em.getTransaction().begin();     
        mf.createMeeting(null, "test", "testDescription",
                new Date(), false, new Date(), "test Adresse", "test City",null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullTitle() {
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("zaz", "zaz",
                "zaz@gmail.com");
    	em.getTransaction().commit();
    	em.getTransaction().begin();     
        mf.createMeeting(se, null, "testDescription",
                new Date(), false, new Date(), "test Adresse", "test City",null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullDescription() {
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("zaz", "zaz",
                "zaz2@gmail.com");
    	em.getTransaction().commit();
    	em.getTransaction().begin();     
        mf.createMeeting(se, "test", null,
                new Date(), false, new Date(), "test Adresse", "test City",null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullEndDate() {
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("zaz", "zaz",
                "zaz3@gmail.com");
    	em.getTransaction().commit();
    	em.getTransaction().begin();     
        mf.createMeeting(se, "test", "testDescription",
                null, false, new Date(), "test Adresse", "test City",null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullPrivateType() {
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("zaz", "zaz",
                "zaz4@gmail.com");
    	em.getTransaction().commit();
    	em.getTransaction().begin();     
        mf.createMeeting(se, "test", null,
                new Date(), null, new Date(), "test Adresse", "test City",null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullStartDate() {
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("zaz", "zaz",
                "zaz5@gmail.com");
    	em.getTransaction().commit();
    	em.getTransaction().begin();     
        mf.createMeeting(se, "test", "testDescription",
        		new Date(), false, null, "test Adresse", "test City",null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullAddress() {
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("zaz", "zaz",
                "zaz6@gmail.com");
    	em.getTransaction().commit();
    	em.getTransaction().begin();     
        mf.createMeeting(se, "test", "testDescription",
        		new Date(), false, new Date(), null, "test City",null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithNullCity() {
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("zaz", "zaz",
                "zaz7@gmail.com");
    	em.getTransaction().commit();
    	em.getTransaction().begin();     
        mf.createMeeting(se, "test", "testDescription",
        		new Date(), false, new Date(), "test Adresse", null ,null);  
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testCreateWithAllNullParameters() {
    	em.getTransaction().begin();     
        mf.createMeeting(null, null, null,
        		null, null, null, null, null ,null);  
        em.getTransaction().commit();
    }
    

    @Test
    public void testSearch() {

        Date start = new Date();
        Date end = new Date();
        em.getTransaction().begin();
        SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer1@gmail.com");
        mf.createMeeting(member3, "tata", "tete", end, false,
                start, "address", "city",null);

        SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer2@gmail.com");
        Meeting m2 = mf.createMeeting(member2, "titi", "toto", end, false,
                start, "address", "city",null);

        SocialEntity member = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer3@gmail.com");
        mf.createMeeting(member, "tutu", "tyty", end, false,
                start, "address", "city",null);

        em.getTransaction().commit();
        String searchText = "titi";
        List<Meeting> results = mf.searchMeeting(searchText);
        Meeting mRes = results.get(0);
        assertEquals(m2.getTitle(), mRes.getTitle());
        assertEquals(m2.getContent(), mRes.getContent());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testSearchWithNull() {
        mf.searchMeeting(null);
    }

    @Test
    public void testDelete1() {
        Date start = new Date();
        Date end = new Date();
        em.getTransaction().begin();
        SocialEntity member = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer4@gmail.com");
        mf.createMeeting(member, "tata", "tete", end, false,
                start, "address", "city",null);
        SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer5@gmail.com");
        Meeting m2 = mf.createMeeting(member2, "titi", "toto", end, false,
                start, "address", "city",null);
        SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer6@gmail.com");
        mf.createMeeting(member3, "tutu", "tyty", end, false,
                start, "address", "city",null);

        em.getTransaction().commit();

        em.getTransaction().begin();

        interactionFacade.deleteInteraction(member2, m2);
        em.getTransaction().commit();
        assertNull(em.find(Meeting.class, m2.getId()));
    }
    
    @Test(expected=UnauthorizedOperationException.class)
    public void testDelete2() {
        Date start = new Date();
        Date end = new Date();
        em.getTransaction().begin();
        SocialEntity member = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer40@gmail.com");
        mf.createMeeting(member, "tata", "tete", end, false,
                start, "address", "city",null);
        SocialEntity member2 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer50@gmail.com");
        Meeting m2 = mf.createMeeting(member2, "titi", "toto", end, false,
                start, "address", "city",null);
        SocialEntity member3 = sef.createSocialEntity("zaza", "zaza",
                "BuildBrokenizer60@gmail.com");
        mf.createMeeting(member3, "tutu", "tyty", end, false,
                start, "address", "city",null);

        em.getTransaction().commit();

        em.getTransaction().begin();

        interactionFacade.deleteInteraction(member, m2);
        em.getTransaction().commit();
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetLastMeetingForTheLastUserConnexionWithNull() {
    	mf.getLastMeetingForTheLastUserConnexion(null) ;
    }
    
    
    @Test
    public void testGetAllMeetings() {
    	Date start = new Date();
        Date end = new Date();
        
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("name", "first", "mail@mail.com");
    	em.getTransaction().commit();
    	
    	em.getTransaction().begin();
    	Meeting me = mf.createMeeting(se, "reunion", "reunion", end, false,
                 start, "address", "city",null);
    	em.getTransaction().commit(); 	
    	
    	List<Meeting> lm = mf.listAllMeeting() ;
    	assertTrue(lm.contains(me)) ;
    	
    }
    
    @Test
    public void testGetAllMeetingsByUser() {
    	Date start = new Date();
        Date end = new Date();
        
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("name", "first", "zmail@mail.com");
    	em.getTransaction().commit();
    	
    	em.getTransaction().begin();
    	Meeting me = mf.createMeeting(se, "reunion", "reunion", end, false,
                 start, "address", "city",null);
    	em.getTransaction().commit(); 	
    	
    	List<Meeting> lm = mf.getAllUserMeeting(se) ;
    	assertTrue(lm.contains(me)) ;
    	
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetAllMeetingsByUserWithNullUser() {
    	mf.getAllUserMeeting(null) ;  	
    }
    
    @Test
    public void testGetUserMeeting() {
    	Date start = new Date();
        Date end = new Date();
        
    	em.getTransaction().begin();
    	SocialEntity se = sef.createSocialEntity("name", "first", "zemail@mail.com");
    	em.getTransaction().commit();
    	
    	em.getTransaction().begin();
    	Meeting me = mf.createMeeting(se, "reunion", "reunion", end, false,
                 start, "address", "city",null);
    	em.getTransaction().commit(); 	
    	
    	List<Meeting> lm = mf.getUserMeeting(se) ;
    	assertTrue(lm.contains(me)) ;
    	
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testGetUserMeetingWithNullUser() {   	
    	mf.getUserMeeting(null) ;
    	
    }
    
    @Test
    public void getMeetingsNow() {
    	mf.getMeetingsHavingRecallWhichOccurNow() ;
    }
    
    
}
