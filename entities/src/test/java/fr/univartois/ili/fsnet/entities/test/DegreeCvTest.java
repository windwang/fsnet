package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateDegreeCV;
import fr.univartois.ili.fsnet.entities.DegreeCV;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;

public class DegreeCvTest {
	
	 @Test
	 public void testSetAndGetByMethodId() {
	    	DegreeCV d = new DegreeCV();
	    	d.setId(11);
	    	assertEquals(11, d.getId());
	    }
	 
	 @Test
	 public void testSetAndGetByMethodStudiesLevel() {
	    	DegreeCV d = new DegreeCV();
	    	d.setStudiesLevel("bad level");
	    	assertEquals("bad level", d.getStudiesLevel());
	    }
	 
	 @Test
	 public void testSetAndGetByMethodDomain() {
	    	DegreeCV d = new DegreeCV();
	    	d.setDomain("domaine test");
	    	assertEquals("domaine test", d.getDomain());
	    }
	 
	 @Test
	 public void testSetAndGetByMethodEstablishmentCV() {
	    	DegreeCV d = new DegreeCV();
	    	EstablishmentCV ets = new EstablishmentCV();
	    	d.setEts(ets);
	    	assertEquals(ets, d.getEts());
	    }
	 
	 @Test
	 public void testSetAndGetByMethodAssociationDateDegreeCV() {
	    	DegreeCV d = new DegreeCV();
	    	AssociationDateDegreeCV addcv = new AssociationDateDegreeCV();
	    	
	    	List<AssociationDateDegreeCV> l = new ArrayList<>();
	    	l.add(addcv);
	    	
	    	d.setAssociationDateDegreeCV(l);
	    	assertEquals(l, d.getAssociationDateDegreeCV());   	
	    	
	    }
	 
	 
}
