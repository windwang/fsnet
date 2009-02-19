package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.ForumFacade;

public class IliForumFacade implements ForumFacade {
    public static final String DATABASE_NAME = "fsnetjpa";

    private EntityManager em;

    public IliForumFacade() {
	EntityManagerFactory factory = Persistence
		.createEntityManagerFactory(DATABASE_NAME);
	em = factory.createEntityManager();
    }

    public void close() {
	em.close();
    }

    @Override
    public boolean addHub(Hub hub) {

	em.getTransaction().begin();
	em.persist(hub);
	em.getTransaction().commit();

	return true;
    }

    @Override
    public boolean addMessage(Message message) {
	em.getTransaction().begin();
	em.persist(message);
	em.getTransaction().commit();
	return true;
    }

    @Override
    public boolean addTopic(Topic topic) {
	em.getTransaction().begin();
	em.persist(topic);
	em.getTransaction().commit();
	return true;
    }

    @Override
    public List<Hub> getListHub() {
	Query query = em.createQuery("SELECT h FROM Hub h ");
	List<Hub> mesHubs;
	mesHubs = (List<Hub>) query.getResultList();
	return mesHubs;
    }

    @Override
    public List<Hub> getListHub(Date dateBegin, Date dateEnd) {
	Query query = em
		.createQuery("SELECT h FROM Hub h WHERE h.dateCreation>?1 and h.dateCreation<?2");
	query.setParameter(1, dateBegin);
	query.setParameter(2, dateEnd);
	List<Hub> mesHubs;
	mesHubs = (List<Hub>) query.getResultList();
	return mesHubs;
    }

    @Override
    public List<Hub> getListHubByEntiteSociale(EntiteSociale decideur) {
	Query query = em
		.createQuery("SELECT h FROM Hub h WHERE h.decideur =?1");
	query.setParameter(1, decideur);
	List<Hub> mesHubs;
	mesHubs = (List<Hub>) query.getResultList();
	return mesHubs;
    }

    @Override
    public List<Message> getListMessage() {
	Query query = em.createQuery("SELECT m FROM Message m ");
	List<Message> mesMess;
	mesMess = (List<Message>) query.getResultList();
	return mesMess;
    }

    @Override
    public List<Message> getListMessage(Date dateBegin, Date dateEnd) {
	Query query = em
		.createQuery("SELECT m FROM Message m WHERE m.dateMessage>?1 AND m.dateMessage<?2");
	query.setParameter(1, dateBegin);
	query.setParameter(2, dateEnd);
	List<Message> mesMess;
	mesMess = (List<Message>) query.getResultList();
	return mesMess;
    }

    @Override
    public List<Message> getListMessageByEntiteSocial(
	    EntiteSociale entiteSociale) {
	Query query = em
		.createQuery("SELECT m FROM Message m WHERE m.propMsg=?1");
	query.setParameter(1, entiteSociale);
	List<Message> mesMess;
	mesMess = (List<Message>) query.getResultList();
	return mesMess;
    }

    @Override
    public List<Message> getListMessageByHub(Hub hub) {
	Query query = em
		.createQuery("SELECT m FROM Message m WHERE m.topic.hub=?1");
	query.setParameter(1, hub);
	List<Message> mesMess;
	mesMess = (List<Message>) query.getResultList();
	return mesMess;
    }

    @Override
    public List<Message> getListMessageByTopic(Topic topic) {
	Query query = em
		.createQuery("SELECT m FROM Message m WHERE m.topic=?1");
	query.setParameter(1, topic);
	List<Message> mesMess;
	mesMess = (List<Message>) query.getResultList();
	return mesMess;
    }

    @Override
    public List<Topic> getListTopic() {
	Query query = em.createQuery("SELECT t FROM Topic t");
	List<Topic> mesTopics;
	mesTopics = (List<Topic>) query.getResultList();
	return mesTopics;
    }

    @Override
    public List<Topic> getListTopic(Date dateBegin, Date dateEnd) {
	Query query = em
		.createQuery("SELECT t FROM Topic t WHERE t.dateSujet>?1 AND t.dateSujet<?2");
	query.setParameter(1, dateBegin);
	query.setParameter(2, dateEnd);
	List<Topic> mesTopics;
	mesTopics = (List<Topic>) query.getResultList();
	return mesTopics;
    }

    @Override
    public List<Topic> getListTopicByEntiteSociale(EntiteSociale entiteSocial) {
	Query query = em
		.createQuery("SELECT t FROM Topic t WHERE t.propTopic=?1");
	query.setParameter(1, entiteSocial);
	List<Topic> mesTopics;
	mesTopics = (List<Topic>) query.getResultList();
	return mesTopics;
    }

    @Override
    public List<Topic> getListTopicByHub(Hub hub) {
	Query query = em.createQuery("SELECT t FROM Topic t WHERE t.hub=?1");
	query.setParameter(1, hub);
	List<Topic> mesTopics;
	mesTopics = (List<Topic>) query.getResultList();
	return mesTopics;
    }

    @Override
    public boolean removeHub(Hub hub) {
	em.getTransaction().begin();
	em.remove(hub);
	em.getTransaction().commit();
	return true;
    }

    @Override
    public boolean removeMessage(Message message) {
	em.getTransaction().begin();
	em.remove(message);
	em.getTransaction().commit();
	return true;
    }

    @Override
    public boolean removeTopic(Topic topic) {
	em.getTransaction().begin();
	em.remove(topic);
	em.getTransaction().commit();
	return true;
    }

    @Override
    public boolean updateHub(Hub hub) {
	em.getTransaction().begin();
	em.refresh(hub);
	em.getTransaction().commit();
	return true;
    }

    @Override
    public boolean updateMessage(Message message) {
	em.getTransaction().begin();
	em.refresh(message);
	em.getTransaction().commit();
	return true;
    }

    @Override
    public boolean updateTopic(Topic topic) {
	em.getTransaction().begin();
	em.refresh(topic);
	em.getTransaction().commit();
	return true;
    }

}
