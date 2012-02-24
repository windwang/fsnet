package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostRemove;

/**
 * 
 * The class Topic.
 * 
 */
@Entity
public class Topic extends Interaction {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * The hub in which the topic appears.
     */
    @ManyToOne
    private Hub hub;
    /**
     * the list of messages that the topic contains.
     */
    @OneToMany(mappedBy = "topic", cascade = {CascadeType.ALL})
    private List<TopicMessage> messages;

    /**
     * Constructor of the class Topic.
     */
    public Topic() {
    }

    public Topic(Hub hub, SocialEntity creator, String title) {
        super(creator, title);
        if (hub == null || creator == null || title == null) {
            throw new IllegalArgumentException();
        }
        this.messages = new ArrayList<TopicMessage>();
        this.hub = hub;
    }

    /**
     *
     * @return the list of messages that the topic contains.
     */
    public List<TopicMessage> getMessages() {
        return messages;
    }

    /**
     * Gives a list of messages to the topic.
     *
     * @param messages
     */
    public void setMessages(List<TopicMessage> messages) {
        this.messages = messages;
    }

    /**
     *
     * @return the hub in which the topic appears.
     */
    public Hub getHub() {
        return hub;
    }

    /**
     * Gives a hub to the topic.
     *
     * @param hub
     */
    public void setHub(Hub hub) {
        this.hub = hub;
    }
    
    @PostRemove
	public void onTopicRemove() {
    	setHub(null);
    }
}
