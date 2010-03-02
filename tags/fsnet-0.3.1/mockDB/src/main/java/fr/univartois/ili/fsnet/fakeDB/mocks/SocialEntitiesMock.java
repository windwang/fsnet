package fr.univartois.ili.fsnet.fakeDB.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.fakeDB.MockLocator;
import fr.univartois.ili.fsnet.fakeDB.Mock;

public class SocialEntitiesMock extends Mock {

	private EntityManager em;

	private SocialEntityFacade seFacade;

	private List<SocialEntity> socialEntities;

	private void init() {
		socialEntities = new ArrayList<SocialEntity>();
		seFacade = new SocialEntityFacade(em);
	}

	@Override
	public void create(EntityManager em) {
		this.em = em;
		init();
		for (String identifier : generateIdentifiers(10, "mathieu")) {
			try {
				em.getTransaction().begin();
				SocialEntity se = seFacade.createSocialEntity(identifier,
						identifier, identifier + "@" + identifier + ".com");
				em.getTransaction().commit();
				socialEntities.add(se);
			} catch (Exception e) {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}
		}
	}

	@Override
	public void link(EntityManager em, MockLocator locator) {

	}

	public List<SocialEntity> getSocialEntities() {
		return socialEntities;
	}

}
