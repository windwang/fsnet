package fr.univartois.ili.fsnet.facade.jforumtags.entities;

import java.util.*;

public class Hub {
	String nom;
	List topics;
	
	
	public Hub() {
		this.topics=new ArrayList<Topic>();
	}
	
	public void setTopic(Topic topic){
		topics.add(topic);
	}
	
	public List getTopics() {
		return topics;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNom() {
		return nom;
	}
}
