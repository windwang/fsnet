package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;
import fr.univartois.ili.fsnet.entities.TrainingCV;

public class TrainingCVTest {

	@Test
	public void testSetByMethodsAndGet() throws ParseException {
		final long id = 12;
		final String name = "toto";
		final String speciality="speciality";
		TrainingCV training= new TrainingCV();
		EstablishmentCV ets= new EstablishmentCV();
		List<AssociationDateTrainingCV> myCVs=new ArrayList<AssociationDateTrainingCV>();
		
		training.setId(id);
		training.setName(name);
		training.setMyEst(ets);
		training.setSpeciality(speciality);
		training.setAssociationDateTrainingCV(myCVs);
		
		assertEquals(training.getId(), id);
		assertEquals(training.getName(), name);
		assertEquals(training.getSpeciality(), speciality);
		assertEquals(training.getMyEst(), ets);
		assertEquals(training.getAssociationDateTrainingCV(), myCVs);

	}
}
