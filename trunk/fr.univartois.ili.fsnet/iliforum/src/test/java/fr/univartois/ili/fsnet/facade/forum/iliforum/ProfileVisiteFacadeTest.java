package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ProfileVisiteFacadeTest {

	private EntityManager em ;
	
	private SocialEntityFacade sef;
	private ProfileVisiteFacade pvf;
	
	@Before 
	public void setUp(){     
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
		em = entityManagerFactory.createEntityManager();
		sef = new SocialEntityFacade(em);
		pvf = new ProfileVisiteFacade(em);
	}
	
	@Test
	public void visiteTest(){
		em.getTransaction().begin();
		SocialEntity totoTiti = sef.createSocialEntity("toto", "titi","toto@titi.com");
		em.getTransaction().commit();
		em.getTransaction().begin();
		SocialEntity totoTutu = sef.createSocialEntity("toto", "tutu","toto@tutu.com");
		em.getTransaction().commit();
		em.getTransaction().begin();
		pvf.visite(totoTiti, totoTutu);
		em.getTransaction().commit();
		em.getTransaction().begin();
		pvf.visite(totoTiti, totoTutu);
		em.getTransaction().commit();
	}
	
}
