package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.ConsultationVote;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionRole;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;

public class SocialEntityTest {

	private EntityManager em;
	private SocialEntity e = new SocialEntity();

	/* Objects instantiated at the beginning to test quicker */
	private Date d = new Date();
	private List<Interaction> interactions = new ArrayList<>();
	private List<InteractionRole> interactionRoles = new ArrayList<>();
	private List<Interest> interests = new ArrayList<>();
	private List<TopicMessage> topicMessages = new ArrayList<>();
	private List<Topic> topics = new ArrayList<>();
	private List<SocialEntity> socialEntities = new ArrayList<>();
	private List<PrivateMessage> privateMessages = new ArrayList<>();
	private List<ProfileVisite> profileVisites = new ArrayList<>();
	private List<ConsultationVote> votes = new ArrayList<>();

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
	}

	@Test
	public void testSetAndGetAddress() {
		Address adr = new Address("rue Jean Souvraz", "LENS");
		e.setAddress(adr);
		assertEquals(adr, e.getAddress());
	}

	@Test
	public void testSetAndGetInscriptionDate() {
		e.setInscriptionDate(d);
		assertEquals(d, e.getInscritpionDate());
	}

	@Test
	public void testSetAndGetBirthDate() {
		e.setBirthDate(d);
		assertEquals(d, e.getBirthDate());
	}

	@Test
	public void testSetAndGetLastConnection() {
		e.setLastConnection(d);
		assertEquals(d, e.getLastConnection());
	}

	@Test
	public void testSetAndGetSex() {
		e.setSex("F");
		assertEquals("F", e.getSex());
	}

	@Test
	public void testSetAndGetPassword() {
		e.setPassword("azerty");
		assertEquals("azerty", e.getPassword());
	}

	@Test
	public void testSetAndGetProfession() {
		e.setProfession("profession");
		assertEquals("profession", e.getProfession());
	}

	@Test
	public void testSetAndGetPhone() {
		e.setPhone("0000000000");
		assertEquals("0000000000", e.getPhone());
	}

	@Test
	public void testSetAndGetInteractions() {
		e.setInteractions(interactions);
		assertEquals(interactions, e.getInteractions());

	}

	@Test
	public void testSetAndGetFavoriteInteractions() {
		e.setFavoriteInteractions(interactions);
		assertEquals(interactions, e.getFavoriteInteractions());
	}

	@Test
	public void testSetAndGetInterests() {
		e.setInterests(interests);
		assertEquals(interests, e.getInterests());
	}

	@Test
	public void testSetAndGetMessages() {
		e.setMessages(topicMessages);
		assertEquals(topicMessages, e.getTopicMessages());
	}

	@Test
	public void testSetAndGetTopics() {
		e.setTopics(topics);
		assertEquals(topics, e.getTopics());
	}

	@Test
	public void testSetAndGetContacts() {
		e.setContacts(socialEntities);
		assertEquals(socialEntities, e.getContacts());
	}

	@Test
	public void testSetAndGetRefused() {
		e.setRefused(socialEntities);
		assertEquals(socialEntities, e.getRefused());
	}

	@Test
	public void testSetAndGetAsked() {
		e.setAsked(socialEntities);
		assertEquals(socialEntities, e.getAsked());
	}

	@Test
	public void testSetAndGetRequested() {
		e.setRequested(socialEntities);
		assertEquals(socialEntities, e.getRequested());
	}

	@Test
	public void testSetAndGetReceivedPrivateMessages() {
		e.setReceivedPrivateMessages(privateMessages);
		assertEquals(privateMessages, e.getReceivedPrivateMessages());
	}

	@Test
	public void testSetAndGetSentPrivateMessages() {
		e.setSentPrivateMessages(privateMessages);
		assertEquals(privateMessages, e.getSentPrivateMessages());
	}

	@Test
	public void testSetAndGetRolesInInteractions() {
		e.setRolesInInteractions(interactionRoles);
		assertEquals(interactionRoles, e.getRolesInInteractions());
	}

	@Test
	public void testSetAndGetVisitesOnProfiles() {
		e.setVisitesOnProfiles(profileVisites);
		assertEquals(profileVisites, e.getVisitesOnProfile());
	}

	@Test
	public void testSetAndGetVisitedProfiles() {
		e.setVisitedProfiles(profileVisites);
		assertEquals(profileVisites, e.getVisitedProfiles());
	}

	@Test
	public void testSetAndGetVotes() {
		e.setVotes(votes);
		assertEquals(votes, e.getVotes());
	}

	@Test
	public void testSetAndGetIsEnabled() {
		e.setIsEnabled(true);
		assertTrue(e.getIsEnabled());
	}

	@Test
	public void testSetAndGetInteractionsRead() {
		e.setInteractionsRead(interactions);
		assertEquals(interactions, e.getInteractionsRead());
	}

	@Test
	public void testHascodeWithNull() {
		SocialEntity e1 = new SocialEntity();
		SocialEntity e2 = new SocialEntity();
		assertEquals(e1.hashCode(), e2.hashCode());
	}

	@Test
	public void testCompareTo() {
		SocialEntity e1 = new SocialEntity("a", "a", "");
		SocialEntity e2 = new SocialEntity("a", "b", "");
		SocialEntity e3 = new SocialEntity("b", "a", "");

		assertEquals(-1, e1.compareTo(e2));
		assertEquals(-1, e1.compareTo(e3));
		assertEquals(-1, e2.compareTo(e3));

		assertEquals(1, e2.compareTo(e1));
		assertEquals(1, e3.compareTo(e1));
		assertEquals(1, e3.compareTo(e2));

		assertEquals(0, e1.compareTo(e1));
	}

	@Test
	public void testPersist() {
		final String lastName = "Germain";
		final String firstName = "Tantoine";
		final String mail = "GermainTantoine@gmail.com";
		SocialEntity ent = new SocialEntity(lastName, firstName, mail);
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		SocialEntity ent2 = em.find(SocialEntity.class, ent.getId());
		assertEquals(ent2.getId(), ent.getId());
		assertEquals(ent2.getName(), lastName);
		assertEquals(ent2.getFirstName(), firstName);
		assertEquals(ent2.getEmail(), mail);
	}

	@Test
	public void testUpdate() {
		SocialEntity ent = new SocialEntity("titi", "tata", "esupdate@gmail.com");
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
		assertEquals(ent.getEmail(), "esupdate@gmail.com");
		ent.setEmail("esupdate2@gmail.com");
		em.getTransaction().begin();
		em.merge(ent);
		em.getTransaction().commit();
		ent = em.find(SocialEntity.class, ent.getId());
		assertEquals(ent.getEmail(), "esupdate2@gmail.com");
	}

	@Test
	public void testDelete() {
		SocialEntity ent1 = new SocialEntity("titi", "titi", "mail1@gmail.com");
		SocialEntity ent2 = new SocialEntity("tyty", "tyty", "mail2@gmail.com");
		SocialEntity ent3 = new SocialEntity("tutu", "tutu", "mail3@gmail.com");

		SocialEntity[] lesEntites = { ent1, ent2, ent3 };
		em.getTransaction().begin();
		for (SocialEntity ent : lesEntites) {
			em.persist(ent);
		}
		em.getTransaction().commit();

		em.getTransaction().begin();
		em.remove(ent2);
		em.getTransaction().commit();
		assertNull(em.find(SocialEntity.class, ent2.getId()));

	}

	@Test(expected = RollbackException.class)
	public void testUniqueMail() {
		SocialEntity ent1 = new SocialEntity("zaza", "zaza", "zaza@gmail.com");
		SocialEntity ent2 = new SocialEntity("zozo", "zozo", "zaza@gmail.com");
		em.getTransaction().begin();
		em.persist(ent1);
		em.persist(ent2);
		em.getTransaction().commit();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRequieredMail() {
		SocialEntity ent = new SocialEntity("zaza", "zaza", null);
		em.getTransaction().begin();
		em.persist(ent);
		em.getTransaction().commit();
	}

	@Test
	public void testAddInteractionRead() {
		int nbInteraction = (Integer) em.createNativeQuery("SELECT COUNT(*) FROM Interaction").getSingleResult();

		em.getTransaction().begin();
		SocialEntity user1 = new SocialEntity("name1", "firstName1", "email1@g.com");
		em.persist(user1);
		Interaction it1 = new Announcement(user1, "title1", "content1", new Date(), false);
		em.persist(it1);
		Interaction it2 = new Announcement(user1, "title2", "content2", new Date(), false);
		em.persist(it2);
		em.getTransaction().commit();

		em.getTransaction().begin();
		SocialEntity userLoad = em.find(SocialEntity.class, user1.getId());
		assertEquals(user1.getId(), userLoad.getId());
		assertEquals(0, userLoad.getInteractionsRead().size());
		Interaction itL1 = em.find(Interaction.class, it1.getId());
		assertEquals(it1.getId(), itL1.getId());
		assertEquals(0, itL1.getReaders().size());
		Interaction itL2 = em.find(Interaction.class, it2.getId());
		assertEquals(it2.getId(), itL2.getId());
		assertEquals(0, itL2.getReaders().size());
		em.getTransaction().commit();

		em.getTransaction().begin();
		userLoad = em.find(SocialEntity.class, user1.getId());
		itL1 = em.find(Interaction.class, it1.getId());
		itL2 = em.find(Interaction.class, it2.getId());
		userLoad.addInteractionRead(itL1);
		em.getTransaction().commit();

		em.getTransaction().begin();
		userLoad = em.find(SocialEntity.class, user1.getId());
		itL1 = em.find(Interaction.class, it1.getId());
		itL2 = em.find(Interaction.class, it2.getId());
		assertEquals(1, userLoad.getInteractionsRead().size());
		assertTrue(userLoad.getInteractionsRead().contains(itL1));
		assertEquals(1, itL1.getReaders().size());
		assertTrue(itL1.getReaders().contains(user1));
		assertEquals(0, itL2.getReaders().size());
		em.getTransaction().commit();
	}

	@Test
	public void TestEquals()
	{
		SocialEntity ent1 = new SocialEntity("totor", "totor", "totor@gmail.com");
		SocialEntity ent2 = new SocialEntity("totor", "totor", "totor@gmail.com");
		assertFalse(ent1.equals(null));
		assertFalse(ent1.equals(""));
		//firstname
		ent1.setFirstname(null);
		assertFalse(ent1.equals(ent2));
		ent1.setFirstname("totor");
		//adress
		ent1.setAddress(new Address("123 rue de la pays", "Paris"));
		assertFalse(ent1.equals(ent2));
		ent1.setAddress(null);
		//inscri
		Calendar calendar= GregorianCalendar.getInstance();
		calendar.roll(Calendar.HOUR, -1);
		ent2.setInscriptionDate(calendar.getTime());
		assertFalse(ent1.equals(ent2));
		ent2.setInscriptionDate(null);
		//birth
		ent2.setBirthDate(calendar.getTime());
		assertFalse(ent1.equals(ent2));
		ent2.setBirthDate(null);
		//last co
		ent2.setLastConnection(calendar.getTime());
		assertFalse(ent1.equals(ent2));
		ent2.setLastConnection(null);
		//sex
		ent2.setSex("F");
		assertFalse(ent1.equals(ent2));
		ent2.setSex(null);
		//pass
		ent1.setPassword("toto");
		assertFalse(ent1.equals(ent2));
		ent1.setPassword(null);
		//profession
		ent1.setProfession("dentiste");
		assertFalse(ent1.equals(ent2));
		ent1.setProfession(null);
		//email
		ent1.setEmail("toto@gmail.com");
		assertFalse(ent1.equals(ent2));
		ent1.setEmail("totor@gmail.com");
		//phone
		ent1.setPhone("0600000000");
		assertFalse(ent1.equals(ent2));
		ent1.setPhone(null);
		
	}
}
