package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class CommunityTest {

	private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() {
	}

	// TODO TEST-LOL galileo
	@Test
	public void testPersist() {
		final SocialEntity socialEntity = new SocialEntity("test5", "test5",
				"test5@test.com");
		Community communaute = new Community(socialEntity, "Ma communaute");
		em.getTransaction().begin();
		em.persist(socialEntity);
		em.persist(communaute);
		em.getTransaction().commit();
		int monId = communaute.getId();
		assertNotNull("id not null", monId);
	}

	@Test
	public void testSetAndGetByMethodHubs() {
		final SocialEntity socialEntity = new SocialEntity("tomtom", "nana",
				"tom@te.com");
		Community communaute = new Community(socialEntity, "Ma communaute");
		
		
		Set<Hub> hubs = new HashSet<Hub>();	
		Hub e = new Hub(communaute, socialEntity, "My hub");
		hubs.add(e);
		
		communaute.setHubs(hubs);
		assertEquals(hubs,communaute.getHubs());
	}
	
	@Test
	public void testSetAndGetByMethodChildrenCommunities() {
		final SocialEntity socialEntity = new SocialEntity("tomtom", "nana",
				"tom@te.com");
		Community communaute = new Community(socialEntity, "Ma communaute");
		
		Set<Community> childrenCommunities = new HashSet<Community>();
		childrenCommunities.add(communaute);
		
		communaute.setChildrenCommunities(childrenCommunities);
		assertEquals(childrenCommunities,communaute.getChildrenCommunities());
	}
	
	@Test
	public void testSetAndGetByMethodCommunity() {
		final SocialEntity socialEntity = new SocialEntity("tomtom", "nana",
				"tom@te.com");
		Community communaute = new Community(socialEntity, "Ma communaute");		
		Community parentCommunity = new Community(socialEntity, "Je suis ton père");
		
		communaute.setParentCommunity(parentCommunity );
		assertEquals(parentCommunity,communaute.getParentCommunity());
	}
}
