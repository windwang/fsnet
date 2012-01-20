package fr.univartois.ili.fsnet.fakeDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

public class DBUtils {
	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	public static final void cleanDB() {
		EntityManager em = factory.createEntityManager();
		for (EntityType<?> et : factory.getMetamodel().getEntities()) {
			em.getTransaction().begin();
			Query q = em.createQuery("DELETE FROM " + et.getName());
			q.executeUpdate();
			em.getTransaction().commit();
		}
		em.close();
	}
	
	public static final void populateDB() {
		EntityManager em = factory.createEntityManager();
		Instancier instancier = new Instancier(em);
		instancier.start();
		
		em.close();
	}
	
	public static final void resetDB() {
		cleanDB();
		populateDB();
	}

		
	
}
