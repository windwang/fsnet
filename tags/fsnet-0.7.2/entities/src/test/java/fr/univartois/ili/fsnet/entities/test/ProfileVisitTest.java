package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.*;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


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
