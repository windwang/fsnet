package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import fr.univartois.ili.fsnet.entities.ProfileVisitePK;


public class ProfileVisitePKTest {
	
	/*private EntityManager em;

	@Before
	public void setUp() {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
	}*/

    /*@Test
    public void testPersist() throws ParseException {
    	ProfileVisitePK profile = new ProfileVisitePK(1,2);
        em.getTransaction().begin();
        em.persist(profile);
        em.getTransaction().commit(); 
        ProfileVisitePK profile2 = em.find(ProfileVisitePK.class, profile.);
        assertEquals(profile.getVisited(), profile2.getVisited());
        assertEquals(profile.getVisitor(), profile2.getVisitor());
    }*/
    
    @Test
    public void testSetByMethodsAndGet() {
    	ProfileVisitePK profile = new ProfileVisitePK();
    	profile.setVisitor(1);
    	profile.setVisited(2);
    	assertEquals(profile.getVisitor(), 1);
    	assertEquals(profile.getVisited(), 2);
    }
    
    @Test
    public void testSetByConstructorAndGet() {
    	ProfileVisitePK profile = new ProfileVisitePK(1,2);
    	assertEquals(profile.getVisitor(), 1);
    	assertEquals(profile.getVisited(), 2);
    }
    
    @Test
	public void testCreateProfileVisiteWithEmptyVisitor() {
    	ProfileVisitePK profile = new ProfileVisitePK();
		assertEquals(profile.getVisited(), 0);
	}
    
    @Test
	public void testCreateProfileVisiteWithEmptyVisited() {
    	ProfileVisitePK profile = new ProfileVisitePK();
		assertEquals(profile.getVisitor(), 0);
	}

}
