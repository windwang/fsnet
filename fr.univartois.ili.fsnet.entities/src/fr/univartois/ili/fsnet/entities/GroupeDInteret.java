package fr.univartois.ili.fsnet.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
@Entity
public class GroupeDInteret extends Communaute {

	@OneToOne
	private Information info;
	@OneToOne
	private Interet interet;
	
	
	
	public GroupeDInteret() {
	}

	public GroupeDInteret(String nomCommunaute, Information info,
			Interet interet) {
		super(nomCommunaute);
		this.info = info;
		this.interet = interet;
	}

	public Information getInfo() {
		return info;
	}

	public void setInfo(Information info) {
		this.info = info;
	}

	public Interet getInteret() {
		return interet;
	}

	public void setInteret(Interet interet) {
		this.interet = interet;
	}
	
	
	
	
	
}
