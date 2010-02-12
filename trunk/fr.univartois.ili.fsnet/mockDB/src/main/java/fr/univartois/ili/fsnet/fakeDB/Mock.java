package fr.univartois.ili.fsnet.fakeDB;

import javax.persistence.EntityManager;

public abstract class Mock {

	public abstract void populate(EntityManager em);
	
}
