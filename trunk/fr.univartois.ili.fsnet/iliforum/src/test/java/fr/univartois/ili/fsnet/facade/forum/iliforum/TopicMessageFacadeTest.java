package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;

public class TopicMessageFacadeTest {
	private EntityManager em;
    private TopicMessageFacade tmf;
    private SocialEntityFacade sef;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        tmf = new TopicMessageFacade(em);
        sef = new SocialEntityFacade(em);
    }
    
    @Test
    public void testCreate() {
    	String body= "body";
    	SocialEntity from = sef.createSocialEntity("topic", "message",
        "topicmess1@gmail.com");
    	Topic topic = null;
    	TopicMessage message = tmf.createTopicMessage(body, from, topic);
    }
}
