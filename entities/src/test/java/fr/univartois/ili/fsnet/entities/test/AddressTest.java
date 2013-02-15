package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Address;

public class AddressTest {

	private static final String ADDRESS = "rue Jean Souvraz";
	private static final String CITY = "LENS";
	
	
	@Test
	public void testCreateEmptyAddress() {
		Address adr = new Address();
		assertNull(adr.getAddress());
		assertNull(adr.getCity());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullAddress() {
		new Address(null, CITY);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateWithNullCity() {
		new Address(ADDRESS, null);
	}
	
    @Test
    public void testSetByMethodsAndGet() {
    	Address adr = new Address();
    	adr.setAddress(ADDRESS);
    	adr.setCity(CITY);
    	assertEquals(ADDRESS, adr.getAddress());
    	assertEquals(CITY, adr.getCity());
    }
    
    @Test
    public void testSetByConstructorAndGet() {
    	Address adr = new Address(ADDRESS, CITY);
    	assertEquals(ADDRESS, adr.getAddress());
    	assertEquals(CITY, adr.getCity());
    }
    
    @Test
    public void testEqualsWithSameAddress() {
    	Address adr1 = new Address(ADDRESS, CITY);
    	Address adr2 = new Address(new String(ADDRESS), new String(CITY));
    	
    	// Same address & city
    	assertTrue(adr1.equals(adr2));
    	assertTrue(adr2.equals(adr1));
    	
    	// Same address & null city
    	adr1.setCity(null);
    	adr2.setCity(null);
    	assertTrue(adr1.equals(adr2));
    	assertTrue(adr2.equals(adr1));
    }  
    
    @Test
    public void testEqualsWithSameObject() {
    	Address adr = new Address(ADDRESS, CITY);
    	assertTrue(adr.equals(adr));
    }  
    
    
    @Test
    public void testEqualsWithDifferentAddressesOrCities() {
    	Address adr1 = new Address(ADDRESS, CITY);
    	Address adr2 = new Address("test", CITY);
    	Address adr3 = new Address(ADDRESS, "test");
    	
    	// Same city but not same address
    	assertFalse(adr1.equals(adr2));
    	assertFalse(adr2.equals(adr1));
    	
    	// Same address but not same city
    	assertFalse(adr1.equals(adr3));
    	assertFalse(adr3.equals(adr1));
    }  
    
    @Test
    public void testEqualsWithNullAddressOrCity() {
    	Address adr1 = new Address(ADDRESS, CITY);
    	Address adr2 = new Address();
    	adr2.setCity(CITY);
    	Address adr3 = new Address();
    	adr3.setAddress(ADDRESS);
    	Address adr4 = new Address();
    	
    	// With not set address
    	assertFalse(adr1.equals(adr2));
    	assertFalse(adr2.equals(adr1));
    	assertFalse(adr2.equals(adr4));
    	assertFalse(adr4.equals(adr2));
    	
    	// With not set city
    	assertFalse(adr1.equals(adr3));
    	assertFalse(adr3.equals(adr1));
    	assertFalse(adr3.equals(adr4));
    	assertFalse(adr4.equals(adr3));
    	
    	// Null null object
    	assertFalse(adr1.equals(null));
    	
    	// Other tests of difference
    	assertFalse(adr1.equals(adr4)) ;
    	assertFalse(adr2.equals(adr3)) ;
    	assertFalse(adr3.equals(adr2)) ;
    	assertFalse(adr4.equals(adr1)) ;
    }  
    

    
    @Test
    public void testEqualsWithDifferenteObjet() {
    	Address adr1 = new Address(ADDRESS, CITY);
    	assertFalse(adr1.equals(new String("test")));
    }  
    
    @Test
    public void testHashCodeWithEqualsAddresses() {
    	Address adr1 = new Address(ADDRESS, CITY);
    	Address adr2 = new Address(new String(ADDRESS), new String(CITY));
    	assertEquals(adr1.hashCode(), adr2.hashCode());
    }  
    
    @Test
    public void testHashCodeWithNull() {
    	Address adr1 = new Address();
    	Address adr2 = new Address();
    	assertEquals(adr1.hashCode(), adr2.hashCode());
    }  
}
