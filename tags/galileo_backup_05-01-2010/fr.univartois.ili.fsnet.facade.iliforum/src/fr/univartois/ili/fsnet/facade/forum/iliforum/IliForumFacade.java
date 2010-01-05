package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.Date;
import java.util.Iterator;
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

public final class IliForumFacade implements ForumFacade {
	public static final String DATABASE_NAME = "fsnetjpa";

	private final transient EntityManager entM;

	private static IliForumFacade instance = new IliForumFacade();

	public static IliForumFacade getInstance() {
		return instance;
	}

	private IliForumFacade() {
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
		entM = factory.createEntityManager();
	}

	public void close() {
		entM.close();
	}

	@Override
	public boolean addHub(final Hub hub) {

		entM.getTransaction().begin();
		entM.persist(hub);
		entM.getTransaction().commit();

		return true;
	}

	@Override
	public boolean addMessage(final Message message) {
		Topic topic;
		topic = message.getTopic();
		entM.getTransaction().begin();
		Topic mergedTopic;
		mergedTopic = entM.merge(topic);
		mergedTopic.getLesMessages().add(message);
		message.setTopic(mergedTopic);
		entM.persist(message);
		entM.getTransaction().commit();
		return true;
	}

	@Override
	public boolean addTopic(final Topic topic) {
		Hub hub;
		hub = topic.getHub();
		entM.getTransaction().begin();
		Hub mergedHub;
		mergedHub = entM.merge(hub);
		mergedHub.getLesTopics().add(topic);
		topic.setHub(mergedHub);
		entM.persist(topic);
		entM.getTransaction().commit();
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hub> getListHub() {
		Query query;
		query = entM.createQuery("SELECT h FROM Hub h ORDER BY h.dateCreation");
		List<Hub> mesHubs;
		mesHubs = (List<Hub>) query.getResultList();
		return mesHubs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hub> getListHub(final Date dateBegin, final Date dateEnd) {
		Query query;
		query = entM
				.createQuery("SELECT h FROM Hub h WHERE h.dateCreation>?1 and h.dateCreation<?2 ORDER BY h.dateCreation");
		query.setParameter(1, dateBegin);
		query.setParameter(2, dateEnd);
		List<Hub> mesHubs;
		mesHubs = (List<Hub>) query.getResultList();
		return mesHubs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hub> getListHubByEntiteSociale(final EntiteSociale decideur) {
		Query query;
		query = entM
				.createQuery("SELECT h FROM Hub h WHERE h.decideur =?1 ORDER BY h.dateCreation");
		query.setParameter(1, decideur);
		List<Hub> mesHubs;
		mesHubs = (List<Hub>) query.getResultList();
		return mesHubs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListMessage() {
		Query query;
		query = entM
				.createQuery("SELECT m FROM Message m ORDER BY m.dateMessage");
		List<Message> mesMess;
		mesMess = (List<Message>) query.getResultList();
		return mesMess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListMessage(final Date dateBegin, final Date dateEnd) {
		Query query;
		query = entM
				.createQuery("SELECT m FROM Message m WHERE m.dateMessage>?1 AND m.dateMessage<?2 ORDER BY m.dateMessage");
		query.setParameter(1, dateBegin);
		query.setParameter(2, dateEnd);
		List<Message> mesMess;
		mesMess = (List<Message>) query.getResultList();
		return mesMess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListMessageByEntiteSocial(
			final EntiteSociale entiteSociale) {
		Query query;
		query = entM
				.createQuery("SELECT m FROM Message m WHERE m.propMsg=?1 ORDER BY m.dateMessage");
		query.setParameter(1, entiteSociale);
		List<Message> mesMess;
		mesMess = (List<Message>) query.getResultList();
		return mesMess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListMessageByHub(final Hub hub) {
		Query query;
		query = entM
				.createQuery("SELECT m FROM Message m WHERE m.topic.hub=?1 ORDER BY m.dateMessage");
		query.setParameter(1, hub);
		List<Message> mesMess;
		mesMess = (List<Message>) query.getResultList();
		return mesMess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListMessageByTopic(final Topic topic) {
		Query query;
		query = entM
				.createQuery("SELECT m FROM Message m WHERE m.topic=?1 ORDER BY m.dateMessage");
		query.setParameter(1, topic);
		List<Message> mesMess;
		mesMess = (List<Message>) query.getResultList();
		return mesMess;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getListTopic() {
		Query query;
		query = entM.createQuery("SELECT t FROM Topic t ORDER BY t.dateSujet");
		List<Topic> mesTopics;
		mesTopics = (List<Topic>) query.getResultList();
		return mesTopics;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getListTopic(final Date dateBegin, final Date dateEnd) {
		Query query;
		query = entM
				.createQuery("SELECT t FROM Topic t WHERE t.dateSujet>?1 AND t.dateSujet<?2 ORDER BY t.dateSujet");
		query.setParameter(1, dateBegin);
		query.setParameter(2, dateEnd);
		List<Topic> mesTopics;
		mesTopics = (List<Topic>) query.getResultList();
		return mesTopics;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getListTopicByEntiteSociale(
			final EntiteSociale entiteSocial) {
		Query query;
		query = entM
				.createQuery("SELECT t FROM Topic t WHERE t.propTopic=?1 ORDER BY t.dateSujet");
		query.setParameter(1, entiteSocial);
		List<Topic> mesTopics;
		mesTopics = (List<Topic>) query.getResultList();
		return mesTopics;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topic> getListTopicByHub(final Hub hub) {
		Query query;
		query = entM
				.createQuery("SELECT t FROM Topic t WHERE t.hub=?1 ORDER BY t.dateSujet");
		query.setParameter(1, hub);
		List<Topic> mesTopics;
		mesTopics = (List<Topic>) query.getResultList();
		return mesTopics;
	}

	@Override
	public boolean removeHub(final Hub hub) {
		Hub hubMerge;
		entM.getTransaction().begin();
		hubMerge = entM.merge(hub);
		List<Topic> mesTopics;
		mesTopics = hubMerge.getLesTopics();
		Iterator<Topic> itTopic;
		itTopic = mesTopics.iterator();
		while (itTopic.hasNext()) {
			Topic topicMerge;
			topicMerge = entM.merge(itTopic.next());
			List<Message> mesMessages;
			mesMessages = topicMerge.getLesMessages();
			Iterator<Message> itMessage;
			itMessage = mesMessages.iterator();
			while (itMessage.hasNext()) {
				Message deleteMess;
				deleteMess = entM.merge(itMessage.next());
				entM.remove(deleteMess);
			}
			entM.remove(topicMerge);
		}
		entM.remove(hubMerge);
		entM.getTransaction().commit();
		return true;
	}

	@Override
	public boolean removeMessage(final Message message) {
		Message messMerge;
		entM.getTransaction().begin();
		messMerge = entM.merge(message);
		entM.remove(messMerge);
		entM.getTransaction().commit();
		return true;
	}

	@Override
	public boolean removeTopic(final Topic topic) {
		Topic topicMerge;
		entM.getTransaction().begin();
		topicMerge = entM.merge(topic);
		List<Message> mesMessages;
		mesMessages = topicMerge.getLesMessages();
		Iterator<Message> iter;
		iter = mesMessages.iterator();
		while (iter.hasNext()) {
			Message deleteMess;
			deleteMess = entM.merge(iter.next());
			entM.remove(deleteMess);
		}
		entM.remove(topicMerge);
		entM.getTransaction().commit();
		return true;
	}

	@Override
	public boolean updateHub(final Hub hub, final String titre) {
		Hub hubMerge;
		entM.getTransaction().begin();
		hubMerge = entM.merge(hub);
		hubMerge.setNomCommunaute(titre);
		entM.persist(hubMerge);
		entM.getTransaction().commit();
		return true;
	}

	public boolean updateMessage(final Message message, final String contenu) {
		Message messMerge;
		entM.getTransaction().begin();
		messMerge = entM.merge(message);
		messMerge.setContenu(contenu);
		entM.persist(messMerge);
		entM.getTransaction().commit();
		return true;
	}

	@Override
	public boolean updateTopic(final Topic topic, final String titre) {
		Topic topMerge;
		entM.getTransaction().begin();
		topMerge = entM.merge(topic);
		topMerge.setSujet(titre);
		entM.persist(topMerge);
		entM.getTransaction().commit();
		return true;
	}
}
