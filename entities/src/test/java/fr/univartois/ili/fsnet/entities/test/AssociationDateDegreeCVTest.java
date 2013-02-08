package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
<<<<<<< HEAD

import java.sql.Date;

import org.junit.After;
=======
import static org.junit.Assert.assertNull;

import java.sql.Date;

>>>>>>> fbd17277bfcf3e45264216640aa488a3a163ca56
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.DegreeCV;

public class AssociationDateDegreeCVTest {

<<<<<<< HEAD
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
=======
	private Date endDate;
	private Date startDate;
	private DegreeCV idDegree;
	private Curriculum idCurriculum;
	private int id;

	@Before
	public void setUp() {
		idCurriculum = new Curriculum();
		idDegree = new DegreeCV();
		java.util.Date d = new java.util.Date();
		endDate = new Date(d.getTime());
		startDate = new Date(d.getTime());
		id=1;
	}

	@Test
	public void testCreateEmptyAssociationDateDegreeCV() {
		AssociationDateDegreeCV add = new AssociationDateDegreeCV();
		assertNull(add.getStartDate());
		assertNull(add.getEndDate());
		assertNull(add.getDegree());
		assertNull(add.getCurriculum());
		assertEquals(0,add.getId());
>>>>>>> fbd17277bfcf3e45264216640aa488a3a163ca56
	}

	@Test
	public void testSetByMethodsAndGet() {
<<<<<<< HEAD
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
=======
		AssociationDateDegreeCV add = new AssociationDateDegreeCV();
		add.setEndDate(endDate);
		add.setStartDate(startDate);
		add.setDegree(idDegree);
		add.setCurriculum(idCurriculum);
		add.setId(id);
		assertEquals(endDate, add.getEndDate());
		assertEquals(startDate, add.getStartDate());
		assertEquals(idDegree, add.getDegree());
		assertEquals(idCurriculum, add.getCurriculum());
		assertEquals(id, add.getId());
>>>>>>> fbd17277bfcf3e45264216640aa488a3a163ca56
	}
}
