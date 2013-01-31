package fr.univartois.ili.fsnet.integration.template;

import static org.junit.Assert.*;

import java.util.Locale;

import net.sourceforge.jwebunit.junit.WebTester;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WebTemplateTests {

	private static final int TWO_MINUTES_TIMEOUT = 120000;
	public static final String HTTP_LOCALHOST_8080_WEB_TEST = "http://localhost:8888/";
	public static final String ADMIN_CONTEXT = HTTP_LOCALHOST_8080_WEB_TEST
			+ "admin/";
	public static final String WEB_CONTEXT = HTTP_LOCALHOST_8080_WEB_TEST
			+ "fsnetWeb/";
	public static final String WEBSERVICE_CONTEXT = "/webservice/";

	protected static WebTester tester;

	@Before
	public void prepare() {
		tester = new WebTester();
		tester.getTestContext().clearAuthorizations();
		tester.setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
		tester.getTestingEngine().setThrowExceptionOnScriptError(true);
		// FIXME : Problem with bootstrap or JWebUnit (bootstrap.js block the
		// execution while webtester has downloaded it). Set to false when
		// problem solve !
		tester.setScriptingEnabled(false);
		tester.getTestContext().addRequestHeader("Accept-Language", "fr");
		tester.getTestContext().addRequestHeader("Connection", "close");
		tester.getTestContext().setAuthorization("admin", "admin"); // Useful to directly access to admin section
		tester.getTestContext().setLocale(Locale.FRENCH);// Work with french version !
		tester.setTimeout(TWO_MINUTES_TIMEOUT);
	}

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
