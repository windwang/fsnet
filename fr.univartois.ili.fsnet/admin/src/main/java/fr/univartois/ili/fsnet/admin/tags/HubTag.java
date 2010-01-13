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

public class HubTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "SELECT h FROM Hub h ORDER BY h.nomCommunaute";

	private static final String DATABASE_NAME = "fsnetjpa";

	private Iterator<Hub> it;
	
	private String var;

	public void setVar(final String var) {
		this.var = var;
	}

	@Override
	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Hub> lesHubs;
		query = em.createQuery(FIND_ALL);
		lesHubs = query.getResultList();
		it = lesHubs.iterator();
		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private void updateContext(Hub entite) {
		pageContext.setAttribute(var, entite);
		pageContext.setAttribute("nbTopics", entite.getLesTopics().size());
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
		pageContext.removeAttribute("nbTopics");
		return SKIP_BODY;
	}

}

