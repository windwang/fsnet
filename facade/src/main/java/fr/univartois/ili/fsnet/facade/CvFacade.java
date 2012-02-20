package fr.univartois.ili.fsnet.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.Meeting;

public class CvFacade {
	
	 private final EntityManager em;
	
	 public CvFacade(EntityManager em) {
	        this.em = em;
	    }
	 
	/**
	 * List all the resumes
	 * @return List<Meeting> the list containing all the resumes
	 */
	public final List<Curriculum> listAllCv() {
		List<Curriculum> results;
		results = em
		.createQuery(
				"SELECT c FROM Curriculum c",
				Curriculum.class).getResultList();
		return results;
	}

}
