package fr.univartois.ili.fsnet.entities;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

public class Interet {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	@OneToOne(mappedBy = "interet")
	private GroupeDInteret ungroupe;
	@ManyToMany(mappedBy = "lesinterets")
	private List<EntiteSociale> lesEntites;
	private String nomInteret;

	public Interet(GroupeDInteret ungroupe, List<EntiteSociale> lesEntites,
			String nomInteret) {
		this.ungroupe = ungroupe;
		this.lesEntites = lesEntites;
		this.nomInteret = nomInteret;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
