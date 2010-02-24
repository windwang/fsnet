package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.ProfileVisitePK;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ProfileVisiteFacade {

	private EntityManager em;

	public ProfileVisiteFacade(EntityManager em) {
		this.em=em;
	}
	
	public void visite(SocialEntity visitor,SocialEntity visited){
		ProfileVisite pv = em.find(ProfileVisite.class, new ProfileVisitePK(visitor.getId(),visited.getId()));
		if(pv==null){
			pv = new ProfileVisite(visited,visitor);
			em.persist(pv);
		}
		else{
			pv.visiteAgainst();
			em.merge(pv);
		}
	}
	
}
