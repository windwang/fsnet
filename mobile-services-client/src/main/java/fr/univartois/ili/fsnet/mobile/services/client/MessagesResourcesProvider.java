package fr.univartois.ili.fsnet.mobile.services.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import fr.univartois.ili.fsnet.mobile.services.model.RestPrivateMessage;

public class MessagesResourcesProvider {

	private final String endpoint;
	
	private final String login;
	
	private final String password;
	
	private static final String LOGIN = "login";
	
	private static final String PASSWORD = "pwd";
	

	public MessagesResourcesProvider(String endpoint, String login, String password) {
		this.endpoint = endpoint;
		this.login = login;
		this.password = password;
	}

	public List<RestPrivateMessage> unreadPrivateMessages() {
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cc);

		WebResource webResource = client.resource(endpoint + "messages/unread");

		List<RestPrivateMessage> messages = webResource //
				.queryParam(LOGIN, login) //
				.queryParam(PASSWORD, password) //
				.accept(MediaType.APPLICATION_JSON) //
				.type(MediaType.APPLICATION_JSON)//
				.get(new GenericType<List<RestPrivateMessage>>() {
				});
		return messages;
	}
}
