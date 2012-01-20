package fr.univartois.ili.fsnet.mobile.services.client.rest;

import java.util.List;
/**
 * 
 * @author william vermersch
 *
 */
public class RestMessagesList {

	private List<RestMessage> messages;
	
	public RestMessagesList(){
	}
	
	public RestMessagesList(List<RestMessage> messages){
		this.setMessages(messages);
	}

	public void setMessages(List<RestMessage> messages) {
		this.messages = messages;
	}

	public List<RestMessage> getMessages() {
		return messages;
	}
	public RestMessage get(int location)
	{
		return messages.get(location);
	}
	public int size()
	{
		return messages.size();
	}
	
}
