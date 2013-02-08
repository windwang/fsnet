package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AssociationDateDegreeCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;
	private Date startDate;
	private Date endDate;

	@ManyToOne
	private DegreeCV degree;

	@ManyToOne
	private Curriculum curriculum;

	public AssociationDateDegreeCV() {

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
	public DegreeCV getDegree() {
		return degree;
	}

	/**
	 * @param idDegree
	 *            the idDegree to set
	 */
	public void setDegree(DegreeCV idDegree) {
		this.degree = idDegree;
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
	public void setCurriculum(Curriculum idCurriculum) {
		this.curriculum = idCurriculum;
	}

}
