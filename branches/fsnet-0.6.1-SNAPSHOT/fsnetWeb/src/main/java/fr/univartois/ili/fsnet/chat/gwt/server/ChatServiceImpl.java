package fr.univartois.ili.fsnet.chat.gwt.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.univartois.ili.fsnet.chat.gwt.client.ChatService;
import fr.univartois.ili.fsnet.chat.gwt.shared.actions.ChatAction;
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

	@Override
	public void closeConversation(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ChatAction> fetchChatActions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openConversation(int userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(int userId, String message) {
		// TODO Auto-generated method stub
		
	}
}
