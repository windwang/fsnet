package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	private String contenu;
	private String dateMessage;
	private EntiteSociale qui;

	public Message(String contenu, String dateMessage, EntiteSociale qui) {
		this.contenu = contenu;
		this.dateMessage = dateMessage;
		this.qui = qui;
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

	public String getDateMessage() {
		return dateMessage;
	}

	public void setDateMessage(String dateMessage) {
		this.dateMessage = dateMessage;
	}

	public EntiteSociale getQui() {
		return qui;
	}

	public void setQui(EntiteSociale qui) {
		this.qui = qui;
	}

}
