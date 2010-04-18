package fr.univartois.ili.fsnet.chat.gwt.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.univartois.ili.fsnet.chat.gwt.shared.actions.ChatAction;

/**
 * The client chat interface 
 */
@RemoteServiceRelativePath("chat")
public interface ChatService extends RemoteService {
		
	/**
	 * return a map that contains the list of logged in users.
	 * The key represents the user id.
	 * The value represents the user name.
	 * @return a map that contains logged in users
	 */
	Map<Integer, String> getLoggedUsers();
	
	/**
	 * Ask to open a new conversation with the given user.
	 * @param userId The user id of the user to open the conversation.
	 */
	void openConversation(int userId);
	
	/**
	 * ask to close conversation with the given user.
	 * @param userId The user id of the user to close the conversation.
	 */
	void closeConversation(int userId);
	
	/**
	 * Send a message to the given user
	 * @param userId The user to send a message
	 * @param message The message to send
	 */
	void sendMessage(int userId, String message);
	
	/**
	 * Return the lasts actions to do in the client side 
	 * @return the lasts actions to do in the client side
	 */
	List<ChatAction> fetchChatActions();
	
}
