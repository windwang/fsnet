package fr.univartois.ili.fsnet.chat.gwt.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import fr.univartois.ili.fsnet.chat.gwt.shared.actions.ChatAction;

public class ChatControler {

	private final Map<Integer, LoggedUser> users = new HashMap<Integer, LoggedUser>();

	private final ChatServiceAsync chatService = GWT.create(ChatService.class);

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
				newUser.getUserMiniature().addMiniatureClickedListener(onMiniatureClicked);
			}
		}
	}

	public void updateActions() {
		final ChatControler controller = this;
		chatService.fetchChatActions(new AsyncCallback<List<ChatAction>>() {
			
			@Override
			public void onSuccess(List<ChatAction> result) {
				for (ChatAction action : result) {
					action.execute(controller);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				GWT.log(caught.getMessage());
			}
		});
	}
	
	private MiniatureClickedListener onMiniatureClicked = new MiniatureClickedListener() {
		
		@Override
		public void onMiniatureClicked(UserPicture picture) {
			Integer userId = picture.getUserId();
			LoggedUser user = users.get(userId);
			final int left = picture.getAbsoluteLeft() + 10;
			final int top = picture.getAbsoluteTop() + 10;
			if (user != null) {
				user.getInfoPanel().show(left, top);
			} else {
				GWT.log("user : " + userId + " not found");
			}
		}
		
	};

}
