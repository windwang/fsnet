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
public class DegreeCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private int id;

	private int PostBacValue;
	private String grade;

	@ManyToMany(mappedBy = "degs")
	private List<Curriculum> myCVs = new ArrayList<Curriculum>();

	@OneToOne
	private EstablishmentCV ets;

	public DegreeCV() {

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
	 * @return the postBacValue
	 */
	public int getPostBacValue() {
		return PostBacValue;
	}

	/**
	 * @param postBacValue
	 *            the postBacValue to set
	 */
	public void setPostBacValue(int postBacValue) {
		PostBacValue = postBacValue;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
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

	/**
	 * @return the myCVs
	 */
	public List<Curriculum> getMyCVs() {
		return myCVs;
	}

	/**
	 * @param myCVs the myCVs to set
	 */
	public void setMyCVs(List<Curriculum> myCVs) {
		this.myCVs = myCVs;
	}

}
