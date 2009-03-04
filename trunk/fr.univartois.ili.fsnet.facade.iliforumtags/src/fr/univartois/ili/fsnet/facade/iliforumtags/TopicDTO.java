package fr.univartois.ili.fsnet.facade.iliforumtags;

import fr.univartois.ili.fsnet.entities.Topic;

public class TopicDTO {

	private Topic topic;

	private int nbMessage;

	public TopicDTO(Topic top) {
		this.topic = top;

		update();
	}

	private void update() {
		this.nbMessage = topic.getLesMessages().size();
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
		update();
	}

	public int getNbMessage() {
		update();
		return nbMessage;
	}

	public void setNbMessage(int nbMessage) {
		this.nbMessage = nbMessage;
		update();
	}

}
