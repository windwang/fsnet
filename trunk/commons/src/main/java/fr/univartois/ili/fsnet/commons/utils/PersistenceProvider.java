package fr.univartois.ili.fsnet.commons.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceProvider {

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");
	
	public static EntityManager createEntityManager() {
		return factory.createEntityManager();
	}

}
