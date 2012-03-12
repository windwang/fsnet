package fr.univartois.ili.fsnet.facade.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.facade.CvFacade;

public class CvTest {
	
	private  EntityManager em;
    private CvFacade cf;
	
	    
    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        cf = new CvFacade(em);
        
    }
    
    
	    @Test
	    public void TestCV(){
	    	
            em.getTransaction().begin();
			
	    	//List<Meeting> result=cf.listAllCv();
	    	//System.out.println(result.get(0).getAddress());
			
			em.close();
			
	    	
	    }

}
