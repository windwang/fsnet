package fr.univartois.ili.fsnet.fakeDB.mocks;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.fakeDB.Mock;
import fr.univartois.ili.fsnet.fakeDB.MockLocator;

public class CommunityMock extends Mock {
	
	private List<Community> communities = new ArrayList<Community>();

	@Override
	public void create(EntityManager em) {}

	@Override
	public void link(EntityManager em, MockLocator locator) {
		SocialEntitiesMock sm = (SocialEntitiesMock) locator.locateMock(SocialEntitiesMock.class);
		CommunityFacade cf = new CommunityFacade(em);
		String basename = "community";
		em.getTransaction().begin();
		for (int i = 0 ; i < 2 ; i++) {
			Community community = cf.createCommunity(sm.getSocialEntities().get(0), basename + i);
			communities.add(community);
		}		
		em.getTransaction().commit();
	}

	public List<Community> getCommunities() {
		return communities;
	}

}
