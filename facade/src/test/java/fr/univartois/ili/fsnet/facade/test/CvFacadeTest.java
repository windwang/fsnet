package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;


import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CvFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

public class CvFacadeTest {

	private EntityManager em;
	private CvFacade cf;
	private SocialEntityFacade sef;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		cf = new CvFacade(em);
		sef = new SocialEntityFacade(em);
	}

	@Test
	public void getCurriculumTest() {
		em.getTransaction().begin();
				
		Curriculum curriculum = new Curriculum();
	    em.persist(curriculum);	    
	    em.getTransaction().commit();
	    
	    long idCurriculum = curriculum.getId();
		Curriculum cv = em.find(Curriculum.class,idCurriculum);
		assertEquals(cv, cf.getCurriculum(idCurriculum));
	}

	@Test
	public void listAllCvTest() {

		em.getTransaction().begin();
		SocialEntity userFacade = sef.createSocialEntity("tom", "mate",
				"to@ma.te");
		int userId = userFacade.getId();
		
		Curriculum curriculum = new Curriculum();
		curriculum.setUserId(userId);
	    em.persist(curriculum);	    
	    em.getTransaction().commit();
	    	
		
		List<Curriculum> results = cf.listAllCv(userId);
		

		assertEquals(results, cf.listAllCv(userId));
	}

}
