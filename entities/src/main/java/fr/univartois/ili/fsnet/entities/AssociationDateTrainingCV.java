package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class AssociationDateTrainingCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private long id;

	private Date startDate;
	private Date endDate;

	@OneToMany(mappedBy = "myCVs")
	private TrainingCV training;

	@ManyToOne
	private Curriculum curriculum;

	public AssociationDateTrainingCV() {

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
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the idDegree
	 */
	public TrainingCV getIdTraining() {
		return training;
	}

	/**
	 * @param idDegree
	 *            the idDegree to set
	 */
	public void setTraining(TrainingCV idTraining) {
		this.training = idTraining;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the idCurriculum
	 */
	public Curriculum getCurriculum() {
		return curriculum;
	}

	/**
	 * @param idCurriculum
	 *            the idCurriculum to set
	 */
	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

}
