package fr.univartois.ili.fsnet.fakeDB;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.fakeDB.mocks.SocialEntitiesMock;

public class Instancier implements MockLocator {
	private static final Logger logger = Logger.getAnonymousLogger();

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
			logger.log(Level.SEVERE, "", e);
		} catch (InstantiationException e) {
			logger.log(Level.SEVERE, "", e);
		} catch (IllegalAccessException e) {
			logger.log(Level.SEVERE, "", e);
		} finally {
			em.close();
		}
	}

	public void start() {
		loadMock(SocialEntitiesMock.class);
		for (Mock mock: mocks.values()) {
			mock.link(em, this);
		}
	}

	@Override
	public Mock locateMock(Class<? extends Mock> clazz) {
		return mocks.get(clazz);
	}

}
