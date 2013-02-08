package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.EstablishmentCV;


public class EstablishmentCvTest {



	@Test
	public void testSetByMethodsAndGet() throws ParseException {
		final long id=12;
		final String name = "toto";
		final String town = "Lens";
		final String land = "france";
		
		EstablishmentCV cv1 = new EstablishmentCV();
		cv1.setName(name);
		cv1.setTown(town);
		cv1.setLand(land);
		cv1.setId(id);
		
		assertEquals(cv1.getId(), id);
		assertEquals(cv1.getName(), name);
		assertEquals(cv1.getTown(), town);
		assertEquals(cv1.getLand(),land);
		
	}
}
