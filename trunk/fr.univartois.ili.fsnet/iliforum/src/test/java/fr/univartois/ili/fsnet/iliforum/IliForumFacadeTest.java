package fr.univartois.ili.fsnet.iliforum;

import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.forum.iliforum.IliForumFacade;

public class IliForumFacadeTest {

    IliForumFacade iff;

    @Before
    public void setUp() {
        System.err.println("Le before est execute");
        iff = IliForumFacade.getInstance();
    }

    @After
    public void tearDown() {
        System.err.println("Le after est execute");
        if (iff != null) {
            iff.close();
        }
    }

    @Test
    public void addHubTest() throws ParseException {
        System.err.println("Le test addHubTest est execute");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("29/01/02");
        Hub hub = new Hub("ma communaute", date);
        iff.addHub(hub);
        int monId = hub.getId();
        assertNotNull("id not null", monId);
    }

    @Test
    public void addTopicTest() throws ParseException {
        System.err.println("Le test addTopicTest est execute");
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("30/01/02");
        Hub hub = new Hub("ma communaute", date);
        iff.addHub(hub);
        Topic topic = new Topic("mon premier topic", date, null, hub, null);
        iff.addTopic(topic);
        int monId = topic.getId();
        assertNotNull("id not null", monId);
    }

    @Test
    public void addMessageTest() throws ParseException {
        System.err.println("le test addMessageTest est execute");
        List<Topic> mestopics = iff.getListTopic();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        Date date = (Date) formatter.parse("30/01/02");
        Message mess = new Message("un messaaaaaage", date, null, mestopics.get(0));
        iff.addMessage(mess);
        int monId = mess.getId();
        assertNotNull("id not null", monId);
    }
}
