package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.ProfileVisitePK;
import fr.univartois.ili.fsnet.entities.SocialEntity;
/**
 * 
 * @author geoffrey boulay
 *
 *
 * Facade for Profiles visites
 */
public class ProfileVisiteFacade {

	private EntityManager em;
	
	/**
	 * create  a ProfileVisiteFacade
	 * @param em used entity manager 
	 */
	public ProfileVisiteFacade(EntityManager em) {
		this.em=em;
	}
	
	/**
	 * Notify that an Social Entity visit the profile on an other Social Entity
	 * @param visitor the visitor of the profile
	 * @param visited the social entity whom profile is visited
	 */
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
	
	/**
	 * get a list of social entity whose visit a same profile
	 * @param visited entite sociale dont le profil a été visité
	 * @return the list of social entity who visit the profile
	 */
	public List<SocialEntity> getLastVisitor(SocialEntity visited){
		//TODO 
		 TypedQuery<SocialEntity> query = em.createNamedQuery(
				"SELECT pv.visitor " +
				"FROM ProfileVisite pv " +
				"WHERE socialEntity = pv.visited "
				,SocialEntity.class).setParameter("socialEntity", visited);
		
		return query.getResultList();		
	}
	
}
