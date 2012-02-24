package fr.univartois.ili.fsnet.entities.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.MemberCV;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class CvTest {
	
	
	private EntityManager em;

    @Before
    public void setUp() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("TestPU");
        em = fact.createEntityManager();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPersist() throws ParseException {
    	List<Curriculum> results=new ArrayList<Curriculum>();
//    	final TypedQuery<Curriculum> query;
//    	query = em.createQuery("SELECT c FROM Curriculum c",Curriculum.class);
//		results = query.getResultList();
		Curriculum cur = new Curriculum();
		
    	MemberCV mc = new MemberCV();
    	mc.setId(1);
    	
    	cur.setMember(mc);
    	em.getTransaction().begin();
    	em.persist(mc);
    	em.persist(cur);
    	em.getTransaction().commit();
    	
		results = em
		.createQuery(
				"SELECT c FROM Curriculum c",
				Curriculum.class).getResultList();
//		
		if(results.isEmpty()) {
			System.out.println("liste results vide");
		}else {
			System.out.println(results.get(0).getId());
		}
		
		em.close();
		//System.out.println(results);
//    	
//        em.getTransaction().begin();
//        em.getTransaction().commit();
    	
//    	List<Meeting> results;
//		final TypedQuery<Meeting> query;
//		query = em.createQuery("SELECT e FROM Meeting e "
//				+ "ORDER BY e.startDate DESC ", Meeting.class);
//		results = query.getResultList();
    	
    	
    	
    }
	

}
