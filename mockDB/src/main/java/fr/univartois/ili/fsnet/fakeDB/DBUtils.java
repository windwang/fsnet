package fr.univartois.ili.fsnet.fakeDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

public final class DBUtils {
	private static final EntityManagerFactory FACTORY = Persistence
			.createEntityManagerFactory("fsnetjpa");

	/*
	 * There has to be a constructor, even if it is a private one
	 */
	private DBUtils(){
		
	}
	
	public static final void cleanDB() {
		EntityManager em = FACTORY.createEntityManager();
		for (EntityType<?> et : FACTORY.getMetamodel().getEntities()) {
			em.getTransaction().begin();
			Query q = em.createQuery("DELETE FROM " + et.getName());
			q.executeUpdate();
			em.getTransaction().commit();
		}
		em.close();
	}
	
	public static final void populateDB() {
		EntityManager em = FACTORY.createEntityManager();
		Instancier instancier = new Instancier(em);
		instancier.start();
		
		em.close();
	}
	
	public static final void resetDB() {
		cleanDB();
		populateDB();
	}

		
	
}
