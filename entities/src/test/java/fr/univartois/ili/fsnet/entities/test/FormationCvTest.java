package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.AssociationDateFormationCV;
import fr.univartois.ili.fsnet.entities.EstablishmentCV;
import fr.univartois.ili.fsnet.entities.FormationCV;


public class FormationCvTest {

	
	@Test
	public void testSetByMethodsAndGet() throws ParseException {
		final int id = 12;
		final String name = "toto";
		EstablishmentCV ets=new EstablishmentCV();
		FormationCV formation = new FormationCV();
		List<AssociationDateFormationCV> myCVs = new ArrayList<AssociationDateFormationCV>();

		formation.setId(id);
		formation.setName(name);
		formation.setEts(ets);
		formation.setAssociationDateFormationCV(myCVs);
		assertEquals(formation.getId(), id);
		assertEquals(formation.getName(), name);
		assertEquals(formation.getEts(), ets);
		assertEquals(formation.getAssociationDateFormationCV(), myCVs);
		

	}

}
