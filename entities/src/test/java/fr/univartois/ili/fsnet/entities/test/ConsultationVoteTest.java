package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;
import fr.univartois.ili.fsnet.entities.ConsultationVote;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ConsultationVoteTest {

	private static int ID;
	private static Consultation CONSULTATION;
	private static SocialEntity VOTER;
	private static String COMMENT;
	private static String OTHER;
	private static List<ConsultationChoiceVote> CHOICES;

	@After
	public void tearDown() {
	}

	@Before
	public void setUp() {
		ID = 1234;
		CONSULTATION = new Consultation();
		VOTER = new SocialEntity();
		COMMENT = "commentaire";
		OTHER = "other";
		CHOICES = new ArrayList<ConsultationChoiceVote>();
	}

	@Test
	public void testCreateEmptyConsultationVote() {
		ConsultationVote cons = new ConsultationVote();
		assertEquals(0, cons.getId());
		assertNull(cons.getConsultation());
		assertNull(cons.getVoter());
		assertNull(cons.getComment());
		assertNull(cons.getOther());
		assertNull(cons.getChoices());
	}

	@Test
	public void testSetByMethodsAndGet() {
		ConsultationVote cons = new ConsultationVote();
		cons.setId(ID);
		cons.setConsultation(CONSULTATION);
		cons.setVoter(VOTER);
		cons.setComment(COMMENT);
		cons.setOther(OTHER);
		cons.setChoices(CHOICES);
		assertEquals(ID, cons.getId());
		assertEquals(CONSULTATION, cons.getConsultation());
		assertEquals(VOTER, cons.getVoter());
		assertEquals(COMMENT, cons.getComment());
		assertEquals(OTHER, cons.getOther());
		assertEquals(CHOICES, cons.getChoices());
	}
	
	@Test
	public void testConstructor() {
		ConsultationVote cons = new ConsultationVote(VOTER,COMMENT,OTHER);
		assertEquals(0, cons.getId());
		assertNull(cons.getConsultation());
		assertEquals(VOTER, cons.getVoter());
		assertEquals(COMMENT, cons.getComment());
		assertEquals(OTHER, cons.getOther());
		assertEquals(0, cons.getChoices().size());
	}
}
