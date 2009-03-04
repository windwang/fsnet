package fr.univartois.ili.fsnet.facade.iliforumtags;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Topic;

public class HubDTO {
	public HubDTO(Hub hub) {
		this.hub = hub;
		updateInfo();
	}

	private Hub hub;

	private int nbTopic;

	private int nbMessage;

	public Hub getHub() {
		return hub;
	}

	public void setHub(Hub hub) {
		this.hub = hub;
		updateInfo();
	}

	public int getNbTopic() {
		updateInfo();
		return nbTopic;
	}

	public void setNbTopic(int nbTopic) {
		this.nbTopic = nbTopic;
		updateInfo();
	}

	public int getNbMessage() {
		updateInfo();
		return nbMessage;
	}

	public void setNbMessage(int nbMessage) {
		this.nbMessage = nbMessage;
		updateInfo();
	}

	private void updateInfo() {
		this.nbTopic = hub.getLesTopics().size();

		int nbMess = 0;
		for (Topic t : hub.getLesTopics()) {
			nbMess += t.getLesMessages().size();
		}
		this.nbMessage = nbMess;
	}

}
