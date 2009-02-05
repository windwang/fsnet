package fr.univartois.ili.fsnet.facade.jforumtags.entities;

public class Message {

	String text;
	String auteur;
	String postTime;
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getText() {
		return text;
	}
	
}
