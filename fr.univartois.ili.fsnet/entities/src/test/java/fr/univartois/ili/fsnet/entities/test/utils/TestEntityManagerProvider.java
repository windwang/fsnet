package fr.univartois.ili.fsnet.entities.test.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestEntityManagerProvider {
	
	private static final TestEntityManagerProvider instance;
	
	static {
		instance = new TestEntityManagerProvider();
	}
	
	public static TestEntityManagerProvider getInstance() {
		return instance;
	}
	
	private EntityManager entityManager;

	private TestEntityManagerProvider() {
		Map<String, String> properties = new HashMap<String, String>();
		writeProperties(properties);
		//Persistence.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("fsnetjpa", properties);
		entityManager = emf.createEntityManager();
	}
	
	private void writeProperties(Map<String, String> map) {
		map.put("eclipselink.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
		map.put("eclipselink.jdbc.url", "jdbc:derby:myDbFSNET;create=true");
		map.put("eclipselink.ddl-generation", "drop-and-create-tables");
		map.put("eclipselink.ddl-generation.output-mode", "database");
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	

}
