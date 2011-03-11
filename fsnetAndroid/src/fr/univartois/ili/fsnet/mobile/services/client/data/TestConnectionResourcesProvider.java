package fr.univartois.ili.fsnet.mobile.services.client.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author alexandre thery
 * @author william vermersch
 */
public class TestConnectionResourcesProvider extends ResourcesProvider{

	public final static String URL_COMPLEMENT="state/isuser";
	public TestConnectionResourcesProvider(InfoSettings info) {
		super(info);
	}

	public boolean isUser() {
		boolean result=false;
		try{
			RestTemplate restTemplate = new RestTemplate();
			Map<String,String> params = new HashMap<String, String>();
			String url = endpoint+URL_COMPLEMENT+"?login={login}&pwd={pwd}";
			params.put(LOGIN, login);
			params.put(PASSWORD, password);
			result = restTemplate.getForObject(url, boolean.class,params);
		    return result;
			
		}catch (Exception e) {
			return false;
		}
	}
}
