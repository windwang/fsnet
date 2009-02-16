package fr.univartois.ili.fsnet.facade.iliforum.test;

import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.facade.iliforum.IliForumFacade;

public class IliForumFacadeTest {

    IliForumFacade iff;

    @Before
    public void setUp() {
	System.err.println("Le before est execute");
	iff = new IliForumFacade();
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
	System.err.println("Le test est execute");
	DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
	Date date = (Date) formatter.parse("29/01/02");
	Hub hub = new Hub("ma communaute", date, null);
	iff.addHub(hub);
	int monId = hub.getId();
	assertNotNull("id not null", monId);
    }

}
