package fr.univartois.ili.fsnet.chat.gwt.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.univartois.ili.fsnet.chat.gwt.shared.actions.ChatAction;

public interface ChatServiceAsync {

	void getLoggedUsers(AsyncCallback<Map<Integer, String>> callback);

	void openConversation(int userId, AsyncCallback<Void> callback);
	
	void closeConversation(int userId, AsyncCallback<Void> callback);

	void sendMessage(int userId, String message, AsyncCallback<Void> callback);

	void fetchChatActions(AsyncCallback<List<ChatAction>> callback); 

}
