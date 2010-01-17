package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class Message.
 * 
 */
@Entity
public class Message {

    /**
     * The identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /**
     * The content of the message.
     */
    private String contenu;
    /**
     * The date of creation of the message.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateMessage;
    /**
     * The creator of the message.
     */
    @ManyToOne
    private EntiteSociale propMsg;
    /**
     * The topic in which the message appears.
     */
    @ManyToOne
    private Topic topic;

    /**
     * Constructor of the class Message.
     */
    public Message() {
    }

    /**
     * Constructor of the class Message.
     *
     * @param contenu
     * @param dateMessage
     * @param propMsg
     * @param topic
     */
    public Message(String contenu, Date dateMessage, EntiteSociale propMsg,
            Topic topic) {
        this.contenu = contenu;
        this.dateMessage = dateMessage;
        this.propMsg = propMsg;
        this.topic = topic;
    }

    /**
     *
     * @return the identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gives an identifier to the Message.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the content of the message.
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Gives a content of the message.
     *
     * @param contenu
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     *
     * @return the date of creation of the message.
     */
    public Date getDateMessage() {
        return dateMessage;
    }

    /**
     * Gives a date of creation to the message.
     *
     * @param dateMessage
     */
    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    /**
     *
     * @return the creator of the message.
     */
    public EntiteSociale getPropMsg() {
        return propMsg;
    }

    /**
     * Gives a creator to the message.
     *
     * @param propMsg
     */
    public void setPropMsg(EntiteSociale propMsg) {
        this.propMsg = propMsg;
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
