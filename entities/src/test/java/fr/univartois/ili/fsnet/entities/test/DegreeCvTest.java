package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.DegreeCV;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;

public class DegreeCvTest {
	
	 @Test
	 public void testSetAndGetByMethodId() {
	    	DegreeCV adr = new DegreeCV();
	    	adr.setId(11);
	    	assertEquals(11, adr.getId());
	    }
	 
	 @Test
	 public void testSetAndGetByMethodStudiesLevel() {
	    	DegreeCV adr = new DegreeCV();
	    	adr.setStudiesLevel("bad level");
	    	assertEquals("bad level", adr.getStudiesLevel());
	    }
	 
	 @Test
	 public void testSetAndGetByMethodDomain() {
	    	DegreeCV adr = new DegreeCV();
	    	adr.setDomain("domaine test");
	    	assertEquals("domaine test", adr.getDomain());
	    }
	 
	 @Test
	 public void testSetAndGetByMethodEstablishmentCV() {
	    	DegreeCV adr = new DegreeCV();
	    	EstablishmentCV ets = new EstablishmentCV();
	    	adr.setEts(ets);
	    	assertEquals(ets, adr.getEts());
	    }
	 
	 
}
