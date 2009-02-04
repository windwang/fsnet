package fr.univartois.ili.fsnet.entities;

import javax.persistence.OneToOne;

public class GroupeDInteret extends Communaute {

	@OneToOne(mappedBy="info")
	private Information info;
	@OneToOne(mappedBy="interet")
	private Interet interet;
	
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
