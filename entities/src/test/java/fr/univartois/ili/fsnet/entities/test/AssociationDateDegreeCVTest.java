package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.DegreeCV;

public class AssociationDateDegreeCVTest {

	private static long ID = 1234;
	private static Curriculum CURRICULUM = new Curriculum();
	private static DegreeCV DEGREE_CV = new DegreeCV();
	private static Date START_DATE = new Date(1111);
	private static Date END_DATE = new Date(2222);

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
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
