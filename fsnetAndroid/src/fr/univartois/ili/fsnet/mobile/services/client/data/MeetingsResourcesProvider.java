package fr.univartois.ili.fsnet.mobile.services.client.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import fr.univartois.ili.fsnet.mobile.services.client.rest.RestMeetingList;

/**
 * 
 * @author alexandre thery
 *
 */
public class MeetingsResourcesProvider extends ResourcesProvider{

	public final static String URL_COMPLEMENT="meetings/new";
	public MeetingsResourcesProvider(InfoSettings info) {
		super(info);
	}

	public RestMeetingList newMeetings() {
		RestMeetingList result=null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			Map<String,String> params = new HashMap<String, String>();
			String url = endpoint+URL_COMPLEMENT+"?login={login}&pwd={pwd}&delay={delay}";
			params.put(LOGIN, login);
			params.put(PASSWORD, password);
			params.put(DELAY, ""+delay);
			result = restTemplate.getForObject(url, RestMeetingList.class,params);
		    return result;
			
		}catch (Exception e) {
				return null;
			}
	}
}
