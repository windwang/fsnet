package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

public class AnnouncementFacadeTest {

	private EntityManager em;
	private AnnouncementFacade af;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		af = new AnnouncementFacade(em);
	}
	
	@Test
	public void testCreate() {
		
	}
}
