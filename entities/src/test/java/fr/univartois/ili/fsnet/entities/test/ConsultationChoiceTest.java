package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.entities.Consultation.TypeConsultation;

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
		assertEquals(0, cons.getMaxVoters());
	}
	


}
