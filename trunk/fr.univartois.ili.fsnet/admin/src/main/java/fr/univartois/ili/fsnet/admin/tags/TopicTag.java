package fr.univartois.ili.fsnet.admin.tags;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Topic;

public class TopicTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "SELECT t FROM Topic t WHERE t.hub = ?1 ORDER BY t.sujet";

	private static final String DATABASE_NAME = "fsnetjpa";

	private Iterator<Topic> it;
	
	private String var;
	
	private Hub hub;

	public void setVar(final String var) {
		this.var = var;
	}

	public void setHub(final Hub hub){
		this.hub = hub;
	}
	
	@Override
	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Topic> lesTopics;

		query = em.createQuery(FIND_ALL);
		query.setParameter(1, hub);
		lesTopics = query.getResultList();
		it = lesTopics.iterator();
		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	private void updateContext(Topic topic) {
		pageContext.setAttribute(var, topic);
		pageContext.setAttribute("nbMessages",topic.getLesMessages().size());
	}

	@Override
	public int doAfterBody() throws JspException {
		if (it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(var);
		pageContext.removeAttribute("nbMessages");
		return SKIP_BODY;
	}

}