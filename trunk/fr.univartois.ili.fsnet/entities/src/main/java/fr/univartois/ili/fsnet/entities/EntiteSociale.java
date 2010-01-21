package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class EntiteSociale.
 * 
 */
@Entity
public class EntiteSociale {

	/**
	 * The identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	/**
	 * The social entity name.
	 */
	private String nom;
	/**
	 * The social entity first name.
	 */
	private String prenom;
	/**
	 * The social entity address.
	 */
	private String adresse;
	/**
	 * The date of entry of the social entity.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateEntree;
	/**
	 * The date of birth of the social entity.
	 */
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	/**
	 * The social entity sexe
	 */
	private String sexe;
	/**
	 * The password for the social entity.
	 */
	private String mdp;
	/**
	 * A picture of the social entity.
	 */
	private String photo;
	/**
	 * The profession of the social entity.
	 */
	private String profession;
	/**
	 * The email address of the social entity
	 */
	@Column(unique = true, nullable = false)
	private String email;
	/**
	 * The telephone number of the social entity.
	 */
	private String numTel;
	/**
	 * The interactions that the social entity created.
	 */
	@OneToMany(mappedBy = "createur")
	private List<Interaction> lesinteractions;
	/**
	 * The interest that the social entity informed.
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST })
	private List<Interet> lesinterets = new ArrayList<Interet>();
	/**
	 * A social entity can play the role of several decision-makers.
	 */
	@OneToMany(mappedBy = "entSociale")
	private List<Decideur> lesDecideurs;
	/**
	 * The messages that the social entity created.
	 */
	@OneToMany(mappedBy = "propMsg")
	private List<Message> lesMessages;
	/**
	 * The topics that the corporate entity has created.
	 */
	@OneToMany(mappedBy = "propTopic")
	private List<Topic> lesTopics;

	
	/**
	 * The contact list
	 */
	@OneToMany
	@JoinTable(name="contacts")
	private List<EntiteSociale> contacts;

	/**
	 * list of refused contacts
	 */
	@OneToMany
	@JoinTable(name="refused")
	private List<EntiteSociale> refused;

	/**
	 * List of requested contacts
	 */
	@OneToMany
	@JoinTable(name="requested")
	private List<EntiteSociale> requested;

	/**
	 * Received demands list
	 */
	@OneToMany
	@JoinTable(name="asked")
	private List<EntiteSociale> asked;

	/**
	 * Constructor of the class EntiteSociale.
	 */
	public EntiteSociale() {
	}

