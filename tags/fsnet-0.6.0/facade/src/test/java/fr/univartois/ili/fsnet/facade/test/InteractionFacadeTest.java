package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

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
	public void deleteInteraction1(){
		SocialEntity entity=sef.createSocialEntity("entity1", "entity1", "entity1@mail.com");
		CommunityFacade cf= new CommunityFacade(em);
		Community community=cf.createCommunity(entity, "com1");
		interactionFacade.deleteInteraction(entity, community);
		assertNull(em.find(Interaction.class, community.getId()));
	}

	@Test(expected=UnauthorizedOperationException.class)
	public void deleteInteraction2(){
		SocialEntity entity=sef.createSocialEntity("entity1", "entity1", "entity10@mail.com");
		CommunityFacade cf= new CommunityFacade(em);
		Community community=cf.createCommunity(entity, "com2");
		SocialEntity entity2=sef.createSocialEntity("entity2", "entity2", "entity20mail.com");
		interactionFacade.deleteInteraction(entity2, community);
	}

}
