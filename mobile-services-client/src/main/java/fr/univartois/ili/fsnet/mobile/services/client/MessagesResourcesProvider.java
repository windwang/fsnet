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

	public MessagesResourcesProvider(String endpoint, String login, String password) {
		this.endpoint = endpoint;
		this.login = login;
		this.password = password;
	}

	public List<RestPrivateMessage> unreadPrivateMessages(int delay) {
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cc);

		WebResource webResource = client.resource(endpoint + "messages/unread/"
				+ delay);

		List<RestPrivateMessage> messages = webResource //
				.queryParam("login", login) //
				.queryParam("pwd", password) //
				.accept(MediaType.APPLICATION_JSON) //
				.type(MediaType.APPLICATION_JSON)//
				.get(new GenericType<List<RestPrivateMessage>>() {
				});
		return messages;
	}
}
