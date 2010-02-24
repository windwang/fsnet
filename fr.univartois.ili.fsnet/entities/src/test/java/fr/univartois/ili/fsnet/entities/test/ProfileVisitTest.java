package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.*;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.ProfileVisitePK;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ProfileVisitTest {
	
	private EntityManager em;
	
	private SocialEntity bibi;
	
	private SocialEntity bobo;
	
	private SocialEntity toto;
	
	private ProfileVisite pv;
	
	@Before 
	public void setUp(){
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
        em = fact.createEntityManager();
		bibi = new SocialEntity("bibi","bibi","bibi@bibi.fr");
		bobo = new SocialEntity("bobo","bobo","bobo@bobo.fr");
		toto = new SocialEntity("toto","toto","toto@bobo.fr");
		pv = new ProfileVisite(bobo, bibi);
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			fail();
		}
		
	}
	
	@After
	public void tearDown(){
		em.close();
	}
	
	@Test
	public void profileVisiteConstructorTest(){
		assertSame(bobo,pv.getVisited());
		assertSame(bibi,pv.getVisitor());
		assert(new Date().before(pv.getLastVisite()));
	}
	
	@Test 
	public void setAndGetVisitorTest(){
		pv.setVisitor(toto);
		assertSame(toto,pv.getVisitor());
	}
	
	@Test 
	public void setAndGetVisitedTest(){
		pv.setVisited(toto);
		assertSame(toto,pv.getVisited());
	}
	
	@Test 
	public void setAndGetLastVisiteTest(){
		Date d =new Date();
		pv.setLastVisite( d);
		assertSame(pv.getLastVisite(),d);
	}
	
	@Test 
	public void visitAgainstTest(){
		Date last = pv.getLastVisite();
		Date d =new Date();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			fail();
		}
		assertTrue(d.after(last));
		pv.visiteAgainst();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			fail();
		}
		assertTrue(d.before(pv.getLastVisite()));
	}
	
	@Test 
	public void persistanceTest(){
		em.getTransaction().begin();
        em.persist(bibi);
        em.persist(bobo);
        em.persist(toto);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(pv);
		em.getTransaction().commit();
		em.getTransaction().begin();
		ProfileVisite pv2 = em.find(ProfileVisite.class
				, new ProfileVisitePK(bibi.getId(),bobo.getId()));
		em.getTransaction().commit();
		assertEquals(pv, pv2);
	}
	
}
