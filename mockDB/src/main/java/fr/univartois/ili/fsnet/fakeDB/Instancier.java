package fr.univartois.ili.fsnet.fakeDB;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.fakeDB.mocks.CommunityMock;
import fr.univartois.ili.fsnet.fakeDB.mocks.HubMock;
import fr.univartois.ili.fsnet.fakeDB.mocks.InterestsMock;
import fr.univartois.ili.fsnet.fakeDB.mocks.SocialEntitiesMock;

public class Instancier implements MockLocator {
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	private Map<Class<? extends Mock>, Mock> mocks = new HashMap<Class<? extends Mock>, Mock>();
	
	private EntityManager em;

	public Instancier(EntityManager em) {
		this.em = em;
	}
	
	private void loadMock(Class<? extends Mock> clazz) {
		try {
			Mock mock;
			mock = clazz.newInstance();
			mock.create(em);
			mocks.put(mock.getClass(), mock);
		} catch (SecurityException e) {
			LOGGER.log(Level.SEVERE, "", e);
			em.close();
		} catch (InstantiationException e) {
			LOGGER.log(Level.SEVERE, "", e);
			em.close();
		} catch (IllegalAccessException e) {
			LOGGER.log(Level.SEVERE, "", e);
			em.close();
		}
	}

	public void start() {
		loadMock(SocialEntitiesMock.class);
		loadMock(InterestsMock.class);
		loadMock(CommunityMock.class);
		loadMock(HubMock.class);
		locateMock(SocialEntitiesMock.class).link(em, this);
		locateMock(InterestsMock.class).link(em, this);
		locateMock(CommunityMock.class).link(em, this);
		locateMock(HubMock.class).link(em, this);
		
	}

	@Override
	public Mock locateMock(Class<? extends Mock> clazz) {
		return mocks.get(clazz);
	}

}
