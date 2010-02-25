package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class HubFacadeTest {

	private static EntityManager em;
	private static HubFacade hf;
	private static CommunityFacade cf;
	private static SocialEntityFacade sef;
	private static Community com;
	private static SocialEntity creator;
	private static InteractionFacade interactionFcade;

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
		interactionFcade = new InteractionFacade(em);
	}

	@Test
	public void createAndGetHubTest() {

		Hub createdHub = hf.createHub(com, creator, "testhub");
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
	public void deleteHubTest() {
		em.getTransaction().begin();
		Hub deletedHub = hf.createHub(com, creator, "deletedHub");
		interactionFcade.deleteInteraction(creator, deletedHub);
		em.getTransaction().commit();
		List<Hub> resultSearch = hf.searchHub("delet");
		assertEquals(0, resultSearch.size());
	}
}
