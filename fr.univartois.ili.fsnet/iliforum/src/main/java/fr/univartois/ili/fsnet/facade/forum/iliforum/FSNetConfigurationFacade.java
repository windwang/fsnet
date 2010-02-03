package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;

public class FSNetConfigurationFacade {
	
	private EntityManager em;
	
	public FSNetConfigurationFacade(EntityManager em) {
		this.em = em;
	}

	
	private String getSMTPPort() {
		return null;
	}
	
}
