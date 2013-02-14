package fr.univartois.ili.fsnet.integration.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import fr.univartois.ili.fsnet.integration.template.WebTemplateTests;

public class TestIndex extends WebTemplateTests {

	/**
	 * Test referinf ticket 347
	 */
	@Test
	public void testIndexBehaviour() {
		// VERY IMPORTANT TO DO !
				tester.setBaseUrl(WEB_CONTEXT);
				tester.beginAt("/");
				// Begining of tests
				assertNotNull(tester.getPageSource());
				tester.assertElementPresentByXPath("//h2[@id='login-title']");
				tester.assertElementPresentByXPath("//form[@class='form-signin' and @action='Authenticate' and @method='post']");
				tester.setTextField("memberMail", "admin@fsnet.com");
				tester.setTextField("memberPass", "admin");
				tester.submit();
				//Must be on index
				tester.assertElementPresentByXPath("//img[@alt='GroupLogo']");
				tester.assertElementPresentByXPath("//div[@class='cadreDivMenuTop' and contains(h2/text() , 'Accueil')]");
				//Click somewhere
				tester.clickLinkWithText("Visites");
				tester.assertElementPresentByXPath("//div[@class='cadreDivMenuTop' and contains(h2/text() , 'Visites')]");
				//Test the behaviour
				tester.gotoPage("/");
				//Must be on index
				tester.assertElementPresentByXPath("//img[@alt='GroupLogo']");
				tester.assertElementPresentByXPath("//div[@class='cadreDivMenuTop' and contains(h2/text() , 'Accueil')]");
	}

}
