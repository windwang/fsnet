package fr.univartois.ili.fsnet.chat.gwt.client;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ChatServiceAsync {

	public void getLoggedUsers(AsyncCallback<Map<Integer, String>> callback);
	
}
