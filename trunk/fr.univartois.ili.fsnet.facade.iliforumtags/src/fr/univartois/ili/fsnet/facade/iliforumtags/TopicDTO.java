package fr.univartois.ili.fsnet.facade.iliforumtags;

import java.util.List;

import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

public class TopicDTO {

    private Topic topic;

    private int nbMessage;

    private Message lastMessage;

    public Message getLastMessage() {
	return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
	this.lastMessage = lastMessage;
    }

    public TopicDTO(Topic top) {
	this.topic = top;
	update();

    }

    private void update() {
	IliForumFacade iff = new IliForumFacade();
	List<Message> lMessage = iff.getListMessageByTopic(topic);
	this.nbMessage = lMessage.size();
	if (!lMessage.isEmpty())
	    this.lastMessage = lMessage.get(lMessage.size() - 1);

    }

    public Topic getTopic() {
	return topic;
    }

    public void setTopic(Topic topic) {
	this.topic = topic;
    }

    public int getNbMessage() {
	return nbMessage;
    }

    public void setNbMessage(int nbMessage) {
	this.nbMessage = nbMessage;
    }

}
