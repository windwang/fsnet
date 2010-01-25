package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class SocialEntity.
 * 
 */
@Entity
public class SocialEntity {

    /**
     * The identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    /**
     * The social entity name.
     */
    private String name;
    /**
     * The social entity first name.
     */
    private String firstName;
    /**
     * The social entity address.
     */
    @Embedded
    private Address address;
    /**
     * The date of entry of the social entity.
     */
    @Temporal(TemporalType.DATE)
    private Date inscritpionDate;
    /**
     * The date of birth of the social entity.
     */
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    /**
     * The date of social entity's last connection
     */
    @Temporal(TemporalType.DATE)
    private Date lastConnection;
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
    @OneToMany(mappedBy = "creator")
    private List<Interaction> lesinteractions;
    /**
     * The interest that the social entity informed.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Interest> lesinterets = new ArrayList<Interest>();
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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contacts")
    private List<SocialEntity> contacts;
    /**
     * list of refused contacts
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "refused")
    private List<SocialEntity> refused;
    /**
     * List of requested contacts
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "requested")
    private List<SocialEntity> requested;
    /**
     * Received demands list
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "asked")
    private List<SocialEntity> asked;

    /**
     * Constructor of the class SocialEntity.
     */
    public SocialEntity() {
    }

    /**
     * Constructor of the class SocialEntity.
     *
     * @param name
     * @param firstName
     * @param email
     */
    public SocialEntity(String name, String firstName, String email) {
        if (name == null || firstName == null || email == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.firstName = firstName;
        this.email = email;
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
        return name;
    }

    /**
     * Gives a name to the social entity.
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.name = nom;
    }

    /**
     *
     * @return the social entity firstname.
     */
    public String getPrenom() {
        return firstName;
    }

    /**
     * Gives a firstname to the social entity.
     *
     * @param firstName
     */
    public void setPrenom(String prenom) {
        this.firstName = prenom;
    }

    /**
     *
     * @return the adresse of the social entity.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Gives an address to the social entity.
     *
     * @param adresse
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gives a date of entry to the social entity.
     *
     * @param dateEntree
     */
    public void setDateEntree(Date dateEntree) {
        this.inscritpionDate = dateEntree;
    }

    /**
     *
     * @return the date of birth of the social entity.
     */
    public String getBirthDate() {

        if (birthDate == null) {
            return null;
        }

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(birthDate);
        int jour = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int mois = cal.get(GregorianCalendar.MONTH) + 1;
        int annee = cal.get(GregorianCalendar.YEAR);
        return jour + "/" + mois + "/" + annee;
    }

    /**
     * Gives a date of birth to the social entity.
     *
     * @param birthDate
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
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
    public List<Interest> getLesinterets() {
        return lesinterets;
    }

    /**
     * Gives a list of interest to the social entity.
     *
     * @param lesinterets
     */
    public void setLesinterets(List<Interest> lesinterets) {
        this.lesinterets = lesinterets;
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
    public List<SocialEntity> getContacts() {
        return contacts;
    }

    /**
     * Set the contact list
     *
     * @param contacts
     */
    public void setContacts(List<SocialEntity> contacts) {
        this.contacts = contacts;
    }

    /**
     *
     * @return the list of refused contacts.
     */
    public List<SocialEntity> getRefused() {
        return refused;
    }

    /**
     * Set the list of refused contacts
     *
     * @param refused
     */
    public void setRefused(List<SocialEntity> refused) {
        this.refused = refused;
    }

    /**
     *
     * @return the list of received demands.
     */
    public List<SocialEntity> getAsked() {
        return asked;
    }

    /**
     * Set the asked list
     * 
     * @param asked
     */
    public void setAsked(List<SocialEntity> asked) {
        this.asked = asked;
    }

    /**
     *
     * @return the list of demands.
     */
    public List<SocialEntity> getRequested() {
        return requested;
    }

    /**
     * Set the requested contacts list
     *
     * @param requested
     */
    public void setRequested(List<SocialEntity> requested) {
        this.requested = requested;
    }
}
