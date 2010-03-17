package fr.univartois.ili.fsnet.chat.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.RootPanel;

public class ChatControler {
	
	private Map<Integer, LoggedUser> users = new HashMap<Integer, LoggedUser>();
	
	public void refreshUsers(Map<Integer, String> newUsers) {
		for (Map.Entry<Integer, LoggedUser> entry : users.entrySet()) {
			if (!newUsers.containsKey(entry.getKey())) {
				entry.getValue().getUserMiniature().removeFromParent();
				users.remove(entry.getKey());
			}
		}
		for (Map.Entry<Integer, String> entry : newUsers.entrySet()) {
			if (!users.containsKey(entry.getKey())) {
				LoggedUser newUser = new LoggedUser(entry.getKey(), entry.getValue());
				users.put(entry.getKey(), newUser);
				RootPanel.get("loggedUsers").add(newUser.getUserMiniature());
			}
		}
	}

}
