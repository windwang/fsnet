package fr.univartois.ili.fsnet.mobile.services.model;

import java.util.List;

public class RestMessagesList {

	private List<RestPrivateMessage> messages;
	
	public RestMessagesList(){
	}
	
	public RestMessagesList(List<RestPrivateMessage> messages){
		this.messages = messages;
	}

	public void setMessages(List<RestPrivateMessage> messages) {
		this.messages = messages;
	}

	public List<RestPrivateMessage> getMessages() {
		return messages;
	}
	
	
}
