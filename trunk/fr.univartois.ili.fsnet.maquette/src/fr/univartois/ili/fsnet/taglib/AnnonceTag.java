package fr.univartois.ili.fsnet.taglib;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Annonce;


public class AnnonceTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	private Date dateFin;
	private String titre;

	private Iterator<Annonce> an;

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateBegin(Date dateFin) {
		this.dateFin = dateFin;
	}

	// public Date getDateEnd() {
	// return dateEnd;
	// }

	// public void setDateEnd(Date dateEnd) {
	// this.dateEnd = dateEnd;
	// }

	public int doStartTag() throws JspException {
		System.err.println("je suis ds la balise Hub");
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();

		if (dateFin != null) {
			Query requete = em.createQuery("SELECT a FROM Annonce a");
			an = (Iterator<Annonce>) requete.getResultList();
		} else {
			Query requete = em.createQuery("SELECT a FROM Annonce a");
			an = (Iterator<Annonce>) requete.getResultList().iterator();
		}
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	private boolean updateContext() {
		if (an.hasNext()) {
			Annonce ann;
			ann = an.next();

			pageContext.setAttribute(var, ann);
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
