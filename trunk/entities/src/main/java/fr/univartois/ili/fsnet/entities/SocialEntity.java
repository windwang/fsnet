package fr.univartois.ili.fsnet.entities;

import java.io.Serializable;
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
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The class SocialEntity.
 * 
 */
@Entity
public class SocialEntity implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Temporal(TemporalType.TIMESTAMP)
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
    @OrderBy(value="name")
    private List<Interest> interests = new ArrayList<Interest>();
    /**
     * The messages that the social entity created.
     */
    @OneToMany(mappedBy = "from")
    private List<TopicMessage> topicMessages;
    /**
     * The topics that the corporate entity has created.
     */
    @OneToMany(mappedBy = "creator")
    private List<Topic> topics;
    /**
     * The contact list
     */
    @OneToMany
    @JoinTable(name = "SOCIAL_ENTITY__CONTACTS")
    private List<SocialEntity> contacts;
    /**
     * list of refused contacts
     */
    @OneToMany
    @JoinTable(name = "SOCIAL_ENTITY__REFUSED_CONTACTS")
    private List<SocialEntity> refused;
    /**
     * List of requested contacts
     */
    @OneToMany
    @JoinTable(name = "SOCIAL_ENTITY__REQUESTED_CONTACTS")
    private List<SocialEntity> requested;
    /**
     * Received demands list
     */
    @OneToMany
    @JoinTable(name = "SOCIAL_ENTITY__ASKED_CONTACTS")
    private List<SocialEntity> asked;
    
    @OneToMany
    @JoinTable(name = "PRIVATE_MESSAGE_RECEIVED")
    private List<PrivateMessage> receivedPrivateMessages;

    @OneToMany
    @JoinTable(name = "PRIVATE_MESSAGE_SENT")
    private List<PrivateMessage> sentPrivateMessages;
  
    @OneToMany(mappedBy = "socialEntity", cascade=CascadeType.ALL)
    private List<InteractionRole> rolesInInteractions;
    
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Interaction> favoriteInteractions;
    
    @OneToMany(mappedBy = "visited", cascade=CascadeType.ALL)
    private List<ProfileVisite> visitesOnProfile;
    
    @OneToMany(mappedBy = "visitor", cascade=CascadeType.ALL)
    private List<ProfileVisite> visitedProlfiles;

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
        interactions = new ArrayList<Interaction>();
        interests = new ArrayList<Interest>();
        topicMessages = new ArrayList<TopicMessage>();
        receivedPrivateMessages = new ArrayList<PrivateMessage>();
        sentPrivateMessages = new ArrayList<PrivateMessage>();
        contacts = new ArrayList<SocialEntity>();
        asked = new ArrayList<SocialEntity>();
        refused = new ArrayList<SocialEntity>();
        requested = new ArrayList<SocialEntity>();
        rolesInInteractions = new ArrayList<InteractionRole>();
        topics = new ArrayList<Topic>();
        visitesOnProfile = new ArrayList<ProfileVisite>();
        visitedProlfiles = new ArrayList<ProfileVisite>();
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
    public void setFisrtname(String firstName) {
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
     * @return the list of TopicMessages that the social entity created.
     */
    public List<TopicMessage> getTopicMessages() {
        return topicMessages;
    }

    /**
     * Set the list of TopicMessages.
     *
     * @param messages
     */
    public void setMessages(List<TopicMessage> messages) {
        this.topicMessages = messages;
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

    /**
     * @return the received private messages by this social entity
     */
    public List<PrivateMessage> getReceivedPrivateMessages() {
        return receivedPrivateMessages;
    }

    /**
     * @param receivedPrivateMessages
     *            the messages received by this social entity
     */
    public void setReceivedPrivateMessages(
            List<PrivateMessage> receivedPrivateMessages) {
        this.receivedPrivateMessages = receivedPrivateMessages;
    }

    /**
     * @return the sent private messages by this social entity
     */
    public List<PrivateMessage> getSentPrivateMessages() {
        return sentPrivateMessages;
    }

    /**
     * @param sentPrivateMessages
     *            the messages sent by this social entity
     */
    public void setSentPrivateMessages(List<PrivateMessage> sentPrivateMessages) {
        this.sentPrivateMessages = sentPrivateMessages;
    }

    /**
     * @return the rolesInInteractions
     */
    public List<InteractionRole> getRolesInInteractions() {
        return rolesInInteractions;
    }

    /**
     * @param rolesInInteractions the roles associated with the interactions to set
     */
    public void setRolesInInteractions(List<InteractionRole> rolesInInteractions) {
        this.rolesInInteractions = rolesInInteractions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SocialEntity other = (SocialEntity) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
            return false;
        }
        if (this.address != other.address && (this.address == null || !this.address.equals(other.address))) {
            return false;
        }
        if (this.inscritpionDate != other.inscritpionDate && (this.inscritpionDate == null || !this.inscritpionDate.equals(other.inscritpionDate))) {
            return false;
        }
        if (this.birthDate != other.birthDate && (this.birthDate == null || !this.birthDate.equals(other.birthDate))) {
            return false;
        }
        if (this.lastConnection != other.lastConnection && (this.lastConnection == null || !this.lastConnection.equals(other.lastConnection))) {
            return false;
        }
        if ((this.sex == null) ? (other.sex != null) : !this.sex.equals(other.sex)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        if ((this.profession == null) ? (other.profession != null) : !this.profession.equals(other.profession)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.phone == null) ? (other.phone != null) : !this.phone.equals(other.phone)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
        hash = 97 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 97 * hash + (this.inscritpionDate != null ? this.inscritpionDate.hashCode() : 0);
        hash = 97 * hash + (this.birthDate != null ? this.birthDate.hashCode() : 0);
        hash = 97 * hash + (this.lastConnection != null ? this.lastConnection.hashCode() : 0);
        hash = 97 * hash + (this.sex != null ? this.sex.hashCode() : 0);
        hash = 97 * hash + (this.password != null ? this.password.hashCode() : 0);
        hash = 97 * hash + (this.profession != null ? this.profession.hashCode() : 0);
        hash = 97 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 97 * hash + (this.phone != null ? this.phone.hashCode() : 0);
        return hash;
    }

    /**
     * @param favoriteInteractions the favoriteInteractions to set
     */
    public void setFavoriteInteractions(List<Interaction> favoriteInteractions) {
        this.favoriteInteractions = favoriteInteractions;
    }

    /**
     * @return the favoriteInteractions
     */
    public List<Interaction> getFavoriteInteractions() {
        return favoriteInteractions;
    }

    /**
     *
     * @return the list of Profile visite where it's this social entity frofile whitch visit
     */
    public List<ProfileVisite> getVisitesOnProfile() {
        return visitesOnProfile;
    }

    /**
     * set the list of Profile visite where it's this social entity profile whitch visit
     * @param haveBeenVisit
     */

    public void setVisitesOnProfiles(List<ProfileVisite> haveBeenVisit) {
        this.visitesOnProfile = haveBeenVisit;
    }

    /**
     *
     * @return the list of Profile visite where the social entity is the visitor
     */
    public List<ProfileVisite> getVisitedProfiles() {
        return visitedProlfiles;
    }

    /**
     * set the list of Profile visite where the social entity is the visitor
     * @param haveVisit
     */


    public void setVisitedProfiles(List<ProfileVisite> haveVisit) {
        this.visitedProlfiles = haveVisit;
    }
}
