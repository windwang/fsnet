package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/*
 * Author : Yoann VASSEUR
 * 
 * Class to manage a cv
 */

@Entity
public class Curriculum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne
	private MemberCV member;

	@ManyToMany
	private List<FormationCV> myFormations = new ArrayList<FormationCV>();

	@ManyToMany
	private List<HobbiesCV> hobs = new ArrayList<HobbiesCV>();

	@OneToMany
	private List<TrainingCV> trains = new ArrayList<TrainingCV>();

	@ManyToMany
	private List<DegreeCV> degs = new ArrayList<DegreeCV>();

	public Curriculum() {

	}

	/**
	 * @return the member
	 */
	public MemberCV getMember() {
		return member;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(MemberCV member) {
		this.member = member;
	}

	/**
	 * @return the myFormation
	 */
	public List<FormationCV> getMyFormations() {
		return myFormations;
	}

	/**
	 * @param myFormation
	 *            the myFormation to set
	 */
	public void setMyFormations(List<FormationCV> myFormation) {
		this.myFormations = myFormation;
	}

	/**
	 * @return the hob
	 */
	public List<HobbiesCV> getHobs() {
		return hobs;
	}

	/**
	 * @param hob
	 *            the hobbies to set
	 */
	public void setHobs(List<HobbiesCV> hob) {
		this.hobs = hob;
	}

	/**
	 * @return the training
	 */
	public List<TrainingCV> getTrains() {
		return trains;
	}

	/**
	 * @param train
	 *            the train to set
	 */
	public void setTrains(List<TrainingCV> train) {
		this.trains = train;
	}

	/**
	 * @return all the degree
	 */
	public List<DegreeCV> getDegs() {
		return degs;
	}

	/**
	 * @param deg
	 *            the deg to set
	 */
	public void setDegs(List<DegreeCV> deg) {
		this.degs = deg;
	}

}
