package fr.univartois.ili.fsnet.mobile.services.client.data;

/**
 * 
 * @author alexandre thery
 *
 */
public class ResourcesProvider {

	protected String endpoint;
	
	protected String login;
	
	protected String password;
	
	protected int delay;
	
	protected static final String LOGIN = "login";
	
	protected static final String PASSWORD = "pwd";
	
	protected static final String DELAY = "delay";
	
	public ResourcesProvider(InfoSettings info)
	{
		String url=info.getServerUrl();
		String domain = url.substring(0, url.indexOf("/", 8))+"/webservice/jaxrs/";
		this.endpoint = domain;
		this.login = info.getLogin();
		this.password = info.getPassword();
		this.delay = info.getMinutes();
	}

}
