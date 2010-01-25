package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public abstract class TopicMessage extends Message implements Serializable {

    /**
     * The topic in which the message appears.
     */
    @ManyToOne
    private Topic topic;

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
