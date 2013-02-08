package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.MemberCV;

/**
 * 
 * @author Mouloud Goutal <mouloud.goutal@gmail.com>
 * 
 */
public class MemberCVTest {



	@Test
	public void testCreateEmptyMemberCV() {
		MemberCV m = new MemberCV();
		assertEquals(m.getId(), 0);
		assertNull(m.getAdress());
		assertNull(m.getBirthDate());
		assertNull(m.getFirstName());
		assertNull(m.getLanguages());
		assertNull(m.getMail());
		assertNull(m.getNumberPhone());
		assertEquals(m.getPostCode(), 0);
		assertNull(m.getSex());
		assertNull(m.getSituationFamilly());
		assertNull(m.getSurname());
		assertNull(m.getTown());
	}

	@Test
	public void testSetByMethodsAndGet() {
		MemberCV m = new MemberCV();
		Map<String, String> languages = new HashMap<String, String>();
		languages.put("en", "english");
		languages.put("fr", "french");
		m.setId(2);
		m.setSurname("coucou");
		m.setFirstName("cou");
		m.setMail("coucou@coucou.co");
		m.setAdress("25 rue de Paris");
		m.setTown("Douai");
		m.setBirthDate(new Date(15000000));
		m.setNumberPhone("0610101010");
		m.setSex("M");
		m.setPostCode(59500);
		m.setSituationFamilly("celibataire");
		m.setLanguages(languages);

		assertEquals(m.getId(), 2);
		assertEquals(m.getSurname(), "coucou");
		assertEquals(m.getFirstName(), "cou");
		assertEquals(m.getMail(), "coucou@coucou.co");
		assertEquals(m.getAdress(), "25 rue de Paris");
		assertEquals(m.getTown(), "Douai");
		assertEquals(m.getBirthDate(), new Date(15000000));
		assertEquals(m.getNumberPhone(), "0610101010");
		assertEquals(m.getSex(), "M");
		assertEquals(m.getPostCode(), 59500);
		assertEquals(m.getSituationFamilly(), "celibataire");
		assertEquals(m.getLanguages(), languages);
	}

}
