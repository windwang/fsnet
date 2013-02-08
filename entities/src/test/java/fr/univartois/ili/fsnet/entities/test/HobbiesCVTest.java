package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.HobbiesCV;

public class HobbiesCVTest {

	@Test
	public void testSetByMethodsAndGet() throws ParseException {
		final int id = 12;
		final String name = "toto";
		final String note= "note";
		List<Curriculum> myCVs=new ArrayList<Curriculum>();
		HobbiesCV hobbie=new HobbiesCV();
		hobbie.setId(id);
		hobbie.setName(name);
		hobbie.setNote(note);
		hobbie.setMyCVs(myCVs);
		assertEquals(hobbie.getId(), id);
		assertEquals(hobbie.getName(), name);
		assertEquals(hobbie.getNote(),note);
		assertEquals(hobbie.getMyCVs(),myCVs);
		

	}
}
