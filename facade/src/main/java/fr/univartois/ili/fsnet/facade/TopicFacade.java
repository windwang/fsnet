package fr.univartois.ili.fsnet.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class TopicFacade {

    private final EntityManager em;

    public TopicFacade(EntityManager em) {
        this.em = em;
    }

    /**
     * Create a new Topic in the designed Hub
     * @param hub the hub in wich the new Topic will be added
     * @param creator the creator of the Topic
     * @param title the title of the topic
     * @return the created and persisted Topic
     */
    public final Topic createTopic(Hub hub, SocialEntity creator, String title) {
    	if (hub == null || creator == null || title == null) {
            throw new IllegalArgumentException();
        }
        Topic topic = new Topic(hub, creator, title);
        em.persist(topic);
        hub.getTopics().add(topic);
        return topic;
    }

    /**
     *
     * @param id
     * @return the topic with the given id
     */
    public final Topic getTopic(int id) {
        return em.find(Topic.class, id);
    }

    /**
     * Search Topics in the given Hub
     * @param pattern the pattern to search
     * @param hub the hub to search in
     * @return a list of topics
     */
    public final List<Topic> searchTopic(String pattern, Hub hub) {
        if (pattern == null || hub == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<Topic> query = em.createQuery("SELECT topic FROM Topic topic WHERE topic.title LIKE :pattern AND topic.hub = :hub ", Topic.class);
        query.setParameter("pattern", "%" + pattern + "%");
        query.setParameter("hub", hub);
        return query.getResultList();
    }

    /**
     * Search Topics
     * @param pattern the pattern to search
     * @return a list of topics
     */
    public final List<Topic> searchTopic(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<Topic> query = em.createQuery("SELECT topic FROM Topic topic WHERE topic.title LIKE :pattern", Topic.class);
        query.setParameter("pattern", "%" + pattern + "%");
        return query.getResultList();
    }
}
