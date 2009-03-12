/**
 * 
 */
package fr.univartois.ili.fsnet.admin.tags;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Inscription;

/**
 * @author romuald druelle
 * 
 */
public class InscriptionTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "SELECT i FROM Inscription i ORDER BY i.entite.nom ASC";

	private static final String DATABASE_NAME = "fsnetjpa";

	private Iterator<Inscription> it;
	private String var;

	public void setVar(final String var) {
		this.var = var;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Inscription> lesInscriptions;

		query = em.createQuery(FIND_ALL);
		lesInscriptions = query.getResultList();
		it = lesInscriptions.iterator();
		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private void updateContext(Inscription entite) {
		pageContext.setAttribute(var, entite);
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
