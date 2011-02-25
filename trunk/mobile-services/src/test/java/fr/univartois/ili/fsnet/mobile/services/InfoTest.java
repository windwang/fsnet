package fr.univartois.ili.fsnet.mobile.services;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import fr.univartois.ili.fsnet.mobile.services.model.AuthInfo;
import fr.univartois.ili.fsnet.mobile.services.model.RestAnnouncement;
import fr.univartois.ili.fsnet.mobile.services.model.RestPrivateMessage;


public class InfoTest {
	
	private static final String ENDPOINT = "http://localhost:8080/webservice/jaxrs";
	
	private static final String LOGIN = "wvermersch@gmail.com";
	
	private static final String PASSWORD = "blabla";

	@Test
	public void empty() {
		
	}
	
	@Test
	public void newMessages() {
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cc);
		
		WebResource webResource = client.resource(ENDPOINT + "/messages/unread");
		
		List<RestPrivateMessage> messages = webResource.accept(MediaType.APPLICATION_JSON) //
				.entity(new AuthInfo(LOGIN, PASSWORD), MediaType.APPLICATION_JSON)//
				.type(MediaType.APPLICATION_JSON)//
				.post(new GenericType<List<RestPrivateMessage>>(){});
		
		Assert.assertNotNull(messages);
	}

	@Test
	public void newMeetings() {
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cc);
		
		WebResource webResource = client.resource(ENDPOINT + "/meetings/new");
		
		List<RestAnnouncement> meetings = webResource.accept(MediaType.APPLICATION_JSON) //
				.entity(new AuthInfo(LOGIN, PASSWORD), MediaType.APPLICATION_JSON)//
				.type(MediaType.APPLICATION_JSON)//
				.post(new GenericType<List<RestAnnouncement>>(){});
		System.out.println("Nouveaux meetings :"+meetings.size());
		for(RestAnnouncement meeting : meetings){
			System.out.println("Titre meeting :"+meeting.getTitle() +"créé par "+meeting.getFrom());
		}
		Assert.assertNotNull(meetings);
	}
	
	@Test
	public void newAnnouncements() {
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(cc);
		
		WebResource webResource = client.resource(ENDPOINT + "/announcements/new");
		
		List<RestAnnouncement> meetings = webResource.accept(MediaType.APPLICATION_JSON) //
				.entity(new AuthInfo(LOGIN, PASSWORD), MediaType.APPLICATION_JSON)//
				.type(MediaType.APPLICATION_JSON)//
				.post(new GenericType<List<RestAnnouncement>>(){});
		System.out.println("Nouvelles annonces:"+meetings.size());
		for(RestAnnouncement meeting : meetings){
			System.out.println("Titre annonce :"+meeting.getTitle() +"créé par "+meeting.getFrom());
		}
		Assert.assertNotNull(meetings);
	}
}
