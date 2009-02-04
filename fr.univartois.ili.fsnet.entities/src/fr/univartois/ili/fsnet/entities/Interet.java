package fr.univartois.ili.fsnet.entities;

import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

public class Interet {

	@OneToOne(mappedBy="groupeDinteret")
	private GroupeDInteret ungroupe;
	@ManyToMany(mappedBy="entite")
	private List<EntiteSociale> lesEntites;
	private String nomInteret;
	
	public Interet(GroupeDInteret ungroupe, List<EntiteSociale> lesEntites, String nomInteret) {
		this.ungroupe = ungroupe;
		this.lesEntites = lesEntites;
		this.nomInteret = nomInteret;
	}


	public GroupeDInteret getUngroupe() {
		return ungroupe;
	}


	public void setUngroupe(GroupeDInteret ungroupe) {
		this.ungroupe = ungroupe;
	}


	public List<EntiteSociale> getLesEntites() {
		return lesEntites;
	}


	public void setLesEntites(List<EntiteSociale> lesEntites) {
		this.lesEntites = lesEntites;
	}


	public String getNomInteret() {
		return nomInteret;
	}


	public void setNomInteret(String nomInteret) {
		this.nomInteret = nomInteret;
	}
	
	
	
	
	
}
