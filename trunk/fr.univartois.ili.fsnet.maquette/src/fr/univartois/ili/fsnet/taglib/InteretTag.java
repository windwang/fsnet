package fr.univartois.ili.fsnet.taglib;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Interet;

/**
 * @author romuald druelle
 * 
 */
public class InteretTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "SELECT i FROM Interet i ORDER BY i.nomInteret ASC";

	private static final String DATABASE_NAME = "fsnetjpa";

	private Iterator<Interet> it;

	private String var;

	public void setVar(final String var) {
		this.var = var;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Interet> lesInterets;

		query = em.createQuery(FIND_ALL);
		lesInterets = query.getResultList();
		it = lesInterets.iterator();
		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private void updateContext(Interet interet) {
		pageContext.setAttribute(var, interet);
	}

	public int doAfterBody() throws JspException {
		if (it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		pageContext.removeAttribute(var);
		return super.doEndTag();
	}
}
