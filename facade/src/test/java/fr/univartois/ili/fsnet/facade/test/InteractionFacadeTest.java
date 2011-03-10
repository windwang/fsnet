package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
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
	private CommunityFacade cf;

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		interactionFacade = new InteractionFacade(em);
		sef= new SocialEntityFacade(em);
		cf = new CommunityFacade(em);
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
	
	@Test
	public void getInteraction() {
		em.getTransaction().begin();
		SocialEntity entity=sef.createSocialEntity("e", "e", "e@e.com");
		Interaction interaction = cf.createCommunity(entity, "An Interaction");
		em.getTransaction().commit();
		int generatedInteractionId = interaction.getId();
		
		em.getTransaction().begin();
		interaction = interactionFacade.getInteraction(interaction.getId());
		em.getTransaction().commit();
		
		assertNotNull(interaction);
		assertEquals(generatedInteractionId, interaction.getId());
	}

	@Test
	public void testGetUnreadAnnouncements() {
		int nbInteraction = (Integer) em.createNativeQuery("SELECT COUNT(*) FROM Interaction").getSingleResult();
		
		em.getTransaction().begin();
		
		SocialEntity user1 = new SocialEntity("name1", "firstName1", "email1@g.com");
		em.persist(user1);
		
		Interaction it1 = new Announcement(user1, "title1", "content1", new Date(), false);
		em.persist(it1);
		
		Interaction it2 = new Announcement(user1, "title2", "content2", new Date(), false);
		em.persist(it2);
		
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		SocialEntity userLoad = sef.getSocialEntity(user1.getId());
		assertEquals(user1.getId(), userLoad.getId());
		
		
		Interaction itL1 = interactionFacade.getInteraction(it1.getId());
		assertEquals(it1.getId(), itL1.getId());
		Interaction itL2 = interactionFacade.getInteraction(it2.getId());
		assertEquals(it2.getId(), itL2.getId());
		em.getTransaction().commit();
		

		em.getTransaction().begin();
		assertEquals(nbInteraction+2, interactionFacade.getUnreadInteractionsForSocialEntity(userLoad).size());
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		userLoad=sef.getSocialEntity(user1.getId());
		itL1 = interactionFacade.getInteraction(it1.getId());
		userLoad.addInteractionRead(itL1);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		assertEquals(nbInteraction+1, interactionFacade.getUnreadInteractionsForSocialEntity(userLoad).size());
		em.getTransaction().commit();
		
	}
	
}
