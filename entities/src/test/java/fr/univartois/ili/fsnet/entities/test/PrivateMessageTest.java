package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Ignore;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class PrivateMessageTest {

	private static final String SUBJECT = "Objet";
	private static final String BODY = "contenu du mail";
	private boolean reed = false;
	private SocialEntity to = new SocialEntity("momo", "momo", "momo@momo.fr");
	private SocialEntity from = new SocialEntity("pp", "pp", "pp@pp.fr");

	@Test
	public void testCreateEmptyPrivateMessage() {
		PrivateMessage pm = new PrivateMessage();
		assertNull(pm.getBody());
		assertNull(pm.getFrom());
		assertNull(pm.getSubject());
		assertNull(pm.getTo());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullSubject() {
		new PrivateMessage(BODY, from, null, to);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullTo() {
		new PrivateMessage(BODY, from, SUBJECT, null);
	}
	
    @Test
    public void testSetByMethodsAndGet() {
		PrivateMessage pm = new PrivateMessage();
    	pm.setBody(BODY);
    	pm.setReed(reed);
    	pm.setTo(to);
    	pm.setSubject(SUBJECT);
    	assertEquals(BODY, pm.getBody());
    	assertEquals(reed, pm.isReed());
    	assertEquals(to, pm.getTo());
    	assertEquals(SUBJECT, pm.getSubject());
    }
    
    @Test
    public void testSetByConstructorAndGet() {
    	PrivateMessage pm = new PrivateMessage(BODY, from, SUBJECT, to);
    	assertEquals(BODY, pm.getBody());
    	assertEquals(reed, pm.isReed());
    	assertEquals(to, pm.getTo());
    	assertEquals(SUBJECT, pm.getSubject());
    }   
	
}
