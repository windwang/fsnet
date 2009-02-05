package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;

@Entity
public class Communaute extends Interaction{

	private String nomCommunaute;

	
	public Communaute() {
	}

	public Communaute(String nomCommunaute) {
		super();
		this.nomCommunaute = nomCommunaute;
	}

	public String getNomCommunaute() {
		return nomCommunaute;
	}

	public void setNomCommunaute(String nomCommunaute) {
		this.nomCommunaute = nomCommunaute;
	}
	
	
}
