package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
import javax.persistence.EntityManager;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class TopicMessageFacade {

    private final EntityManager em;

    public TopicMessageFacade(EntityManager em) {
        this.em = em;
    }

    /**
     * Create a message
     * @param body the body of the message
     * @param from the author of the message
     * @param topic the topic in wich the new message will be added
     */
    public final void createTopicMessage(String body, SocialEntity from, Topic topic) {
        if (body != null || from == null || topic == null) {
            throw new IllegalArgumentException();
        }
        TopicMessage message = new TopicMessage(body, from, topic);
        em.persist(message);
        topic.getMessages().add(message);
    }

    /**
     *
     * @param id
     * @return the topic with given id
     */
    public final TopicMessage getTopicMessage(int id) {
        return em.find(TopicMessage.class, id);
    }

    /**
     * Delete the TopicMessage with given id
     * @param id
     */
    public final void deleteTopicMessage(int id) {
        TopicMessage message = em.find(TopicMessage.class, id);
        if (message != null) {
            em.remove(message);
        }
    }
}
