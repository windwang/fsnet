package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.Consultation.TypeConsultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationVote;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;

/**
 * 
 * @author mickael watrelot - micgamers@gmail.com
 * 
 */
public class ConsultationTest {

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
	public void testSetGetDescription(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setDescription("description");
		assertEquals("description", cons.getDescription());
	}

	@Test
	public void testSetGetlimitChoicesPerParticipantMin(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitChoicesPerParticipantMin(2);
		assertEquals(2, cons.getLimitChoicesPerParticipantMin());
	}
		
	@Test
	public void testSetGetlimitChoicesPerParticipantMax(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitChoicesPerParticipantMax(2);
		assertEquals(2, cons.getLimitChoicesPerParticipantMax());
	}
	
	@Test
	public void testSetGetType(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setType(TypeConsultation.PREFERENCE_ORDER);
		assertEquals(TypeConsultation.PREFERENCE_ORDER, cons.getType());
	}

	@Test
	public void testSetGetIfNecessaryWeight(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setIfNecessaryWeight(0);
		final double DELTA = 1e-15;
		assertEquals(0, cons.getIfNecessaryWeight(), DELTA);
	}
	
	@Test
	public void testSetGetMaxDate() throws ParseException{
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setMaxDate(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/2989"));
		assertEquals(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/2989"), cons.getMaxDate());
	}
	
	@Test
	public void testSetGetMaxVoters(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setMaxVoters(10);
		assertEquals(10, cons.getMaxVoters());
	}
	
	@Test
	public void testSetGetCurrentVoters(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setCurrentVoters(2);
		assertEquals(2, cons.getCurrentVoters());
	}
	
	@Test
	public void testSetGetChoices(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		ConsultationChoice ch1 = new ConsultationChoice();
		ConsultationChoice ch2 = new ConsultationChoice();
		List<ConsultationChoice> choices = new ArrayList<>();
		choices.add(ch1);
		choices.add(ch2);
		cons.setChoices(choices);
		assertEquals(2, cons.getChoices().size());
	}
	
	@Test
	public void testAddChoice(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		ConsultationChoice ch1 = new ConsultationChoice();
		ConsultationChoice ch2 = new ConsultationChoice();
		cons.addChoice(ch1);
		cons.addChoice(ch2);
		assertEquals(2, cons.getChoices().size());
	}

	@Test
	public void testSetGetConsultationVotes(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		ConsultationVote v1 = new ConsultationVote();
		ConsultationVote v2 = new ConsultationVote();
		List<ConsultationVote> votes = new ArrayList<>();
		votes.add(v1);
		votes.add(v2);
		cons.setConsultationVotes(votes);
		assertEquals(2, cons.getConsultationVotes().size());
	}
	
	@Test
	public void testSetLimitParticipantsPerChoice(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitParticipantsPerChoice("T");
		assertEquals("T", cons.getLimitParticipantsPerChoice());
	}

	@Test
	public void testIsLimitParticipantsPerChoice(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitParticipantsPerChoice("T");
		assertTrue(cons.isLimitParticipantsPerChoice());
		cons.setLimitParticipantsPerChoice("F");
		assertFalse(cons.isLimitParticipantsPerChoice());
	}
	
	@Test
	public void testSetLimitParticipantPerChoiceWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitParticipantPerChoice(true);
		assertEquals(cons.getLimitParticipantsPerChoice(), "T");
	}
	
	@Test
	public void testSetLimitParticipantPerChoiceWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitParticipantPerChoice(false);
		assertEquals(cons.getLimitParticipantsPerChoice(), "F");
	}
	
	@Test
	public void testGetSetLimitChoicesPerParticipant(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitChoicesPerParticipant("T");
		assertEquals(cons.getLimitChoicesPerParticipant(), "T");
	}
	
	@Test
	public void testIsLimitChoicesPerParticipant(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitChoicesPerParticipant("T");
		assertTrue(cons.isLimitChoicesPerParticipant());
		cons.setLimitChoicesPerParticipant("F");
		assertFalse(cons.isLimitChoicesPerParticipant());
	}
	
