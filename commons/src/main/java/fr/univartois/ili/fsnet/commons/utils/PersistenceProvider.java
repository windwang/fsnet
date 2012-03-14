package fr.univartois.ili.fsnet.commons.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.metamodel.Metamodel;

import org.eclipse.persistence.config.PersistenceUnitProperties;

/**
 * @author FSNet
 * 
 */
public final class PersistenceProvider {

	private static Map<String, Object> lOptions = new HashMap<String, Object>();
	static {
		lOptions.put(PersistenceUnitProperties.CACHE_SHARED_DEFAULT, "false");
	}

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa", lOptions);

	
	private PersistenceProvider(){
		
	}
	/**
	 * @return
	 */
	public static EntityManager createEntityManager() {
		return factory.createEntityManager();
	}

	/**
	 * @return
	 */
	public static Metamodel getMetamodel() {
		return factory.getMetamodel();
	}

}
