package fr.univartois.ili.fsnet.taglib;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Interet;

/**
 * @author romuald druelle
 * 
 */
public class InteretTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "SELECT i FROM Interet i ORDER BY i.nomInteret ASC";

	private static final String FIND_ENTITE = "SELECT e FROM EntiteSociale e WHERE e.id=?1";

	private static final String DATABASE_NAME = "fsnetjpa";

	private Iterator<Interet> it;

	private List<Interet> interetsPerso;

	private String var;

	private Integer idLogin;

	public void setVar(final String var) {
		this.var = var;
	}

	public Integer getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Integer idLogin) {
		this.idLogin = idLogin;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query queryInt, queryEnt;
		List<Interet> lesInterets;

		queryInt = em.createQuery(FIND_ALL);
		lesInterets = (List<Interet>) queryInt.getResultList();
		it = lesInterets.iterator();

		queryEnt = em.createQuery(FIND_ENTITE);
		queryEnt.setParameter(1, idLogin.intValue());

		EntiteSociale ent = (EntiteSociale) queryEnt.getSingleResult();
		interetsPerso = ent.getLesinterets();

		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private void updateContext(Interet interet) {
		String checked = "";
		if (interetsPerso.contains(interet)) {
			checked = "checked";
		}
		pageContext.setAttribute("check", checked);
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
		pageContext.removeAttribute("check");
		return super.doEndTag();
	}
}
