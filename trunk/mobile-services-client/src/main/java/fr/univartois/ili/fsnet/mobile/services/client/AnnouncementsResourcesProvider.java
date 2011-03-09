package fr.univartois.ili.fsnet.mobile.services.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import fr.univartois.ili.fsnet.mobile.services.model.RestAnnouncement;

public class AnnouncementsResourcesProvider {

private final String endpoint;
	
	private final String login;
	
	private final String password;
	
	private final int delay;
	
	private static final String LOGIN = "login";
	
	private static final String PASSWORD = "pwd";
	
	private static final String DELAY = "delay";
	
	public AnnouncementsResourcesProvider(String endpoint, String login,
			String password, int delay) {
		this.endpoint = endpoint;
		this.login = login;
		this.password = password;
		this.delay = delay;
	}

	public List<RestAnnouncement> newAnnouncements() {
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cc);

		WebResource webResource = client.resource(endpoint + "announcements/new");

		List<RestAnnouncement> announcements = webResource //
				.queryParam(LOGIN, login) //
				.queryParam(PASSWORD, password) //
				.queryParam(DELAY, ""+delay) //
				.accept(MediaType.APPLICATION_JSON) //
				.type(MediaType.APPLICATION_JSON)//
				.get(new GenericType<List<RestAnnouncement>>() {
				});
		return announcements;
	}

}
