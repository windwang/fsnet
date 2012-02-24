package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TopicMessage extends Message implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * The topic in which the message appears.
     */
    @ManyToOne
    private Topic topic;

    public TopicMessage() {
    }

    public TopicMessage(String body, SocialEntity from, Topic topic) {
        super(body, from);
        if (topic == null) {
            throw new IllegalArgumentException();
        }
        this.topic = topic;
    }

    /**
     *
     * @return the topic in which the message appears.
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     * Gives the topic in which the message appears.
     *
     * @param topic
     */
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
