package fr.univartois.ili.fsnet.mobile.services.client.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import fr.univartois.ili.fsnet.mobile.services.client.rest.RestMessagesList;


/**
 * 
 * @author william vermersch
 * @author alexandre thery
 */
public class MessagesResourcesProvider extends ResourcesProvider{

	public final static String URL_COMPLEMENT="messages/unread";
	public MessagesResourcesProvider(InfoSettings info)
	{
		super(info);
	}
	public RestMessagesList unreadPrivateMessages() {
		RestMessagesList result=null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			Map<String,String> params = new HashMap<String, String>();
			String url = endpoint+URL_COMPLEMENT+"?login={login}&pwd={pwd}";
			params.put(LOGIN, login);
			params.put(PASSWORD, password);
			result = restTemplate.getForObject(url, RestMessagesList.class,params);
		    return result;
			
		}catch (Exception e) {
			return null;
		}
	}

}
