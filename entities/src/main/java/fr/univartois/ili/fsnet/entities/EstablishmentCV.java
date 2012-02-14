package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 * Author : Yoann VASSEUR
 * 
 * 
 */
@Entity
public class EstablishmentCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	private String name;

	private String adress;
	private int postCode;
	private String town;
	private String land;

	public EstablishmentCV() {

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
	 * @return the adress
	 */
	public String getAdress() {
		return adress;
	}

	/**
	 * @param adress
	 *            the adress to set
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}

	/**
	 * @return the postCode
	 */
	public int getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode
	 *            the postCode to set
	 */
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @param town
	 *            the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * @return the land
	 */
	public String getLand() {
		return land;
	}

	/**
	 * @param land
	 *            the land to set
	 */
	public void setLand(String land) {
		this.land = land;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
