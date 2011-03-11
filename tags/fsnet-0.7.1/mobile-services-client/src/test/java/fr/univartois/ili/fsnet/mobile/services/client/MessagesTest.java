package fr.univartois.ili.fsnet.mobile.services.client;

import junit.framework.Assert;

import org.junit.Test;

import fr.univartois.ili.fsnet.mobile.services.model.RestMessagesList;
import fr.univartois.ili.fsnet.mobile.services.model.RestPrivateMessage;

public class MessagesTest {

	private static final String ENDPOINT = "http://localhost:8080/webservice/jaxrs/";

	private static final String LOGIN = "wvermersch@gmail.com";

	private static final String PASSWORD = "blabla";
	

	@Test
	public void empty() {

	}

	@Test
	public void newMessages() {
		MessagesResourcesProvider provider = new MessagesResourcesProvider( //
				ENDPOINT, //
				LOGIN, //
				PASSWORD);

		RestMessagesList messages = provider.unreadPrivateMessages();

		for(RestPrivateMessage mess : messages.getMessages()){
			System.out.println("Message : "+mess.getSubject());
		}
		Assert.assertNotNull(messages);
	}

}
