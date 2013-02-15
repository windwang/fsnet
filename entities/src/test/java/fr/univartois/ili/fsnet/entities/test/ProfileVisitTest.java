package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.*;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.ProfileVisite;
import fr.univartois.ili.fsnet.entities.ProfileVisitePK;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ProfileVisitTest {

	private EntityManager em;

	private SocialEntity alan;
	private SocialEntity momo;
	private SocialEntity pepe;
	private SocialEntity kenny;
	private SocialEntity kevin;

	private ProfileVisite alan_momo;
	private ProfileVisite momo_pepe;
	private ProfileVisite kenny_kevin;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();

		alan = new SocialEntity("alan", "alan", "alan@alan.fr");
		momo = new SocialEntity("momo", "momo", "momo@momo.fr");
		pepe = new SocialEntity("pepe", "pepe", "pepe@pepe.fr");
		kenny = new SocialEntity("kenny", "kenny", "kenny@kenny.fr");
		kevin = new SocialEntity("kevin", "kevin", "kevin@kevin.fr");

		alan_momo = new ProfileVisite(alan, momo);
		momo_pepe = new ProfileVisite(momo, pepe);
		kenny_kevin = new ProfileVisite(kenny, kevin);

		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			fail();
		}

	}

	@After
	public void tearDown() {
		em.close();
	}

	@Test
	public void persistanceTest() {
		em.getTransaction().begin();
		em.persist(alan);
		em.persist(momo);
		em.persist(pepe);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(alan_momo);
		em.getTransaction().commit();
		em.getTransaction().begin();
		ProfileVisite pv2 = em.find(ProfileVisite.class, new ProfileVisitePK(
				momo.getId(), alan.getId()));
		em.getTransaction().commit();
		assertEquals(alan_momo, pv2);

	}

	@Test
	public void sameUserConsultTheSameProfilTest() {
		Date d = alan_momo.getLastVisite();
		alan_momo.visiteAgainst();
		assertNotSame(d, alan_momo.getLastVisite());
		assertTrue(d.before(alan_momo.getLastVisite()));
		assertSame(alan, alan_momo.getVisited());
		assertSame(momo, alan_momo.getVisitor());
	}

	@Test
	public void notEqualsTest() {

		assertFalse(momo_pepe.equals(alan_momo));
		assertFalse(momo_pepe.equals(null));
		assertFalse(momo_pepe.equals(momo));
	}

	@Test
	public void equalsTestWithLastVisite() {

		ProfileVisite pv3 = new ProfileVisite();

		pv3.setVisited(alan_momo.getVisited());
		pv3.setVisitor(alan_momo.getVisitor());
		pv3.setLastVisite(alan_momo.getLastVisite());

		assertEquals(alan_momo, pv3);

		pv3.visiteAgainst();
		assertFalse(alan_momo.equals(pv3));

		pv3.setLastVisite(null);
		assertFalse(pv3.equals(alan_momo));
		
		alan_momo.setLastVisite(null);
		assertTrue(pv3.equals(alan_momo));

	}

	@Test
	public void equalsTestWithVisited() {
		ProfileVisite pv3 = new ProfileVisite();

		pv3.setVisited(alan_momo.getVisited());
		pv3.setVisitor(alan_momo.getVisitor());
		pv3.setLastVisite(alan_momo.getLastVisite());

		assertEquals(alan_momo, pv3);

		pv3.setVisited(pepe);
		assertFalse(alan_momo.equals(pv3));

		pv3.setVisited(null);
		assertFalse(pv3.equals(alan_momo));
		
		alan_momo.setVisited(null);		
		assertTrue(pv3.equals(alan_momo));

	}
	
	@Test
	public void equalsTestWithVisitor() {
		ProfileVisite pv3 = new ProfileVisite();

		pv3.setVisited(alan_momo.getVisited());
		pv3.setVisitor(alan_momo.getVisitor());
		pv3.setLastVisite(alan_momo.getLastVisite());

		assertEquals(alan_momo, pv3);

		pv3.setVisitor(pepe);
		assertFalse(alan_momo.equals(pv3));

		pv3.setVisitor(null);
		assertFalse(pv3.equals(alan_momo));
		
		alan_momo.setVisitor(null);		
		assertTrue(pv3.equals(alan_momo));

	}

	@Test
	public void hashCodeTest() {
		int hc = kenny_kevin.hashCode();
		em.getTransaction().begin();
		em.persist(kenny);
		em.persist(kevin);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(kenny_kevin);
		em.getTransaction().commit();
		em.getTransaction().begin();
		ProfileVisite pv2 = em.find(ProfileVisite.class, new ProfileVisitePK(
				kevin.getId(), kenny.getId()));
		em.getTransaction().commit();
		assertEquals(hc, pv2.hashCode());
		pv2.setLastVisite(null);
		assertSame(pv2.getLastVisite(), null);
		assertNotSame(hc, pv2.hashCode());
		hc = pv2.hashCode();
		pv2.setVisited(null);
		assertNotSame(hc, pv2.hashCode());
		hc = pv2.hashCode();
		pv2.setVisitor(null);
		assertNotSame(hc, pv2.hashCode());
	}

}
