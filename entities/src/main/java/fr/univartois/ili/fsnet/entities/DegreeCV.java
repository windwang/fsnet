package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class DegreeCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	private String studiesLevel;
	
	private String domain;

	@OneToMany (mappedBy="degree")
	private List<AssociationDateDegreeCV> myCVs = new ArrayList<AssociationDateDegreeCV>();

	@OneToOne
	private EstablishmentCV ets;

	public DegreeCV() {

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
	 * @return the studiesLevel
	 */
	public String getStudiesLevel() {
		return studiesLevel;
	}

	/**
	 * @param studiesLevel
	 *            the studiesLevel to set
	 */
	public void setStudiesLevel(String studiesLevel) {
		this.studiesLevel = studiesLevel;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}


	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @param ets
	 *            the ets to set
	 */
	public void setEts(EstablishmentCV ets) {
		this.ets = ets;
	}

	/**
	 * @return the myCVs
	 */
	public List<AssociationDateDegreeCV> getAssociationDateDegreeCV() {
		return myCVs;
	}

	/**
	 * @param myCVs
	 *            the myCVs to set
	 */
	public void setAssociationDateDegreeCV(
			List<AssociationDateDegreeCV> myAssociationDateDegreeCV) {
		this.myCVs = myAssociationDateDegreeCV;
	}

	public EstablishmentCV getEts() {
		return ets;
	}

}
