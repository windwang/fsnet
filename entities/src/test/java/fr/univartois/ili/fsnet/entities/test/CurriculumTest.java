package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.AssociationDateFormationCV;
import fr.univartois.ili.fsnet.entities.AssociationDateTrainingCV;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.HobbiesCV;
import fr.univartois.ili.fsnet.entities.MemberCV;

public class CurriculumTest {
	
	private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() {
	}

    @Test
    public void testPersist() throws ParseException {
    	Curriculum cv = new Curriculum();
    	cv.setTitleCv("test cv");
    	cv.setId(1);
    	cv.setUserId(1);
    	MemberCV member = new MemberCV();
    	cv.setMember(member);
    	ArrayList<AssociationDateFormationCV> formations = new ArrayList<AssociationDateFormationCV>();
    	cv.setMyFormations(formations);
    	ArrayList<HobbiesCV> hobs = new ArrayList<HobbiesCV>();
    	cv.setHobs(hobs);
    	ArrayList<AssociationDateTrainingCV> trains = new ArrayList<AssociationDateTrainingCV>();
    	cv.setTrains(trains);
    	ArrayList<AssociationDateDegreeCV> degs = new ArrayList<AssociationDateDegreeCV>();
    	cv.setDegs(degs);
        em.getTransaction().begin();
        em.persist(cv);
        em.persist(member);
        em.getTransaction().commit();
        Curriculum cv2 = em.find(Curriculum.class, cv.getId());
        assertEquals(cv.getTitleCv(), cv2.getTitleCv());
        assertEquals(cv.getId(), cv2.getId());
        assertEquals(cv.getUserId(), cv2.getUserId());
        assertEquals(cv.getMember(), cv2.getMember());
        assertEquals(cv.getMyFormations(), cv2.getMyFormations());
        assertEquals(cv.getHobs(), cv2.getHobs());
        assertEquals(cv.getTrains(), cv2.getTrains());
        assertEquals(cv.getDegs(), cv2.getDegs());
    }
    
    @Test
	public void testCreateEmptyCV() {
    	Curriculum cv = new Curriculum();
		assertNull(cv.getTitleCv());
		assertNull(cv.getMember());
		assertEquals(cv.getId(), 0);
		assertEquals(cv.getUserId(), 0);
	}
    
}
