package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class PrivateMessageFacadeTest {

    private EntityManager em;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
        em = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of sendPrivateMessage method, of class PrivateMessageFacade.
     */
    @Test
    public void testSendPrivateMessage() {
        System.out.println("sendMessage");
        String body = "Test message privé 1";
        em.getTransaction().begin();
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity from = sef.createSocialEntity("TestName 1", "TestFirstName1", "sendmessagetest1@gmail.com");
        String subject = "The subject test 1";
        SocialEntity to = sef.createSocialEntity("TestName 12", "TestFirstName12", "sendmessagetest12@gmail.com");

        PrivateMessageFacade instance = new PrivateMessageFacade(em);
        PrivateMessage result = instance.sendPrivateMessage(body, from, subject, to);
        em.getTransaction().commit();
        PrivateMessage pmfound = em.find(PrivateMessage.class, 1);
        // TODO assertSame because equals method not implement
        assertSame(from.getSentPrivateMessages().get(0), to.getReceivedPrivateMessages().get(0));
        assertSame(result, pmfound);
    }
}
