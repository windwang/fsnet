package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TrainingCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private long id;

	private String name;
	private String speciality;

	@ManyToOne
	private List<AssociationDateTrainingCV> myCVs = new ArrayList<AssociationDateTrainingCV>();

	// Pensez aux dates debut et fin :)

	@OneToMany
	private EstablishmentCV myEst;

	public TrainingCV() {

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
	 * @return the speciality
	 */
	public String getSpeciality() {
		return speciality;
	}

	/**
	 * @param speciality
	 *            the speciality to set
	 */
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	/**
	 * @return the myEst
	 */
	public EstablishmentCV getMyEst() {
		return myEst;
	}

	/**
	 * @param myEst
	 *            the myEst to set
	 */
	public void setMyEst(EstablishmentCV myEst) {
		this.myEst = myEst;
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
	public List<AssociationDateTrainingCV> getAssociationDateTrainingCV() {
		return myCVs;
	}

	/**
	 * @param myCVs
	 *            the myCVs to set
	 */
	public void setAssociationDateTrainingCV(
			List<AssociationDateTrainingCV> myCVs) {
		this.myCVs = myCVs;
	}

}
