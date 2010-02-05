package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;


public class FSNetConfigurationFacadeTest {

	private EntityManager em;
	private FSNetConfigurationFacade fcf;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        fcf = new FSNetConfigurationFacade(em);
    }
    
	@Test
	public void testSMTPPort() {
		em.getTransaction().begin();		
		int smtpPort = 2225;
		fcf = new FSNetConfigurationFacade(em);
		fcf.setSMTPPort(smtpPort);
		em.getTransaction().commit();
		assertEquals(2225,fcf.getSMTPPort());
	}
	
	@Test 
	public void testSMTPHost(){
		em.getTransaction().begin();		
		String smtpHost = "smtp.gmail.com";
		fcf = new FSNetConfigurationFacade(em);
		fcf.setSMTPHost(smtpHost);
		em.getTransaction().commit();
		assertEquals("smtp.gmail.com",fcf.getSMTPHost());
	}
	
	@Test 
	public void testSMTPUser(){
		em.getTransaction().begin();		
		String smtpUser = "mikes";
		fcf = new FSNetConfigurationFacade(em);
		fcf.setSMTPUser(smtpUser);
		em.getTransaction().commit();
		assertEquals("mikes",fcf.getSMTPUser());
	}
	
	@Test 
	public void testSMTPPassword(){
		em.getTransaction().begin();		
		String smtpPassword = "test";
		fcf = new FSNetConfigurationFacade(em);
		fcf.setSMTPPassword(smtpPassword);
		em.getTransaction().commit();
		assertEquals("test",fcf.getSMTPPassword());
	}
	
	@Test 
	public void testTLS(){
		em.getTransaction().begin();		
		boolean tls = Boolean.TRUE;
		fcf = new FSNetConfigurationFacade(em);
		fcf.setTLS(tls);
		em.getTransaction().commit();
		assertEquals(Boolean.TRUE.toString(),fcf.getTLS());
	}
	
	@Test 
	public void testSSL(){
		em.getTransaction().begin();		
		boolean ssl = Boolean.TRUE;
		fcf = new FSNetConfigurationFacade(em);
		fcf.setSSL(ssl);
		em.getTransaction().commit();
		assertEquals(Boolean.TRUE.toString(),fcf.getSSL());
	}
	
	@Test 
	public void testAuthentification(){
		em.getTransaction().begin();		
		boolean authentification = Boolean.TRUE;
		fcf = new FSNetConfigurationFacade(em);
		fcf.setAuthentification(authentification);
		em.getTransaction().commit();
		assertEquals(Boolean.TRUE.toString(),fcf.getAuthentification());
	}
	
	@Test 
	public void testMailFrom(){
		em.getTransaction().begin();		
		String mailFrom = "mic@gmail.com";
		fcf = new FSNetConfigurationFacade(em);
		fcf.setMailFrom(mailFrom);
		em.getTransaction().commit();
		assertEquals("mic@gmail.com",fcf.getMailFrom());
	}
	
	@Test 
	public void testUrl(){
		em.getTransaction().begin();		
		String url = "http://localhost:8080/fsnet/";
		fcf = new FSNetConfigurationFacade(em);
		fcf.setUrl(url);
		em.getTransaction().commit();
		assertEquals("http://localhost:8080/fsnet/",fcf.getUrl());
	}
}
