package fr.univartois.ili.fsnet.taglib;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Manifestation;

public class ManifestationTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;
	
	
	private Iterator<Manifestation> manif;
	private Integer nbAnnonce;
	private int cpt;

	public Integer getNbAnnonce() {
		return nbAnnonce;
	}

	public void setNbAnnonce(Integer nbAnnonce) {
		this.nbAnnonce = nbAnnonce;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

		public int doStartTag() throws JspException {
			
			cpt=0;
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();

		if (nbAnnonce != null) {
			// Limite le nombre d'annonces

			Query requete = em
					.createQuery("SELECT m FROM Manifestation m ");
			

			manif = (Iterator<Manifestation>) requete.getResultList().iterator();

		} else {

			Query requete = em.createQuery("SELECT m FROM Manifestation m");
			manif = (Iterator<Manifestation>) requete.getResultList().iterator();
		}
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

		private boolean updateContext() {
			
		if (nbAnnonce == null) {
			if (manif.hasNext()) {
				Manifestation man;
				man = manif.next();
				pageContext.setAttribute(var, man);
				return true;
			}
		} else {
			if ((manif.hasNext()) && (cpt< nbAnnonce.intValue())) {
				cpt++;
				Manifestation man;
				man = manif.next();
				pageContext.setAttribute(var, man);
				return true;

			}
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