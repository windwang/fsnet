package fr.univartois.ili.fsnet.entities.test;

import java.sql.Date;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import fr.univartois.ili.fsnet.entities.AssociationDateFormationCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.FormationCV;

/**
 * 
 * @author Mouloud Goutal <mouloud.goutal@gmail.com>
 *
 */
public class AssociationDateFormationCVTest {

	
	@Test
	public void testCreateEmptyAssociationDateFormationCV() {
		AssociationDateFormationCV a  = new AssociationDateFormationCV();
		assertEquals(a.getId(),0);
		assertNull(a.getIdCurriculum());
		assertNull(a.getIdFormation());
		assertNull(a.getObtainedDate());		
	}	
	
	@Test
	public void testSetByMethodsAndGet(){
		AssociationDateFormationCV a  = new AssociationDateFormationCV();
		Curriculum c = new Curriculum();
		FormationCV f = new FormationCV();
		Date d = new Date(15000000);
		a.setId(2);
		a.setIdCurriculum(c);
		a.setIdFormation(f);
		a.setObtainedDate(d);
		
		assertEquals(a.getId(), 2);
		assertEquals(a.getIdCurriculum(), c);
		assertEquals(a.getIdFormation(), f);
		assertEquals(a.getObtainedDate(),d);
	}
}
