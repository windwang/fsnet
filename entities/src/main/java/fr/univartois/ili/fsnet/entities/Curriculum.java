package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@OneToOne
	private MemberCV member;

	@OneToMany (mappedBy = "idCurriculum")
	private List<AssociationDateFormationCV> myFormations = new ArrayList<AssociationDateFormationCV>();

	@ManyToMany
	private List<HobbiesCV> hobs = new ArrayList<HobbiesCV>();

	@OneToMany (mappedBy="curriculum")
	private List<AssociationDateTrainingCV> trains = new ArrayList<AssociationDateTrainingCV>();

	@OneToMany(mappedBy = "curriculum")
	private List<AssociationDateDegreeCV> degs = new ArrayList<AssociationDateDegreeCV>();

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
	public List<AssociationDateFormationCV> getMyFormations() {
		return myFormations;
	}

	/**
	 * @param myFormation
	 *            the myFormation to set
	 */
	public void setMyFormations(List<AssociationDateFormationCV> myFormation) {
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
	public List<AssociationDateTrainingCV> getTrains() {
		return trains;
	}

	/**
	 * @param train
	 *            the train to set
	 */
	public void setTrains(List<AssociationDateTrainingCV> train) {
		this.trains = train;
	}

	/**
	 * @return all the degree
	 */
	public List<AssociationDateDegreeCV> getDegs() {
		return degs;
	}

	/**
	 * @param deg
	 *            the deg to set
	 */
	public void setDegs(List<AssociationDateDegreeCV> deg) {
		this.degs = deg;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

}
