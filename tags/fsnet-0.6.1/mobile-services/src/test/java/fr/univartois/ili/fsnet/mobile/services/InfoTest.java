package fr.univartois.ili.fsnet.mobile.services;
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
import fr.univartois.ili.fsnet.mobile.services.model.RestPrivateMessage;


public class InfoTest {
	
	private static final String ENDPOINT = "http://localhost:8080/webservice/jaxrs";
	
	private static final String LOGIN = "mat.boniface@gmail.com";
	
	private static final String PASSWORD = "password";

	@Test
	public void empty() {
		
	}
	
	//@Test
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

}
