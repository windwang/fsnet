package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;

public class ConsultationChoiceTest {

	private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
	}
	
	@Test
	public void testConstructor1() {
		ConsultationChoice cons = new ConsultationChoice();
		
		assertNull(cons.getConsultation());
		assertNull(cons.getIntituled());
		assertNull(cons.getConsultationChoiceVotes());
		assertEquals(cons.getId(),0);
		assertEquals(0, cons.getMaxVoters());
	}
	
	@Test
	public void testConstructor2() {
		Consultation c = new Consultation();
		ConsultationChoice cons = new ConsultationChoice(c,"choix");
		assertEquals(cons.getConsultation(),c);
		assertEquals(cons.getIntituled(),"choix");
		assertNull(cons.getConsultationChoiceVotes());
		assertEquals(cons.getId(),0);
		assertEquals(0, cons.getMaxVoters());
	}
	
	@Test
	public void testSetByMethodsAndGet(){
		ConsultationChoice cons = new ConsultationChoice();
		Consultation c = new Consultation();
		List <ConsultationChoiceVote> l = new ArrayList<>();
		l.add(new ConsultationChoiceVote());
		cons.setId(2);
		cons.setConsultation(c);
		cons.setIntituled("consultation");
		cons.setMaxVoters(15);
		cons.setConsultationChoiceVotes(l);		
		assertEquals(cons.getId(), 2);
		assertEquals(cons.getConsultation(),c);
		assertEquals(cons.getIntituled(),"consultation");
		assertEquals(cons.getMaxVoters(),15);
		assertEquals(cons.getConsultationChoiceVotes(),l);
	}
	


}
