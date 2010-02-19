package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class InteractionFacadeTest {
	
	private EntityManager em;
	private InteractionFacade interactionFacade;
	private SocialEntityFacade sef;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		interactionFacade = new InteractionFacade(em);
		sef= new SocialEntityFacade(em);
	}
	
	@Test
	public void deleteInteraction(){
		SocialEntity entity=sef.createSocialEntity("entity1", "entity1", "entity1@mail.com");
		CommunityFacade cf= new CommunityFacade(em);
		Community community=cf.createCommunity(entity, "com1");
		SocialEntity entity2=sef.createSocialEntity("entity2", "entity2", "entity2mail.com");
		assertFalse(interactionFacade.deleteInteraction(entity2, community));
		assertTrue(interactionFacade.deleteInteraction(entity, community));
	}

}
