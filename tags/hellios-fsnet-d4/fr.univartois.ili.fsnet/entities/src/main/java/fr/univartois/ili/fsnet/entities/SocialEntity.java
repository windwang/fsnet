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
    private List<TopicMessage> topicMessages;
    /**
     * The topics that the corporate entity has created.
     */
    @OneToMany(mappedBy = "creator")
    private List<Topic> topics;
    /**
     * The contact list
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SOCIAL_ENTITY__CONTACTS")
    private List<SocialEntity> contacts;
    /**
     * list of refused contacts
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SOCIAL_ENTITY__REFUSED_CONTACTS")
    private List<SocialEntity> refused;
    /**
     * List of requested contacts
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SOCIAL_ENTITY__REQUESTED_CONTACTS")
    private List<SocialEntity> requested;
    /**
     * Received demands list
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SOCIAL_ENTITY__ASKED_CONTACTS")
    private List<SocialEntity> asked;
    
    @OneToMany(mappedBy = "to")
    private List<PrivateMessage> receivedPrivateMessages;
    
    @OneToMany(mappedBy = "from")
    private List<PrivateMessage> sentPrivateMessages;
    
    @OneToMany(mappedBy = "socialEntity")
    private List<InteractionRole> rolesInInteractions;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((asked == null) ? 0 : asked.hashCode());
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((contacts == null) ? 0 : contacts.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((inscritpionDate == null) ? 0 : inscritpionDate.hashCode());
		result = prime * result
				+ ((interactions == null) ? 0 : interactions.hashCode());
		result = prime * result
				+ ((interests == null) ? 0 : interests.hashCode());
		result = prime * result
				+ ((lastConnection == null) ? 0 : lastConnection.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((picture == null) ? 0 : picture.hashCode());
		result = prime * result
				+ ((profession == null) ? 0 : profession.hashCode());
		result = prime
				* result
				+ ((receivedPrivateMessages == null) ? 0
						: receivedPrivateMessages.hashCode());
		result = prime * result + ((refused == null) ? 0 : refused.hashCode());
		result = prime * result
				+ ((requested == null) ? 0 : requested.hashCode());
		result = prime
				* result
				+ ((rolesInInteractions == null) ? 0 : rolesInInteractions
						.hashCode());
		result = prime
				* result
				+ ((sentPrivateMessages == null) ? 0 : sentPrivateMessages
						.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result
				+ ((topicMessages == null) ? 0 : topicMessages.hashCode());
		result = prime * result + ((topics == null) ? 0 : topics.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocialEntity other = (SocialEntity) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (asked == null) {
			if (other.asked != null)
				return false;
		} else if (!asked.equals(other.asked))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (contacts == null) {
			if (other.contacts != null)
				return false;
		} else if (!contacts.equals(other.contacts))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (inscritpionDate == null) {
			if (other.inscritpionDate != null)
				return false;
		} else if (!inscritpionDate.equals(other.inscritpionDate))
			return false;
		if (interactions == null) {
			if (other.interactions != null)
				return false;
		} else if (!interactions.equals(other.interactions))
			return false;
		if (interests == null) {
			if (other.interests != null)
				return false;
		} else if (!interests.equals(other.interests))
			return false;
		if (lastConnection == null) {
			if (other.lastConnection != null)
				return false;
		} else if (!lastConnection.equals(other.lastConnection))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (picture == null) {
			if (other.picture != null)
				return false;
		} else if (!picture.equals(other.picture))
			return false;
		if (profession == null) {
			if (other.profession != null)
				return false;
		} else if (!profession.equals(other.profession))
			return false;
		if (receivedPrivateMessages == null) {
			if (other.receivedPrivateMessages != null)
				return false;
		} else if (!receivedPrivateMessages
				.equals(other.receivedPrivateMessages))
			return false;
		if (refused == null) {
			if (other.refused != null)
				return false;
		} else if (!refused.equals(other.refused))
			return false;
		if (requested == null) {
			if (other.requested != null)
				return false;
		} else if (!requested.equals(other.requested))
			return false;
		if (rolesInInteractions == null) {
			if (other.rolesInInteractions != null)
				return false;
		} else if (!rolesInInteractions.equals(other.rolesInInteractions))
			return false;
		if (sentPrivateMessages == null) {
			if (other.sentPrivateMessages != null)
				return false;
		} else if (!sentPrivateMessages.equals(other.sentPrivateMessages))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (topicMessages == null) {
			if (other.topicMessages != null)
				return false;
		} else if (!topicMessages.equals(other.topicMessages))
			return false;
		if (topics == null) {
			if (other.topics != null)
				return false;
		} else if (!topics.equals(other.topics))
			return false;
		return true;
	}
    
}
