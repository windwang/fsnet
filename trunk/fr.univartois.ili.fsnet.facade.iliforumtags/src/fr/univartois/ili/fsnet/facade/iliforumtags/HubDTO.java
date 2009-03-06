package fr.univartois.ili.fsnet.facade.iliforumtags;

import java.util.List;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

public class HubDTO {
	public HubDTO(Hub hub) {
		this.hub = hub;
		updateInfo();
	}

	private Hub hub;

	private int nbTopic;

	private int nbMessage;

	private Message lastMessage;

	public Hub getHub() {
		return hub;
	}

	public void setHub(Hub hub) {
		this.hub = hub;
	}

	public int getNbTopic() {
		return nbTopic;
	}

	public void setNbTopic(int nbTopic) {
		this.nbTopic = nbTopic;
	}

	public int getNbMessage() {
		return nbMessage;
	}

	public void setNbMessage(int nbMessage) {
		this.nbMessage = nbMessage;
	}

	private void updateInfo() {
		List<Message> lMessage = IliForumFacade.getInstance().getListMessageByHub(hub);
		this.nbTopic = hub.getLesTopics().size();

		this.nbMessage = lMessage.size();

		if (!lMessage.isEmpty())
			this.lastMessage = lMessage.get(lMessage.size() - 1);
	}

	public Message getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(Message lastMessage) {
		this.lastMessage = lastMessage;
	}

}
