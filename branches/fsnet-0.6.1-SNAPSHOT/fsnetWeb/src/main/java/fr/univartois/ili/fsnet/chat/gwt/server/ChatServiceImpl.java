package fr.univartois.ili.fsnet.chat.gwt.server;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.univartois.ili.fsnet.chat.gwt.client.ChatService;
import fr.univartois.ili.fsnet.core.LoggedUsersContainer;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService {
	
	public Map<Integer, String> getLoggedUsers() {
		return new HashMap<Integer, String>(LoggedUsersContainer.getInstance().getUsers());
	}
}
