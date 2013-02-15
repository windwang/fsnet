package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;

public class HubTest {

    private EntityManager em;

    @Before
    public void setUp() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
        em = fact.createEntityManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPersist() throws ParseException {
        final SocialEntity socialEntity = new SocialEntity("ktest6", "test6", "test6d@test.com");
        final Community community = new Community(socialEntity, "Ma comm");
        Hub hub = new Hub(community, socialEntity, "mon hub");
        em.getTransaction().begin();
        em.persist(socialEntity);
        em.persist(community);
        em.persist(hub);
        em.getTransaction().commit();
        Hub hub2 = em.find(Hub.class, hub.getId());

        assertEquals(hub.getId(), hub2.getId());
        assertEquals(hub.getCreationDate(), hub2.getCreationDate());
    }
    
    @Test
    public void testSetAndGetTopics(){
    	Hub hub = new Hub() ;
    	Topic top = new Topic();
		List<Topic> topics = new ArrayList<>();
		topics.add(top);
		hub.setTopics(topics);	
		assertEquals(topics, hub.getTopics());
		assertEquals(topics.size(), hub.getTopics().size());
    }
    
    @Test
    public void testSetAndGetCommunity(){
    	Hub hub = new Hub();
    	Community com = new Community() ;
    	hub.setCommunity(com);
    	assertEquals(com, hub.getCommunity());
    }
    
    @Test
    public void testHubRemove(){
    	Hub hub = new Hub() ;
    	
    	Topic top = new Topic();
		List<Topic> topics = new ArrayList<>();
		topics.add(top);
		hub.setTopics(topics);

    	Community com = new Community() ;
    	hub.setCommunity(com);
    	
		hub.onHubRemove();
		assertNull(hub.getCommunity());
		assertEquals(0, topics.size());
    }
}
    
