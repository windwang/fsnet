package fr.univartois.ili.fsnet.fakeDB.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.fakeDB.Mock;
import fr.univartois.ili.fsnet.fakeDB.MockLocator;

public class InterestsMock extends Mock {

	private InterestFacade facade;

	private List<Interest> interests = new ArrayList<Interest>();

	@Override
	public void create(EntityManager em) {
		facade = new InterestFacade(em);
		for (String identifier : generateIdentifiers(10, "interest")) {
			try {
				em.getTransaction().begin();
				Interest interest;
				interest = facade.createInterest(identifier);
				interests.add(interest); 
				em.getTransaction().commit();
			} catch (RollbackException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "", e);
			}
		}
	}

	@Override
	public void link(EntityManager em, MockLocator locator) {

	}

}
