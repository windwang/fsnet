package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;

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
        if (body == null || from == null || topic == null) {
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

    /**
     * Search TopicMessages in the given Topic
     * @param pattern the pattern to search
     * @param topic the Topic to search in
     * @return a list of TopicMessage
     */
    public List<TopicMessage> searchTopic(String pattern, Topic topic) {
        if (pattern == null || topic == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<TopicMessage> query = em.createQuery("SELECT tm FROM TopicMessage tm WHERE tm.body LIKE :pattern AND tm.topic = :topic ", TopicMessage.class);
        query.setParameter("pattern", "%" + pattern + "%");
        query.setParameter("topic", topic);
        return query.getResultList();
    }

    /**
     * Search TopicMessages
     * @param pattern the pattern to search
     * @return a list of TopicMessage
     */
    public List<TopicMessage> searchTopic(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<TopicMessage> query = em.createQuery("SELECT tm FROM TopicMessage tm WHERE tm.body LIKE :pattern ", TopicMessage.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return query.getResultList();
    }
}
