package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ILIFSNetFacade;

public class ILIFSNetFacadeTest {
	private EntityManager em;
	private ILIFSNetFacade ilifsnet;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		ilifsnet = new ILIFSNetFacade(em);
	}

	@Test
	public void testCreate() {
		em.getTransaction().begin();

		
		SocialEntity socialEntityILIFSNet = ilifsnet.createSocialEntity("ili",
				"fsnet", "ilifsnet@gmail.com");
		em.getTransaction().commit();

		assertEquals(SocialEntity.class, socialEntityILIFSNet.getClass());
	}

}
