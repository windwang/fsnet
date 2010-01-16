package fr.univartois.ili.fsnet.admin.tags;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.univartois.ili.fsnet.admin.tags.utils.AbstractGenericIteratorTag;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Topic;

/**
 * @author m
 *
 */
/**
 * @author m
 *
 */
/**
 * @author m
 *
 */
/**
 * @author m
 * 
 */
public class TopicTag extends AbstractGenericIteratorTag<Topic> {

	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "SELECT t FROM Topic t WHERE t.hub = ?1 ORDER BY t.sujet";

	private static final String DATABASE_NAME = "fsnetjpa";

	private static final EntityManagerFactory factory = Persistence
			.createEntityManagerFactory(DATABASE_NAME);

	private String var;

	private Hub hub;

	/**
	 * @return "topic" as the default var name
	 */
	@Override
	public String getDefaultVarName() {
		return "topic";
	}

	@Override
	protected Iterator<Topic> initIterator() {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Topic> lesTopics;
		query = em.createQuery(FIND_ALL);
		query.setParameter(1, hub);
		lesTopics = query.getResultList();
		em.close();
		return lesTopics.iterator();
	}

	public String getVar() {
		return var;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public Hub getHub() {
		return hub;
	}

	public void setHub(final Hub hub) {
		this.hub = hub;
	}

}