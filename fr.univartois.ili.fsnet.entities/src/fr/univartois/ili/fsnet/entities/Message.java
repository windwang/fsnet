package fr.univartois.ili.fsnet.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Message {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String contenu;
	private Date dateMessage;
	
	@ManyToOne
	private EntiteSociale propMsg;
	@ManyToOne
	private Topic topic;

	public Message(String contenu, Date dateMessage, EntiteSociale propMsg, Topic topic) {
		this.contenu = contenu;
		this.dateMessage = dateMessage;
		this.propMsg = propMsg;
		this.topic = topic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public Date getDateMessage() {
		return dateMessage;
	}

	public void setDateMessage(Date dateMessage) {
		this.dateMessage = dateMessage;
	}

	public EntiteSociale getPropMsg() {
		return propMsg;
	}

	public void setPropMsg(EntiteSociale propMsg) {
		this.propMsg = propMsg;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}


}
