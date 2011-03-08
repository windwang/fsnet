package fr.univartois.ili.fsnet.commons.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class PersistenceProvider {

	private static Map<String, Object> lOptions = new HashMap<String, Object>();
	static {
		lOptions.put(PersistenceUnitProperties.CACHE_SHARED_DEFAULT, "false");
	}
	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa", lOptions);

	public static EntityManager createEntityManager() {
		return factory.createEntityManager();
	}

}
