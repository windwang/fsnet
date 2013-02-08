package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fr.univartois.ili.fsnet.entities.InteractionRolePK;

public class InteractionRolePKTest {

	@Test
	public void testSetByMethodsAndGet() {
		int socialEntity = 10;
		int interaction = 11;
		InteractionRolePK ir = new InteractionRolePK();
		ir.setSocialEntityId(socialEntity);
		ir.setInteractionId(interaction);

		assertEquals(socialEntity, ir.getSocialEntityId());
		assertEquals(interaction, ir.getInteractionId());
	}
}
