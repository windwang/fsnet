package fr.univartois.ili.fsnet.chat.gwt.client;

import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Chat implements EntryPoint {

	private final ChatServiceAsync chatService = GWT
			.create(ChatService.class);

	private final ChatControler controler = new ChatControler();
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Timer timer = new Timer() {
			
			@Override
			public void run() {
				chatService.getLoggedUsers(new AsyncCallback<Map<Integer,String>>() {
					
					@Override
					public void onSuccess(Map<Integer, String> result) {
						controler.refreshUsers(result);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						GWT.log("erreur de communication", caught);
						cancel();
					}
				});
			}
		};
		GWT.log("schedullleee");
		timer.scheduleRepeating(1500);
	}
}
