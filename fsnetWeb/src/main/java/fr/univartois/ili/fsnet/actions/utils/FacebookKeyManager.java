package fr.univartois.ili.fsnet.actions.utils;

import java.util.Properties;

import fr.univartois.ili.fsnet.commons.mail.FSNetConfiguration;

public class FacebookKeyManager {

	private FacebookKeyManager(){}
	
	public static String getKeyFacebook(){
		FSNetConfiguration conf = FSNetConfiguration.getInstance();
		Properties properties = conf.getFSNetConfiguration();
		String keyFacebook = (String) properties.get(FSNetConfiguration.KEY_FACEBOOK);
		
		return keyFacebook;
	}
	
}
