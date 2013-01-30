package fr.univartois.ili.fsnet.integration.template;

import static org.junit.Assert.*;

import java.util.Locale;

import net.sourceforge.jwebunit.junit.WebTester;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WebTemplateTests {

	public static final String HTTP_LOCALHOST_8080_WEB_TEST = "http://localhost:8080/";
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
		//tester.setScriptingEnabled(false);
		tester.setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
		tester.setIgnoreFailingStatusCodes(true);
		tester.getTestContext().addRequestHeader("Accept-Language", "fr");
		tester.getTestContext().setLocale(Locale.FRENCH);// Work with french
															// version !
	}

	// @Ignore
	// public void testAdminWebappStarted() {
	// // set the identifier of admin
	// tester.getTestContext().setAuthorization("admin", "admin");
	// System.out
	// .println("444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444");
	// tester.setBaseUrl(ADMIN_CONTEXT);
	// tester.beginAt("");
	// System.out
	// .println("555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555");
	// }

	@Test
	public void testFsnetWebWebappStarted() {
		tester.setBaseUrl(WEB_CONTEXT);
		System.out
				.println("444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444");
		tester.beginAt("");
		System.out
				.println("555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555");
		System.out.println(tester.getTestContext().getBaseUrl().toString());
		System.out.println(tester);
		System.out.println(tester.getResponseHeaders());
		String content = tester.getPageSource();
		assertNotNull(content);
		// tester.assertElementPresentByXPath("//h2[@id='login-title']");
		// System.out
		// .println("333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
		// tester.assertElementPresentByXPath("//form[@class='form-signin' and action='Authenticate' and method='post']");
		// System.out
		// .println("############################################################################################################################################################################");
	}

	@Ignore
	public void testWebserviceWebappStarted() {
		tester.beginAt(WEBSERVICE_CONTEXT);
	}
}
