package fr.univartois.ili.fsnet.entities;

import java.util.ArrayList;
import java.util.Date;
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
    private String sex;
    /**
     * The password for the social entity.
     */
    private String password;
    /**
     * A picture of the social entity.
     */
    private String picture;
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
    private String phone;
    /**
     * The interactions that the social entity created.
     */
    @OneToMany(mappedBy = "creator")
    private List<Interaction> interactions;
    /**
     * The interest that the social entity informed.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Interest> interests = new ArrayList<Interest>();
    /**
     * The messages that the social entity created.
     */
    @OneToMany(mappedBy = "from")
    private List<Message> messages;
    /**
     * The topics that the corporate entity has created.
     */
    @OneToMany(mappedBy = "creator")
    private List<Topic> topics;
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
    public String getName() {
        return name;
    }

    /**
     * Gives a name to the social entity.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the social entity firstname.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gives a firstname to the social entity.
     *
     * @param firstName
     */
    public void setPrenom(String firstName) {
        this.firstName = firstName;
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
     * Set a date of entry to the social entity.
     *
     * @param inscriptionDate
     */
    public void setInscriptionDate(Date inscriptionDate) {
        this.inscritpionDate = inscriptionDate;
    }

    public Date getInscritpionDate() {
        return inscritpionDate;
    }

    
    /**
     *
     * @return the date of birth of the social entity.
     */
    public Date getBirthDate() {
        return birthDate;
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
    public String getSex() {
        return sex;
    }

    /**
     * Gives a sexe to the social entity.
     *
     * @param sexe
     */
    public void setSex(String sexe) {
        this.sex = sexe;
    }

    /**
     *
     * @return the password of the social entity.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gives a password to the social entity.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return the picture of the social entity.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Gives a picture to the social entity.
     *
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
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
    public String getPhone() {
        return phone;
    }

    /**
     * Gives a telephone number to the social entity.
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return the list of interactions that the social entity created.
     */
    public List<Interaction> getInteractions() {
        return interactions;
    }

    /**
     * Gives a list of interactions to the social entity.
     *
     * @param interactions
     */
    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    /**
     *
     * @return the list of interest that the social entity informed.
     */
    public List<Interest> getInterests() {
        return interests;
    }

    /**
     * Gives a list of interest to the social entity.
     *
     * @param interests
     */
    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    /**
     *
     * @return the list of messages that the social entity created.
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Gives a list of messages to the social entity.
     *
     * @param messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     *
     * @return the list of topics that the social entity created.
     */
    public List<Topic> getTopics() {
        return topics;
    }

    /**
     * Gives a list of topics to the social entity.
     *
     * @param topics
     */
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
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
