package fr.univartois.ili.fsnet.fakeDB.mocks;

import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.HubFacade;
import fr.univartois.ili.fsnet.fakeDB.Mock;
import fr.univartois.ili.fsnet.fakeDB.MockLocator;

public class HubMock extends Mock {
	
	

	@Override
	public void create(EntityManager em) {}

	@Override
	public void link(EntityManager em, MockLocator locator) {
		HubFacade hf = new HubFacade(em);
		SocialEntitiesMock sm = (SocialEntitiesMock) locator.locateMock(SocialEntitiesMock.class);
		SocialEntity owner = sm.getSocialEntities().get(0);
		CommunityMock cm = (CommunityMock) locator.locateMock(CommunityMock.class);
		List<Community> communities = cm.getCommunities();
		String baseName = "hub";
		
		for (Community c : communities) {	
			for (int i = 0 ; i < 5 ; i++) {
				em.getTransaction().begin();
				Hub h = hf.createHub(c, owner, baseName + i);
				c.getHubs().add(h);
				em.getTransaction().commit();
			}
		}
	}
	
	

}
