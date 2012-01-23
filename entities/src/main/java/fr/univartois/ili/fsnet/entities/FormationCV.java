package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/*
 * Author : Yoann VASSEUR
 * 
 *
 */
@Entity
public class FormationCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private int id;

	private String name;

	@ManyToMany(mappedBy = "myFormations")
	private List<Curriculum> myCVs = new ArrayList<Curriculum>();

	// Pensez a la durée !
	@OneToOne
	private EstablishmentCV ets;

	public FormationCV() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ets
	 */
	public EstablishmentCV getEts() {
		return ets;
	}

	/**
	 * @param ets
	 *            the ets to set
	 */
	public void setEts(EstablishmentCV ets) {
		this.ets = ets;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
