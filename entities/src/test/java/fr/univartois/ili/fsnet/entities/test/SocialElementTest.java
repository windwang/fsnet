package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialGroup;

public class SocialElementTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateEmptyGroup() {
		SocialElement socialElement = new SocialGroup();
		assertNull(socialElement.getGroup());
	}

    @Test
    public void testSetByMethodsAndGetId() {
    	SocialElement socialElement = new SocialGroup();
    	socialElement.setId(11); 
    	assertEquals(11, socialElement.getId());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSetGroupNull() {
    	new SocialElement(null) {

			private static final long serialVersionUID = 1L;

			@Override
			public void setIsEnabled(boolean isEnabled) {
			}
			
			@Override
			public boolean getIsEnabled() {
				return false;
			}
		};
    	
    }
    
    @Test
    public void testSetGroupNotNull() {
    	SocialGroup group = new SocialGroup();
    	new SocialElement(group) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setIsEnabled(boolean isEnabled) {
			}
			
			@Override
			public boolean getIsEnabled() {
				return false;
			}
		};
    	
    }
}