	/**
	 * Constructor of the class EntiteSociale.
	 * 
	 * @param nom
	 * @param prenom
	 * @param email
	 */
	public EntiteSociale(String nom, String prenom, String email) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;

	}

	/**
	 * Constructor of the class EntiteSociale.
	 * 
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param dateEntree
	 * @param dateNaissance
	 * @param mdp
	 * @param photo
	 * @param profession
	 * @param email
	 * @param numTel
	 * @param lesinteractions
	 * @param lesinterets
	 * @param lesDecideurs
	 */
	public EntiteSociale(String nom, String prenom, String adresse,
			Date dateEntree, Date dateNaissance, String mdp, String photo,
			String profession, String email, String numTel,
			List<Interaction> lesinteractions, List<Interet> lesinterets,
			List<Decideur> lesDecideurs) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.dateEntree = dateEntree;
		this.dateNaissance = dateNaissance;
		this.mdp = mdp;
		this.photo = photo;
		this.profession = profession;
		this.email = email;
		this.numTel = numTel;
		this.lesinteractions = lesinteractions;
		this.lesinterets = lesinterets;
		this.lesDecideurs = lesDecideurs;
	}

	/**
	 * Constructor of the class EntiteSociale.
	 * 
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param dateEntree
	 * @param dateNaissance
	 * @param mdp
	 * @param photo
	 * @param profession
	 * @param email
	 * @param numTel
	 * @param lesinteractions
	 * @param lesinterets
	 * @param lesDecideurs
	 * @param lesMessages
	 * @param lesTopics
	 */
	public EntiteSociale(String nom, String prenom, String adresse,
			Date dateEntree, Date dateNaissance, String mdp, String photo,
			String profession, String email, String numTel,
			List<Interaction> lesinteractions, List<Interet> lesinterets,
			List<Decideur> lesDecideurs, List<Message> lesMessages,
			List<Topic> lesTopics) {
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.dateEntree = dateEntree;
		this.dateNaissance = dateNaissance;
		this.mdp = mdp;
		this.photo = photo;
		this.profession = profession;
		this.email = email;
		this.numTel = numTel;
		this.lesinteractions = lesinteractions;
		this.lesinterets = lesinterets;
		this.lesDecideurs = lesDecideurs;
		this.lesMessages = lesMessages;
		this.lesTopics = lesTopics;
	}

	/**
	 * 
	 * @return the identifier.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gives an identifier to the social entity.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the social entity name.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Gives a name to the social entity.
	 * 
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * 
	 * @return the social entity firstname.
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Gives a firstname to the social entity.
	 * 
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * 
	 * @return the adresse of the social entity.
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * Gives an address to the social entity.
	 * 
	 * @param adresse
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * 
	 * @return the date of entry of the social entity.
	 */
	public String getDateEntree() {

		if (dateEntree == null) {
			return null;
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dateEntree);
		int jour = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int mois = cal.get(GregorianCalendar.MONTH) + 1;
		int annee = cal.get(GregorianCalendar.YEAR);
		return jour + "/" + mois + "/" + annee;
	}

	/**
	 * Gives a date of entry to the social entity.
	 * 
	 * @param dateEntree
	 */
	public void setDateEntree(Date dateEntree) {
		this.dateEntree = dateEntree;
	}

	/**
	 * 
	 * @return the date of birth of the social entity.
	 */
	public String getDateNaissance() {

		if (dateNaissance == null) {
			return null;
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dateNaissance);
		int jour = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int mois = cal.get(GregorianCalendar.MONTH) + 1;
		int annee = cal.get(GregorianCalendar.YEAR);
		return jour + "/" + mois + "/" + annee;
	}

	/**
	 * Gives a date of birth to the social entity.
	 * 
	 * @param dateNaissance
	 */
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	/**
	 * 
	 * @return the sexe of the social entity.
	 */
	public String getSexe() {
		return sexe;
	}

	/**
	 * Gives a sexe to the social entity.
	 * 
	 * @param sexe
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	/**
	 * 
	 * @return the password of the social entity.
	 */
	public String getMdp() {
		return mdp;
	}

	/**
	 * Gives a password to the social entity.
	 * 
	 * @param mdp
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	/**
	 * 
	 * @return the picture of the social entity.
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * Gives a picture to the social entity.
	 * 
	 * @param photo
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * 
	 * @return the profession of the social entity.
	 */
	public String getProfession() {
		return profession;
	}

	/**
	 * Gives a profession to the social entity.
	 * 
	 * @param profession
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}

	/**
	 * 
	 * @return the email address of the social entity.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gives an email address to the social entity.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return the telephone number of the social entity.
	 */
	public String getNumTel() {
		return numTel;
	}

	/**
	 * Gives a telephone number to the social entity.
	 * 
	 * @param numTel
	 */
	public void setNumTel(String numTel) {
		this.numTel = numTel;
	}

	/**
	 * 
	 * @return the list of interactions that the social entity created.
	 */
	public List<Interaction> getLesinteractions() {
		return lesinteractions;
	}

	/**
	 * Gives a list of interactions to the social entity.
	 * 
	 * @param lesinteractions
	 */
	public void setLesinteractions(List<Interaction> lesinteractions) {
		this.lesinteractions = lesinteractions;
	}

	/**
	 * 
	 * @return the list of interest that the social entity informed.
	 */
	public List<Interet> getLesinterets() {
		return lesinterets;
	}

	/**
	 * Gives a list of interest to the social entity.
	 * 
	 * @param lesinterets
	 */
	public void setLesinterets(List<Interet> lesinterets) {
		this.lesinterets = lesinterets;
	}

	/**
	 * 
	 * @return the list of roles of decision makers that the social entity
	 *         played.
	 */
	public List<Decideur> getLesDecideurs() {
		return lesDecideurs;
	}

	/**
	 * Gives a list of roles of decision makers to the social entity.
	 * 
	 * @param lesDecideurs
	 */
	public void setLesDecideurs(List<Decideur> lesDecideurs) {
		this.lesDecideurs = lesDecideurs;
	}

	/**
	 * 
	 * @return the list of messages that the social entity created.
	 */
	public List<Message> getLesMessages() {
		return lesMessages;
	}

	/**
	 * Gives a list of messages to the social entity.
	 * 
	 * @param lesMessages
	 */
	public void setLesMessages(List<Message> lesMessages) {
		this.lesMessages = lesMessages;
	}

	/**
	 * 
	 * @return the list of topics that the social entity created.
	 */
	public List<Topic> getLesTopics() {
		return lesTopics;
	}

	/**
	 * Gives a list of topics to the social entity.
	 * 
	 * @param lesTopics
	 */
	public void setLesTopics(List<Topic> lesTopics) {
		this.lesTopics = lesTopics;
	}

	/**
	 * 
	 * @return the list of contacts.
	 */
	public List<EntiteSociale> getContacts() {
		return contacts;
	}

	/**
	 * Set the contact list
	 * 
	 * @param contacts
	 */
	public void setContacts(List<EntiteSociale> contacts) {
		this.contacts = contacts;
	}

	/**
	 * 
	 * @return the list of refused contacts.
	 */
	public List<EntiteSociale> getRefused() {
		return refused;
	}

	/**
	 * Set the list of refused contacts
	 * 
	 * @param refused
	 */
	public void setRefused(List<EntiteSociale> refused) {
		this.refused = refused;
	}

	
	
	/**
	 * 
	 * @return the list of received demands.
	 */
	public List<EntiteSociale> getAsked() {
		return asked;
	}
	
    /**
     * Set the asked list
     * 
     * @param asked
     */
	public void setAsked(List<EntiteSociale> asked) {
		this.asked = asked;
	}

	/**
	 * 
	 * @return the list of demands.
	 */
	public List<EntiteSociale> getRequested() {
		return requested;
	}
	
	/**
	 * Set the requested contacts list
	 * 
	 * @param requested
	 */
	public void setRequested(List<EntiteSociale> requested) {
		this.requested = requested;
	}

}
