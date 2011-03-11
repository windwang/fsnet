package fr.univartois.ili.fsnet.mobile.services.client.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import fr.univartois.ili.fsnet.mobile.services.client.rest.RestAnnouncementList;

/**
 * 
 * @author alexandre thery
 *
 */
public class AnnouncementsResourcesProvider extends ResourcesProvider{

	public final static String URL_COMPLEMENT="announcements/new";
	public AnnouncementsResourcesProvider(InfoSettings info) {
		super(info);
	}


	public RestAnnouncementList newAnnouncements() {
		RestAnnouncementList result=null;
		try{
			RestTemplate restTemplate = new RestTemplate();
			Map<String,String> params = new HashMap<String, String>();
			String url = endpoint+URL_COMPLEMENT+"?login={login}&pwd={pwd}&delay={delay}";
			params.put(LOGIN, login);
			params.put(PASSWORD, password);
			params.put(DELAY, delay+"");
			result = restTemplate.getForObject(url, RestAnnouncementList.class,params);
		    return result;
			
		}catch (Exception e) {
				return null;
			}
	}

}
