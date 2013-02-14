package fr.univartois.ili.fsnet.integration.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import fr.univartois.ili.fsnet.integration.template.WebTemplateTests;

public class TestApplicationStarted extends WebTemplateTests {

	@Test
	public void testAdminWebappStarted() {
		// VERY IMPORTANT TO DO !
		tester.setBaseUrl(ADMIN_CONTEXT);
		tester.beginAt("/");
		// Begining of tests
		tester.assertElementPresentByXPath("//h2[@id='slogan']");
		tester.assertTextPresent("ADMINISTRATION");
	
	}

	@Test
	public void testFsnetWebWebappStarted() {
		// VERY IMPORTANT TO DO !
		tester.setBaseUrl(WEB_CONTEXT);
		tester.beginAt("/");
		// Begining of tests
		String content = tester.getPageSource();
		assertNotNull(content);
		tester.assertElementPresentByXPath("//h2[@id='login-title']");
		tester.assertElementPresentByXPath("//form[@class='form-signin' and @action='Authenticate' and @method='post']");
	}

	@Ignore
	public void testWebserviceWebappStarted() {
		// WebService isn't in the supported webapp !
		tester.beginAt(WEBSERVICE_CONTEXT);
	}
}
