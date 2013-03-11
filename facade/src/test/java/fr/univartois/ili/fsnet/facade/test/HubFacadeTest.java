package fr.univartois.ili.fsnet.facade.test;

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

		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		Hub createdHub = hf.createHub(com, creator, "testhub");
		em.getTransaction().commit();
		
		Hub resultGet = hf.getHub(createdHub.getId());
		
		assertEquals(createdHub, resultGet);
	}

	@Test
	public void searchHubTest() {
		em.getTransaction().begin();
		createHubs(0);
		em.getTransaction().commit();
		List<Hub> resultSearch = hf.searchHub("java");
		assertEquals(1, resultSearch.size());
	}
	
	
	@Test
	public void testSearchHubByName(){
		em.getTransaction().begin();
		createHubs(2);
		Hub hub = hf.createHub(com, creator, "groovyhub1");
		em.getTransaction().commit();
		Hub result = hf.getHubByName("groovyhub1", com);
		assertEquals(hub, result);
	}
	
	@Test
	public void testSearchHub(){
		em.getTransaction().begin();
		createHubs(3);
		hf.createHub(com, creator, "scalahub1");
		em.getTransaction().commit();
		List<Hub> resultSearch = hf.searchHub("scala", com);
		assertEquals(1, resultSearch.size());
	}
	
	@Test
	public void testGetHubById(){
		em.getTransaction().begin();
		createHubs(4);
		Hub hub = hf.createHub(com, creator, "groovyhub1");
		em.getTransaction().commit();
		Hub result = hf.getHubById(hub.getId(), com);
		assertEquals(hub, result);
	
	}
	
	@Test
	public void testModifyName(){
		Hub hub = hf.createHub(com, creator, "groovyhub6");
		hf.modifyName("groovyhub7", hub);
		assertEquals("groovyhub7",hub.getTitle());
	}
	

	private void createHubs(int num) {
		hf.createHub(com, creator, "javahub"+num);
		hf.createHub(com, creator, "c#hub"+num);
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
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateException(){
		hf.createHub(com, null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateException2(){
		hf.createHub(com, creator, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchExcpetion(){
		hf.searchHub(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetHubByNameException(){
		hf.getHubByName(null,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetHubByNameException2(){
		hf.getHubByName("nameHub",null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetHubByIdException(){
		hf.getHubById(null,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetHubByIdException2(){
		hf.getHubById(1,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchHubException(){
		hf.searchHub(null, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSearchHubException2(){
		hf.searchHub("pattern", null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testModifyNameNullException(){
		hf.modifyName(null, null);
	}
}
