package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.DegreeCV;

public class AssociationDateDegreeCVTest {

	private static long ID ;
	private static Curriculum CURRICULUM ;
	private static DegreeCV DEGREE_CV ;
	private static Date START_DATE ;
	private static Date END_DATE ;
	
	
	@After
	public void tearDown() {
	
	}
	@Before
	public void setUp() {
		CURRICULUM = new Curriculum();
		DEGREE_CV = new DegreeCV();
		END_DATE = new Date(12345);
		START_DATE = new Date(23456);
		ID=1234;
	}

	@Test
	public void testCreateEmptyAssociationDateDegreeCV() {
		AssociationDateDegreeCV add = new AssociationDateDegreeCV();
		assertNull(add.getStartDate());
		assertNull(add.getEndDate());
		assertNull(add.getDegree());
		assertNull(add.getCurriculum());
		assertEquals(0,add.getId());
	}

	@Test
	public void testSetByMethodsAndGet() {
		AssociationDateDegreeCV addCV = new AssociationDateDegreeCV();
		addCV.setId(ID);
		addCV.setCurriculum(CURRICULUM);
		addCV.setDegree(DEGREE_CV);
		addCV.setStartDate(START_DATE);
		addCV.setEndDate(END_DATE);
		assertEquals(ID, addCV.getId());
		assertEquals(CURRICULUM, addCV.getCurriculum());
		assertEquals(DEGREE_CV, addCV.getDegree());
		assertEquals(START_DATE, addCV.getStartDate());
		assertEquals(END_DATE, addCV.getEndDate());

	}
}
