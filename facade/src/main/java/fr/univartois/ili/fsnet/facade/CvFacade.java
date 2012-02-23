package fr.univartois.ili.fsnet.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.Meeting;


/**
 * 
 * @author Ayoub AICH
 *
 */


public class CvFacade {
	
	 private final EntityManager em;
	
	 public CvFacade(EntityManager em) {
	        this.em = em;
	    }
	 
	/**
	 * List all the resumes
	 * @return List<Meeting> the list containing all the resumes
	 */
	public final List<Meeting> listAllCv() {
		List<Meeting> results;
		results = em
		.createQuery(
				"SELECT e FROM Meeting e",
				Meeting.class).getResultList();
		return results;
	}
	
	/**
    *
    * @param meetingId
    * @return
    */
   public final Meeting getMeeting(int meetingId) {
       return em.find(Meeting.class, meetingId);
   }

}
