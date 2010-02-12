package fr.univartois.ili.fsnet.fakeDB.mocks;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;
import fr.univartois.ili.fsnet.fakeDB.Mock;

public class MathieuBonifaceMock extends Mock {

	private EntityManager em;
	
	private SocialEntityFacade seFacade;
	
	private void init() {
		seFacade = new SocialEntityFacade(em);
	}
	
	private String getNextIdentifier(int i) {
		return "mathieu" + i;
	}
	
	@Override
	public void populate(EntityManager em) {
		this.em = em;
		init();
		String identifier;
		em.getTransaction().begin();
		try {
		for (int i = 0 ; i < 10; i++) {
			identifier = getNextIdentifier(i);
			seFacade.createSocialEntity(identifier, identifier, identifier + "@" + identifier + ".com");
		}
		em.getTransaction().commit();
		} catch (Exception e) {	
			Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
		}
	}

}
