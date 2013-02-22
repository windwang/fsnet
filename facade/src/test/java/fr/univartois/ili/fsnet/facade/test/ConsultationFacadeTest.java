package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.ConsultationChoice;
import fr.univartois.ili.fsnet.entities.ConsultationChoiceVote;
import fr.univartois.ili.fsnet.entities.ConsultationVote;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.entities.Consultation.TypeConsultation;
import fr.univartois.ili.fsnet.facade.ConsultationFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

public class ConsultationFacadeTest {

	private EntityManager em;
	private ConsultationFacade cf;
	private String[] choices = {"coix1", "choix2"};

	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();
		cf = new ConsultationFacade(em);
	}

	@Test
	public void testCreate() {
		SocialEntity creator = new SocialEntity("toto1", "toto1", "toto1@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe1", "description1");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre1", "la description1", choices, TypeConsultation.YES_NO, lsc);
		em.getTransaction().commit();
		Consultation consp = em.find(Consultation.class, cons.getId());
		assertSame(cons, consp);	
	}

	@Test
	public void testSearch() {
		SocialEntity creator = new SocialEntity("toto2", "toto2", "toto2@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe2", "description2");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre2", "la description2", choices, TypeConsultation.YES_NO, lsc);
		em.getTransaction().commit();
		Consultation consFound = cf.getConsultation(cons.getId());
		assertSame(cons,consFound);
	}

	@Test
	public void testGetConsultationVote(){
		SocialEntity creator = new SocialEntity("toto3", "toto3", "toto3@mail.com");
		SocialEntity voter = new SocialEntity("tat3", "tata3", "tata3@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe3", "description3");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre3", "la description3", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment", "other");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		ConsultationVote cvFound = cf.getVote(cv.getId());
		assertSame(cv, cvFound);
	}

	@Test
	public void testDeleteVoteWithOnlyConsultationVote(){
		SocialEntity creator = new SocialEntity("toto4", "toto4", "toto4@mail.com");
		SocialEntity voter = new SocialEntity("tata4", "tata4", "tata4@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe4", "description4");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre4", "la description4", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment4", "other4");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		cf.deleteVote(cv);
		ConsultationVote cvFound = cf.getVote(cv.getId());
		assertNull(cvFound);
	}

	@Test
	public void testDeleteVoteWithConsulationSocialEntityIsNotNullAndConsultationVoteIsNotNullAndVoterEqualsSocialEntity(){
		SocialEntity creator = new SocialEntity("toto5", "toto5", "toto5@mail.com");
		SocialEntity voter = new SocialEntity("tata5", "tata5", "tata5@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe5", "description5");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre5", "la description5", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment5", "other5");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		cf.deleteVote(cons, voter, cv);
		ConsultationVote cvFound = cf.getVote(cv.getId());
		assertNull(cvFound);
	}

	@Test(expected = UnauthorizedOperationException.class)
	public void testDeleteVoteWithConsulationSocialEntityIsNotNullAndConsultationVoteIsNotNullAndVoterNotEqualsSocialEntity(){
		SocialEntity creator = new SocialEntity("toto6", "toto6", "toto6@mail.com");
		SocialEntity voter = new SocialEntity("tata6", "tata6", "tata6@mail.com");
		SocialEntity voter2 = new SocialEntity("titi6", "titi6", "titi6@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe6", "description6");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre6", "la description6", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment6", "other6");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		cf.deleteVote(cons, voter2, cv);
	}

	@Test
	public void testDeleteVoteWithConsulationSocialEntityIsNullAndConsultationVoteIsNotNull(){
		SocialEntity creator = new SocialEntity("toto7", "toto7", "toto7@mail.com");
		SocialEntity voter = new SocialEntity("tata7", "tata7", "tata7@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe7", "description7");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre7", "la description7", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment7", "other7");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		cf.deleteVote(cons, voter, null);
		//if vote is null, delete vote do nothing
		ConsultationVote cvFound = cf.getVote(cv.getId());
		assertNotNull(cvFound);
	}

	@Test
	public void testDeleteVoteWithConsulationSocialEntityIsNotNullAndConsultationVoteIsNull(){
		SocialEntity creator = new SocialEntity("toto8", "toto8", "toto8@mail.com");
		SocialEntity voter = new SocialEntity("tata8", "tata8", "tata8@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe8", "description8");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre8", "la description8", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment8", "other8");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		cf.deleteVote(cons, null, cv);
		//if voter is null, delete vote do nothing
		ConsultationVote cvFound = cf.getVote(cv.getId());
		assertNotNull(cvFound);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDeleteVoteWithOnlyConsultationVoteNull(){
		SocialEntity creator = new SocialEntity("toto9", "toto9", "toto9@mail.com");
		SocialEntity voter = new SocialEntity("tata9", "tata9", "tata9@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe9", "description9");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre9", "la description9", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment9", "other9");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		cf.deleteVote(null);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testGetUserConsultationsWithNullMember(){
		SocialEntity creator = new SocialEntity("toto10", "toto10", "toto10@mail.com");
		SocialEntity voter = new SocialEntity("tata10", "tata10", "tata10@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe10", "description10");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre10", "la description10", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment10", "other10");
		cv.setConsultation(cons);
		em.persist(cv);
		em.getTransaction().commit();
		cf.getUserConsultations(null);
	}

	@Test
	public void testGetUserConsultations(){
		SocialEntity creator = new SocialEntity("toto11", "toto11", "toto11@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe11", "description11");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre11", "la description11", choices, TypeConsultation.YES_NO, lsc);
		em.getTransaction().commit();
		List<Consultation> lcons = cf.getUserConsultations(creator);
		assertFalse(lcons.isEmpty());
		assertTrue(lcons.contains(cons));
	}
	
	@Test
	public void testVoteForConsultationWithNullConsultation(){
		SocialEntity creator = new SocialEntity("toto12", "toto12", "toto12@mail.com");
		SocialEntity voter = new SocialEntity("tata12", "tata12", "tata12@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe12", "description12");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre12", "la description12", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment12", "other12");
		cf.voteForConsultation(null, cv);
		em.getTransaction().commit();
		assertTrue(cons.getConsultationVotes().isEmpty());
	}
	
	@Test
	public void testVoteForConsultationWithNullConsultationVote(){
		SocialEntity creator = new SocialEntity("toto13", "toto13", "toto13@mail.com");
		SocialEntity voter = new SocialEntity("tata13", "tata13", "tata13@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe13", "description13");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre13", "la description13", choices, TypeConsultation.YES_NO, lsc);
		cf.voteForConsultation(cons, null);
		em.getTransaction().commit();
		assertTrue(cons.getConsultationVotes().isEmpty());
	}
	
	@Test
	public void testVoteForConsultation(){
		SocialEntity creator = new SocialEntity("toto14", "toto14", "toto14@mail.com");
		SocialEntity voter = new SocialEntity("tata14", "tata14", "tata14@mail.com");
		voter.setVotes(new ArrayList<ConsultationVote>());
		SocialGroup sc = new SocialGroup(creator, "groupe14", "description14");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre14", "la description14", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment14", "other14");
		cf.voteForConsultation(cons, cv);
		em.getTransaction().commit();
		assertFalse(cons.getConsultationVotes().isEmpty());
		assertTrue(cons.getConsultationVotes().contains(cv));
	}
	
	@Test
	public void testDeleteConsultationWithNullConsultation(){
		SocialEntity creator = new SocialEntity("toto15", "toto15", "toto15@mail.com");
		SocialEntity voter = new SocialEntity("tata15", "tata15", "tata15@mail.com");
		voter.setVotes(new ArrayList<ConsultationVote>());
		SocialGroup sc = new SocialGroup(creator, "groupe15", "description15");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre15", "la description15", choices, TypeConsultation.YES_NO, lsc);
		voter.getInteractions().add(cons);
		cf.deleteConsultation(null, voter);
		em.getTransaction().commit();
		assertTrue(voter.getInteractions().contains(cons));

	}
	
	@Test
	public void testDeleteConsultationWithNullMember(){
		SocialEntity creator = new SocialEntity("toto16", "toto16", "toto16@mail.com");
		SocialEntity voter = new SocialEntity("tata16", "tata16", "tata16@mail.com");
		voter.setVotes(new ArrayList<ConsultationVote>());
		SocialGroup sc = new SocialGroup(creator, "groupe16", "description16");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre16", "la description16", choices, TypeConsultation.YES_NO, lsc);
		voter.getInteractions().add(cons);
		cf.deleteConsultation(cons, null);
		em.getTransaction().commit();
		assertTrue(voter.getInteractions().contains(cons));

	}
	
	@Test
	public void testDeleteConsultation(){
		SocialEntity creator = new SocialEntity("toto17", "toto17", "toto17@mail.com");
		SocialEntity voter = new SocialEntity("tata17", "tata17", "tata17@mail.com");
		voter.setVotes(new ArrayList<ConsultationVote>());
		SocialGroup sc = new SocialGroup(creator, "groupe17", "description17");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre17", "la description17", choices, TypeConsultation.YES_NO, lsc);
		voter.getInteractions().add(cons);
		cf.deleteConsultation(cons, voter);
		em.getTransaction().commit();
		assertTrue(voter.getInteractions().isEmpty());

	}

	@Test
	public void testCloseConsultationWithConsultationNull(){
		SocialEntity creator = new SocialEntity("toto18", "toto18", "toto18@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe18", "description18");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre18", "la description18", choices, TypeConsultation.YES_NO, lsc);
		cf.closeConsultation(null);
		em.getTransaction().commit();
		assertTrue(cons.isOpened());
	}
	
	@Test
	public void testCloseConsultation(){
		SocialEntity creator = new SocialEntity("toto19", "toto19", "toto19@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe19", "description19");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre19", "la description19", choices, TypeConsultation.YES_NO, lsc);
		cf.closeConsultation(cons);
		em.getTransaction().commit();
		assertFalse(cons.isOpened());
	}
	
	@Test
	public void testOpenConsultationWithConsultationNull(){
		SocialEntity creator = new SocialEntity("toto120", "toto20", "toto20@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe20", "description20");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre20", "la description20", choices, TypeConsultation.YES_NO, lsc);
		cf.closeConsultation(cons);
		cf.openConsultation(null);
		em.getTransaction().commit();
		assertFalse(cons.isOpened());
	}
	
	@Test
	public void testOpenConsultation(){
		SocialEntity creator = new SocialEntity("toto21", "toto21", "toto21@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe21", "description21");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre21", "la description21", choices, TypeConsultation.YES_NO, lsc);
		cf.openConsultation(cons);
		em.getTransaction().commit();
		assertTrue(cons.isOpened());
	}
	
	@Test
	public void testGetConsultationsContaining(){
		SocialEntity creator = new SocialEntity("toto22", "toto22", "toto22@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe22", "description22");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre22", "la description22", choices, TypeConsultation.YES_NO, lsc);
		em.getTransaction().commit();
		List<Consultation> lcons = cf.getConsultationsContaining("titre22");
		assertTrue(lcons.contains(cons));
	}
	
	@Test
	public void testGetOtherChoice(){
		SocialEntity creator = new SocialEntity("toto23", "toto23", "toto23@mail.com");
		SocialEntity voter = new SocialEntity("tata23", "tata23", "tata23@mail.com");
		voter.setVotes(new ArrayList<ConsultationVote>());
		SocialGroup sc = new SocialGroup(creator, "groupe23", "description23");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		em.persist(voter);
		Consultation cons = cf.createConsultation(creator, "le titre23", "la description23", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment23", "other23");
		cf.voteForConsultation(cons, cv);
		em.getTransaction().commit();
		List<String> lcons = cf.getOtherChoice(cons.getId(), "other23");
		assertTrue(lcons.contains("other23"));
	}
	
	@Test
	public void testGetChoicesVote(){
		SocialEntity creator = new SocialEntity("toto24", "toto24", "toto24@mail.com");
		SocialEntity voter = new SocialEntity("tata24", "tata24", "tata24@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe24", "description24");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = cf.createConsultation(creator, "le titre24", "la description24", choices, TypeConsultation.YES_NO, lsc);
		ConsultationVote cv = new ConsultationVote(voter, "comment24", "other24");
		ConsultationChoice choice = new ConsultationChoice(cons, "choix1");
		ConsultationChoiceVote choicevote = new ConsultationChoiceVote(cv, choice);
		em.persist(cv);
		em.persist(voter);
		em.persist(choice);
		em.persist(choicevote);
		em.getTransaction().commit();
		List<ConsultationChoiceVote> lchoice = cf.getChoicesVote(choice.getId());
		assertTrue(lchoice.contains(choicevote));

	}	
	
	@Test
	public void testGetConsultationsWhichOccurToday(){
		int before = cf.getConsultationsWhichOccurToday().size();
		SocialEntity creator = new SocialEntity("toto25", "toto25", "toto25@mail.com");
		SocialGroup sc = new SocialGroup(creator, "groupe25", "description25");
		ArrayList<SocialGroup> lsc = new ArrayList<SocialGroup>();
		lsc.add(sc);
		em.getTransaction().begin();
		em.persist(sc);
		Consultation cons = new Consultation(creator, "le titre25", "la description25", TypeConsultation.YES_NO, lsc);
		
		Calendar calendar = GregorianCalendar.getInstance();
		Date today = calendar.getTime();

		cons.setMaxDate(today);
		em.persist(cons);
		em.getTransaction().commit();
		int after = cf.getConsultationsWhichOccurToday().size();
		assertEquals(before + 1, after);
	}
}

