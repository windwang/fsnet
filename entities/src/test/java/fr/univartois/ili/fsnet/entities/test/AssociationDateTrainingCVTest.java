package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.TrainingCV;


public class AssociationDateTrainingCVTest {
	
	@Test
	 public void testSetAndGetByMethodId() {
	    	AssociationDateTrainingCV adtcv = new AssociationDateTrainingCV();
	    	adtcv.setId(11);
	    	assertEquals(11, adtcv.getId());
	    }
	
	@Test
	 public void testSetAndGetByMethodStartDate() {
	    	AssociationDateTrainingCV adtcv = new AssociationDateTrainingCV();	    
	    	Date startDate = new Date(0);
	    	
			adtcv.setStartDate(startDate );
	    	assertEquals(startDate, adtcv.getStartDate());
	    }
	
	@Test
	 public void testSetAndGetByMethodEndDate() {
	    	AssociationDateTrainingCV adtcv = new AssociationDateTrainingCV();	    
	    	Date endDate = new Date(0);
	    	
			adtcv.setEndDate(endDate);
	    	assertEquals(endDate, adtcv.getEndDate());
	    }
	
	@Test
	 public void testSetAndGetByMethodTraining() {
	    	AssociationDateTrainingCV adtcv = new AssociationDateTrainingCV();	    
	    	TrainingCV idTraining = new TrainingCV();
	    	
			adtcv.setTraining(idTraining);
			assertEquals(idTraining, adtcv.getIdTraining());
	    }
	
	@Test
	 public void testSetAndGetByMethodCurriculum() {
	    	AssociationDateTrainingCV adtcv = new AssociationDateTrainingCV();   
			Curriculum curriculum = new Curriculum();
			
			adtcv.setCurriculum(curriculum);
			assertEquals(curriculum, adtcv.getCurriculum());
	    }	
}
