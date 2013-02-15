package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;
import fr.univartois.ili.fsnet.entities.ConsultationVote;

public class ConsultationChoiceVoteTest {

	private static int ID;
	private static ConsultationVote VOTE;
	private static ConsultationChoice CHOICE;
	private static boolean IF_NECESSARY;
	private static int PREFERENCE_ORDER;

	@After
	public void tearDown() {
	}

	@Before
	public void setUp() {
		ID = 1234;
		VOTE = new ConsultationVote();
		CHOICE = new ConsultationChoice();
		IF_NECESSARY = true;
		PREFERENCE_ORDER = 7;
	}

	@Test
	public void testCreateEmptyConsultationChoiceVote() {
		ConsultationChoiceVote cons = new ConsultationChoiceVote();
		assertEquals(0, cons.getId());
		assertNull(cons.getChoice());
		assertNull(cons.getVote());
		assertFalse(cons.isIfNecessary());
		assertEquals(0, cons.getPreferenceOrder());
	}

	@Test
	public void testSetByMethodsAndGet() {
		ConsultationChoiceVote cons = new ConsultationChoiceVote();
		cons.setId(ID);
		cons.setChoice(CHOICE);
		cons.setVote(VOTE);
		cons.setIfNecessary(IF_NECESSARY);
		cons.setPreferenceOrder(PREFERENCE_ORDER);
		assertEquals(ID, cons.getId());
		assertEquals(CHOICE, cons.getChoice());
		assertEquals(VOTE, cons.getVote());
		assertEquals(IF_NECESSARY, cons.isIfNecessary());
		assertEquals(PREFERENCE_ORDER, cons.getPreferenceOrder());
	}

	@Test
	public void testConstructor() {
		ConsultationChoiceVote cons = new ConsultationChoiceVote(VOTE,CHOICE);
		assertEquals(0, cons.getId());
		assertEquals(0, cons.getPreferenceOrder());
		cons.setChoice(CHOICE);
		cons.setVote(VOTE);
		assertFalse(cons.isIfNecessary());
	}
}
