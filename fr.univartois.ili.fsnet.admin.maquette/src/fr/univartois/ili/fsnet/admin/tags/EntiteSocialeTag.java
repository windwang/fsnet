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

import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * @author romuald druelle
 * 
 */
public class EntiteSocialeTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "SELECT e FROM EntiteSociale e ORDER BY e.nom ASC";
	
	private static final String FIND_BY_ID = "SELECT e FROM EntiteSociale e WHERE e.id = ?1 ";

	private static final String DATABASE_NAME = "fsnetjpa";

	private Iterator<EntiteSociale> it;
	private String var;
	private String id;

	public void setVar(final String var) {
		this.var = var;
	}
	
	public void setId(final String id) {
		this.id = id;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query query = null;
		List<EntiteSociale> lesEntites;

		if (id == null ){	
		query = em.createQuery(FIND_ALL);
		}
		else {
			query = em.createQuery(FIND_BY_ID);
			query.setParameter(1, Integer.parseInt(id));
		}
		lesEntites = query.getResultList();
		it = lesEntites.iterator();
		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private void updateContext(EntiteSociale entite) {
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
