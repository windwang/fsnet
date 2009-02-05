package fr.univartois.ili.fsnet.facade.jforumtags.entities;


import java.util.*;

public class Topic {
	String nom;
	List messages;
	
	public Topic() {
		this.messages=new ArrayList<Message>();
	}
	
	public void setMessage(Message m){
		this.messages.add(m);
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getNom() {
		return nom;
	}
	
	public List getMessages() {
		return messages;
	}
}
