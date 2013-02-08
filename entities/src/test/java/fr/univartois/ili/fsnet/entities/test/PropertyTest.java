package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Property;

public class PropertyTest {


	@Test
	public void testPersist() throws ParseException {
		
		final String key = "key";
		final String value = "value";
			
		Property prop = new Property();
		prop.setKey(key);
		prop.setValue(value);
		assertEquals(prop.getKey(),key);
		assertEquals(prop.getValue(),value);
	}
}