	@Test
	public void testSetLimitChoicesPerParticipantWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitChoicesPerParticipant(true);
		assertEquals(cons.getLimitChoicesPerParticipant(), "T");
	}
	
	@Test
	public void testSetLimitChoicesPerParticipantWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setLimitChoicesPerParticipant(false);
		assertEquals(cons.getLimitChoicesPerParticipant(), "F");
	}
	
	@Test
	public void testGetSetShowBeforeAnswer(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeAnswer("before");
		assertEquals(cons.getShowBeforeAnswer(), "before");
	}
	
	@Test
	public void testIsShowBeforeAnswer(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeAnswer("T");
		assertTrue(cons.isShowBeforeAnswer());
		cons.setShowBeforeAnswer("F");
		assertFalse(cons.isShowBeforeAnswer());
	}
	
	@Test
	public void testSetShowBeforeAnswerWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeAnswer(true);
		assertEquals(cons.getShowBeforeAnswer(), "T");
	}
	
	@Test
	public void testSetShowBeforeAnswerWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeAnswer(false);
		assertEquals(cons.getShowBeforeAnswer(), "F");
	}
	
	@Test
	public void testGetSetAllowAllToModify(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setAllowAllToModify("T");
		assertEquals(cons.getAllowAllToModify(), "T");
	}	
	
	@Test
	public void testIsAllowAllToModify(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setAllowAllToModify("T");
		assertTrue(cons.isAllowAllToModify());
		cons.setAllowAllToModify("F");
		assertFalse(cons.isAllowAllToModify());
	}
	
	@Test
	public void testSetAllowAllToModifyWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setAllowAllToModify(true);
		assertEquals(cons.getAllowAllToModify(), "T");
	}
	
	@Test
	public void testSetAllowAllToModifyWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setAllowAllToModify(false);
		assertEquals(cons.getAllowAllToModify(), "F");
	}
	
	@Test
	public void testGetSetClosingAtDate(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate("T");
		assertEquals(cons.getClosingAtDate(), "T");
	}	
	
	@Test
	public void testIsClosingAtDate(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate("T");
		assertTrue(cons.isClosingAtDate());
		cons.setClosingAtDate("F");
		assertFalse(cons.isClosingAtDate());
	}
	
	@Test
	public void testSetClosingAtDateWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate(true);
		assertEquals(cons.getClosingAtDate(), "T");
	}
	
	@Test
	public void testSetClosingAtDateWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate(false);
		assertEquals(cons.getClosingAtDate(), "F");
	}
	
	@Test
	public void testGetSetShowBeforeClosing(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeClosing("before");
		assertEquals(cons.getShowBeforeClosing(), "before");
	}	
	
	@Test
	public void testIsShowBeforeClosing(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeClosing("T");
		assertTrue(cons.isShowBeforeClosing());
		cons.setShowBeforeClosing("F");
		assertFalse(cons.isShowBeforeClosing());
	}
	
	@Test
	public void testSetShowBeforeClosingWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeClosing(true);
		assertEquals(cons.getShowBeforeClosing(), "T");
	}
	
	@Test
	public void testSetShowBeforeClosingWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setShowBeforeClosing(false);
		assertEquals(cons.getShowBeforeClosing(), "F");
	}
	
	
	@Test
	public void testGetSetClosingAtMaxVoters(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters("T");
		assertEquals(cons.getClosingAtMaxVoters(), "T");
	}	
	
	@Test
	public void testIsClosingAtMaxVoters(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters("T");
		assertTrue(cons.isClosingAtMaxVoters());
		cons.setClosingAtMaxVoters("F");
		assertFalse(cons.isClosingAtMaxVoters());
	}
	
	@Test
	public void testSetClosingAtMaxVotersWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters(true);
		assertEquals(cons.getClosingAtMaxVoters(), "T");
	}
	
	@Test
	public void testSetClosingAtMaxVotersWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters(false);
		assertEquals(cons.getClosingAtMaxVoters(), "F");
	}
	
	@Test
	public void testGetSetOpened(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setOpened("F");
		assertEquals(cons.getOpened(), "F");
	}	
	
	@Test
	public void testIsOpened(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setOpened("F");
		assertFalse(cons.isOpened());
		cons.setOpened("T");
		assertTrue(cons.isOpened());
	}
	
	@Test
	public void testSetOpenedWithTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setOpened(true);
		assertEquals(cons.getOpened(), "T");
	}
	
	@Test
	public void testSetOpenedWithFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setOpened(false);
		assertEquals(cons.getOpened(), "F");
	}
	
	@Test
	public void testIsVotedTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		SocialEntity voter = new SocialEntity();
		ConsultationVote vote = new ConsultationVote(voter, "comment", "other");
		List<ConsultationVote> votes = new ArrayList<>();
		votes.add(vote);
		cons.setConsultationVotes(votes);
		assertTrue(cons.isVoted(voter));
	}
	
	@Test
	public void testIsVotedFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		SocialEntity voter1 = new SocialEntity("toto", "toto", "toto@mail.com");
		SocialEntity voter2 = new SocialEntity("titi", "titi", "titi@mail.com");
		ConsultationVote vote = new ConsultationVote(voter1, "comment", "other");
		List<ConsultationVote> votes = new ArrayList<>();
		votes.add(vote);
		cons.setConsultationVotes(votes);
		assertFalse(cons.isVoted(voter2));
	}
	
	@Test
	public void testIsMaximumVoterReachedWithAllTrue(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters("T");
		cons.setMaxVoters(1);
		SocialEntity voter1 = new SocialEntity("toto", "toto", "toto@mail.com");
		ConsultationVote vote = new ConsultationVote(voter1, "comment", "other");
		List<ConsultationVote> votes = new ArrayList<>();
		votes.add(vote);
		cons.setConsultationVotes(votes);
		assertTrue(cons.isMaximumVoterReached());
	}
	
	@Test
	public void testIsMaximumVoterReachedFalseDueToClosingAtMaxVoters(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters("F");
		cons.setMaxVoters(1);
		SocialEntity voter1 = new SocialEntity("toto", "toto", "toto@mail.com");
		ConsultationVote vote = new ConsultationVote(voter1, "comment", "other");
		List<ConsultationVote> votes = new ArrayList<>();
		votes.add(vote);
		cons.setConsultationVotes(votes);
		assertFalse(cons.isMaximumVoterReached());
	}
	
	@Test
	public void testIsMaximumVoterReachedFalseDuetoSizeConsultationVoteList(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters("T");
		cons.setMaxVoters(1);
		List<ConsultationVote> votes = new ArrayList<>();
		cons.setConsultationVotes(votes);
		assertFalse(cons.isMaximumVoterReached());
	}
	
	@Test
	public void testIsMaximumVoterReachedWithAllFalse(){
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtMaxVoters("F");
		cons.setMaxVoters(1);
		List<ConsultationVote> votes = new ArrayList<>();
		cons.setConsultationVotes(votes);
		assertFalse(cons.isMaximumVoterReached());
	}
	
	@Test
	public void testIsDeadlineReachedWithAllTrue() throws ParseException{
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate("T");
		cons.setMaxDate(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/1989"));
		assertTrue(cons.isDeadlineReached());
	}
	
	@Test
	public void testIsDeadlineReachedFalseDueToClosingAtDate() throws ParseException{
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate("F");
		cons.setMaxDate(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/1989"));
		assertFalse(cons.isDeadlineReached());
	}
	
	@Test
	public void testIsDeadlineReachedFalseDueToDate() throws ParseException{
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate("T");
		cons.setMaxDate(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/4989"));
		assertFalse(cons.isDeadlineReached());
	}
	
	@Test
	public void testIsDeadlineReachedWithAllFalse() throws ParseException{
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		cons.setClosingAtDate("F");
		cons.setMaxDate(DateFormat.getDateInstance(DateFormat.SHORT).parse("04/07/4989"));
		assertFalse(cons.isDeadlineReached());
	}
	
	@Test
	public void testPersist() throws ParseException {
		final SocialEntity socialEntity = new SocialEntity("test", "test",
				"test@test.com");
		SocialGroup sc = new SocialGroup();
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		Consultation cons = new Consultation(socialEntity, "Consultation test",
				"for testing consultations", TypeConsultation.YES_NO, lsc);
		em.getTransaction().begin();
		em.persist(socialEntity);
		em.persist(cons);
		em.getTransaction().commit();
		Consultation cons2 = em.find(Consultation.class, cons.getId());
		assertEquals(cons.getId(), cons2.getId());
		assertEquals(cons.getDescription(), cons2.getDescription());
	}

	@Ignore
	@Test
	public void testPersistTwo() throws ParseException {
		SocialEntity es = new SocialEntity("name", "prenom",
				"mailannonce@mail.com");
		em.getTransaction().begin();
		em.persist(es);
		em.getTransaction().commit();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date = (Date) formatter.parse("29/01/02");
		Announcement annonce = new Announcement(es, "TITLE", "content", date,
				true);
		em.getTransaction().begin();
		em.persist(annonce);
		em.getTransaction().commit();
		Announcement annonce2 = em.find(Announcement.class, annonce.getId());
		assertEquals(annonce.getId(), annonce2.getId());
		assertEquals(annonce.getCreationDate(), annonce2.getCreationDate());
		assertEquals(annonce.getEndDate(), annonce2.getEndDate());
		assertEquals(annonce.getContent(), annonce2.getContent());
		assertEquals(annonce.getCreator(), annonce2.getCreator());
	}

	@Ignore
	@Test
	public void testGeneratedValueId() throws ParseException {
		final SocialEntity socialEntity = new SocialEntity("test2", "test2",
				"test2@test.com");
		Announcement annonce = new Announcement(socialEntity,
				"test2 Announcement", "HEHEHEHEHE2", new Date(), false);
		em.getTransaction().begin();
		em.persist(socialEntity);
		em.persist(annonce);
		em.getTransaction().commit();
		final SocialEntity socialEntity1 = new SocialEntity("test3", "test3",
				"test3@test.com");
		Announcement annonce2 = new Announcement(socialEntity1,
				"test 3Announcement", "HEHEHEHEHE3", new Date(), false);
		em.getTransaction().begin();
		em.persist(socialEntity1);
		em.persist(annonce2);
		em.getTransaction().commit();
		assertEquals(annonce.getId() + 1, annonce2.getId());
	}

	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testDateIsNotNull() {
		Announcement annonce = new Announcement(null, null, null, null, true);
		em.getTransaction().begin();
		em.persist(annonce);
		em.getTransaction().commit();
	}
}
