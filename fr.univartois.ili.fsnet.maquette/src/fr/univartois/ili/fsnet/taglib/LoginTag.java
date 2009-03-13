package fr.univartois.ili.fsnet.taglib;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;

public class LoginTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;
	private Integer idLogin;
	private Iterator<EntiteSociale> entSoc;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setIdLogin(int idLogin) {
		System.out.println("Mise a jour id" + idLogin);
		this.idLogin = idLogin;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();
		System.out.println("Valeur id " + idLogin);
		if (idLogin != null) {
			System.out.println("Ici id = " + idLogin);
			Query requete = em
					.createQuery("SELECT e FROM EntiteSociale e WHERE e.id=?1");
			requete.setParameter(1, idLogin.intValue());
			entSoc = (Iterator<EntiteSociale>) requete.getResultList()
					.iterator();
		}

		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private boolean updateContext() {
		if (entSoc.hasNext()) {
			EntiteSociale entSociale = entSoc.next();
			System.out.println("Nom Prenom ==>" + entSociale.getNom() + " "
					+ entSociale.getPrenom());
			pageContext.setAttribute(var, entSociale);
			return true;
		}
		return false;
	}

	public int doAfterBody() throws JspException {

		if (updateContext()) {
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(var);

		return super.doEndTag();
	}
}
