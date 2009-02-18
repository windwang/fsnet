package fr.univartois.ili.fsnet.facade.forum;

import java.util.Date;
import java.util.List;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;

/**
 * This interface provide a facade between FSNET and a framework of forum.
 * 
 * @author STACKOWIAK Denis
 */
public interface ForumFacade {

    /**
     * Get the list of all hub.
     * 
     * @return List of hub
     */
    public List<Hub> getListHub();

    /**
     * Get the list of hub created between the date "dateBegin" and the date
     * "dateEnd".
     * 
     * @param dateBegin
     * @param dateEnd
     * @return List of hub
     */
    public List<Hub> getListHub(Date dateBegin, Date dateEnd);

    /**
     * Get the list of hub created by the EntiteSociale "decideur".
     * 
     * @param decideur
     *            who created the hub
     * @return List of hub
     */
    public List<Hub> getListHubByEntiteSociale(EntiteSociale decideur);

    /**
     * Get the list of all Topic.
     * 
     * @return List of topic
     */
    public List<Topic> getListTopic();

    /**
     * Get the list of topic created between the date "dateBegin" and the date
     * "dateEnd".
     * 
     * @param dateBegin
     * @param dateEnd
     * @return List of topic
     */
    public List<Topic> getListTopic(Date dateBegin, Date dateEnd);

    /**
     * Get the list of topic created by the EntiteSociale "entiteSocial".
     * 
     * @param entiteSocial
     *            who created the hub
     * @return
     */
    public List<Topic> getListTopicByEntiteSociale(EntiteSociale entiteSocial);

    /**
     * Get the list of topic from the hub "hub".
     * 
     * @param hub
     * @return List of topic
     */
    public List<Topic> getListTopicByHub(Hub hub);

    /**
     * Get the list of All Message.
     * 
     * @return List of message
     */
    public List<Message> getListMessage();

    /**
     * Get the list of message created between the date "dateBegin" and the date
     * "dateEnd".
     * 
     * @param dateBegin
     * @param dateEnd
     * @return List of message
     */
    public List<Message> getListMessage(Date dateBegin, Date dateEnd);

    /**
     * Get the list of message created by the EntiteSociale "entiteSocial".
     * 
     * @param entiteSociale
     * @return List of message
     */
    public List<Message> getListMessageByEntiteSocial(
	    EntiteSociale entiteSociale);

    /**
     * Get list of message from the topic "topic".
     * 
     * @param topic
     * @return List of message
     */
    public List<Message> getListMessageByTopic(Topic topic);

    /**
     * Get list of message from the hub "hub"
     * 
     * @param hub
     * @return List of message
     */
    public List<Message> getListMessageByHub(Hub hub);

    /**
     * Add a hub.
     * 
     * @param hub
     * @return True if hub add successfully, False else.
     */
    public boolean addHub(Hub hub);

    /**
     * Update the hub
     * 
     * @param hub
     * @return
     */
    public boolean updateHub(Hub hub);

    public boolean removeHub(Hub hub);

    public boolean addTopic(Topic topic);

    public boolean updateTopic(Topic topic);

    public boolean removeTopic(Topic topic);

    public boolean addMessage(Message message);

    public boolean updateMessage(Message message);

    public boolean removeMessage(Message message);

}
