package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.HubFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

public class HubFacadeTest {

	private static EntityManager em;
	private static HubFacade hf;
	private static CommunityFacade cf;
	private static SocialEntityFacade sef;
	private static Community com;
	private static SocialEntity creator;
	private static InteractionFacade interactionFacade;

	@BeforeClass
	public static void setUp() {
		EntityManagerFactory entityManagerFactory = Persistence
				.createEntityManagerFactory("TestPU");
		em = entityManagerFactory.createEntityManager();
		hf = new HubFacade(em);
		sef = new SocialEntityFacade(em);
		cf = new CommunityFacade(em);
		creator = sef.createSocialEntity("creator", "man", "hubman@gmail.com");
		com = cf.createCommunity(creator, "community1");
		interactionFacade = new InteractionFacade(em);
	}

	@Test
	public void createAndGetHubTest() {

		em.getTransaction().begin();
		Hub createdHub = hf.createHub(com, creator, "testhub");
		em.getTransaction().commit();
		
		Hub resultGet = hf.getHub(createdHub.getId());
		
		assertEquals(createdHub, resultGet);
	}

	@Test
	public void searchHubTest() {
		em.getTransaction().begin();
		hf.createHub(com, creator, "javahub1");
		hf.createHub(com, creator, "c#hub1");
		em.getTransaction().commit();
		List<Hub> resultSearch = hf.searchHub("java");
		assertEquals(1, resultSearch.size());
	}

	@Test
	public void deleteHubTest1() {
		em.getTransaction().begin();
		Hub deletedHub = hf.createHub(com, creator, "XyvhYuj");
		em.getTransaction().commit();
		
		List<Hub> resultSearch = hf.searchHub("XyvhYuj");
		assertEquals(1, resultSearch.size());
		
		em.getTransaction().begin();
		interactionFacade.deleteInteraction(creator, deletedHub);
		em.getTransaction().commit();
		
		resultSearch = hf.searchHub("XyvhYuj");
		assertEquals(0, resultSearch.size());
	}
	
	@Test(expected=UnauthorizedOperationException.class)
	public void deleteHubTest2() {
		em.getTransaction().begin();
		SocialEntity socialEntity = new SocialEntity("efefefe", "dede", "mefefe@email.com");
		Hub deletedHub = hf.createHub(com, creator, "deletedHub");
		interactionFacade.deleteInteraction(socialEntity, deletedHub);
		em.getTransaction().commit();
	}
}
