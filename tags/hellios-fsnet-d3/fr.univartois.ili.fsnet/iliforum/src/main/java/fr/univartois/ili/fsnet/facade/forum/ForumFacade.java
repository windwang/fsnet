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
    List<Hub> getListHub();

    /**
     * Get the list of hub created between the date "dateBegin" and the date
     * "dateEnd".
     * 
     * @param dateBegin
     * @param dateEnd
     * @return List of hub
     */
    List<Hub> getListHub(Date dateBegin, Date dateEnd);

    /**
     * Get the list of hub created by the EntiteSociale "decideur".
     * 
     * @param decideur
     *            who created the hub
     * @return List of hub
     */
    List<Hub> getListHubByEntiteSociale(EntiteSociale decideur);

    /**
     * Get the list of all Topic.
     * 
     * @return List of topic
     */
    List<Topic> getListTopic();

    /**
     * Get the list of topic created between the date "dateBegin" and the date
     * "dateEnd".
     * 
     * @param dateBegin
     * @param dateEnd
     * @return List of topic
     */
    List<Topic> getListTopic(Date dateBegin, Date dateEnd);

    /**
     * Get the list of topic created by the EntiteSociale "entiteSocial".
     * 
     * @param entiteSocial
     *            who created the hub
     * @return
     */
    List<Topic> getListTopicByEntiteSociale(EntiteSociale entiteSocial);

    /**
     * Get the list of topic from the hub "hub".
     * 
     * @param hub
     * @return List of topic
     */
    List<Topic> getListTopicByHub(Hub hub);

    /**
     * Get the list of All Message.
     * 
     * @return List of message
     */
    List<Message> getListMessage();

    /**
     * Get the list of message created between the date "dateBegin" and the date
     * "dateEnd".
     * 
     * @param dateBegin
     * @param dateEnd
     * @return List of message
     */
    List<Message> getListMessage(Date dateBegin, Date dateEnd);

    /**
     * Get the list of message created by the EntiteSociale "entiteSocial".
     * 
     * @param entiteSociale
     * @return List of message
     */
    List<Message> getListMessageByEntiteSocial(
            EntiteSociale entiteSociale);

    /**
     * Get list of message from the topic "topic".
     * 
     * @param topic
     * @return List of message
     */
    List<Message> getListMessageByTopic(Topic topic);

    /**
     * Get list of message from the hub "hub"
     * 
     * @param hub
     * @return List of message
     */
    List<Message> getListMessageByHub(Hub hub);

    /**
     * Add a hub.
     * 
     * @param hub
     * @return True if hub add successfully, False else.
     */
    boolean addHub(Hub hub);

    /**
     * Update the hub
     * 
     * @param hub
     * @return
     */
    boolean updateHub(Hub hub, String titre);

    boolean removeHub(Hub hub);

    boolean addTopic(Topic topic);

    boolean updateTopic(Topic topic, String titre);

    boolean removeTopic(Topic topic);

    boolean addMessage(Message message);

    boolean updateMessage(Message message, String contenu);

    boolean removeMessage(Message message);
}
