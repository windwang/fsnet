package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/*
 * Author : Yoann VASSEUR
 * 
 * 
 */
@Entity
public class HobbiesCV implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	private String name;

	private String note;

	@ManyToMany(mappedBy = "hobs")
	private List<Curriculum> myCVs = new ArrayList<Curriculum>();

	public HobbiesCV() {

	}

	/**
	 * @return the myCVs
	 */
	public List<Curriculum> getMyCVs() {
		return myCVs;
	}

	/**
	 * @param myCVs
	 *            the myCVs to set
	 */
	public void setMyCVs(List<Curriculum> myCVs) {
		this.myCVs = myCVs;
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
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}


}
