package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class AssociationDateFormationCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	private Date obtainedDate;


	@ManyToOne
	private FormationCV idFormation;

	@ManyToOne
	private Curriculum idCurriculum;

	public AssociationDateFormationCV() {

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
	 * @return the obtainedDate
	 */
	public Date getObtainedDate() {
		return obtainedDate;
	}

	/**
	 * @param obtainedDate
	 *            the obtainedDate to set
	 */
	public void setObtainedDate(Date obtainedDate) {
		this.obtainedDate = obtainedDate;
	}

	

	/**
	 * @return the idFormation
	 */
	public FormationCV getIdFormation() {
		return idFormation;
	}

	/**
	 * @param idFormation
	 *            the idFormation to set
	 */
	public void setIdFormation(FormationCV idFormation) {
		this.idFormation = idFormation;
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
	public Curriculum getIdCurriculum() {
		return idCurriculum;
	}

	/**
	 * @param idCurriculum
	 *            the idCurriculum to set
	 */
	public void setIdCurriculum(Curriculum idCurriculum) {
		this.idCurriculum = idCurriculum;
	}

}
