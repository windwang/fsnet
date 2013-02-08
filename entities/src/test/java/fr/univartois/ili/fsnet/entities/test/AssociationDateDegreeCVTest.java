package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.DegreeCV;

public class AssociationDateDegreeCVTest {

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
	}

	@Test
	public void testSetByMethodsAndGet() {
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
	}
}
