package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;



import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionRole;
import fr.univartois.ili.fsnet.entities.InteractionRole.RoleName;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class InteractionRoleTest {
	
	private InteractionRole ir = null;
	private SocialEntity se = new SocialEntity();
	private Interaction i = new Interaction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	};
	

	@Test
	public void testConstructorWithSetAndGetByMethodRolename() {
		ir = new InteractionRole(se, i);
		RoleName rolename = RoleName.DECISION_MAKER;
		
		ir.setRolename(rolename);
		assertEquals(rolename, ir.getRolename());
	}
	
	@Test
	public void testSetAndGetByMethodInteraction() {
		ir = new InteractionRole();
		
		ir.setInteraction(i);
		assertEquals(i, ir.getInteraction());		
	}
	
	
	@Test
	public void testSetAndGetByMethodRole() {
		ir = new InteractionRole();		
		RoleName role = RoleName.SUBSCRIBER;
		
		ir.setRole(role);
		assertEquals(role, ir.getRole());
	}
	
	
	@Test
	public void testSetAndGetByMethodSocialEntity() {
		ir = new InteractionRole();
		
		ir.setSocialEntity(se);
		assertEquals(se, ir.getSocialEntity());
	}
	
}
