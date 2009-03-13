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

	private static final String FIND_LAST_BY_STATE = "SELECT i FROM Inscription i WHERE i.etat=?1 ";
	// private static final String FIND_LAST_BY_STATE =
	// "SELECT i FROM Inscription i WHERE i.etat=?1 ORDER BY i.id DESC LIMIT 5";

	private static final String DATABASE_NAME = "fsnetjpa";

	private static final String WAITING = "En attente d'inscription";

	private static final String REGISTERED = "Inscrit";

	private static final String CONDITION_AWAITING_REGISTRATION = "ORDER BY i.entite.nom ASC";

	private static final String CONDITION_REGISTERED = "ORDER BY i.id DESC";

	private static final int MAX_MEMBERS = 7;

	private Iterator<Inscription> it;
	private String var;
	private String etat;

	public void setVar(final String var) {
		this.var = var;
	}

	public void setEtat(final String etat) {
		this.etat = etat;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Inscription> lesInscriptions;
		String condition = null;

		if (etat == null) {
			query = em.createQuery(FIND_ALL);
			lesInscriptions = query.getResultList();
		} else {
			if (REGISTERED.equals(etat)) {
				condition = CONDITION_REGISTERED;
				query = em.createQuery(FIND_LAST_BY_STATE + condition);
				query.setParameter(1, etat);
				lesInscriptions = query.setFirstResult(0).setMaxResults(
						MAX_MEMBERS).getResultList();
			} else {
				condition = CONDITION_AWAITING_REGISTRATION;
				query = em.createQuery(FIND_LAST_BY_STATE + condition);
				query.setParameter(1, etat);
				lesInscriptions = query.getResultList();
			}
			// query = em.createQuery(FIND_LAST_BY_STATE + condition);
			// query.setParameter(1, etat);
		}
		// lesInscriptions = query.setFirstResult(MAX_MEMBERS).getResultList();
		it = lesInscriptions.iterator();
		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private void updateContext(Inscription inscription) {
		pageContext.setAttribute(var, inscription);
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
