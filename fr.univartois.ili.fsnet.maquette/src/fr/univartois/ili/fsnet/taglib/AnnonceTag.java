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
	private transient Integer idChoisi;
	private transient Date dateFin;
	private transient final Date dateJour = new Date();
	private transient String titre;
	private transient Iterator<Annonce> annonces;
	private transient Integer nbAnnonce;
	private transient int cpt;

	public Integer getNbAnnonce() {
		return nbAnnonce;
	}

	public void setNbAnnonce(final Integer nbAnnonce) {
		this.nbAnnonce = nbAnnonce;
	}

	public String getVar() {
		return var;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public void setIdChoisi(final int idChoisi) {
		this.idChoisi = idChoisi;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateBegin(final Date dateFin) {
		this.dateFin = dateFin;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory;
		EntityManager entM;
		cpt = 0;
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		entM = factory.createEntityManager();
		if (idChoisi != null) {

			Query requete;
			requete = entM.createQuery("SELECT a FROM Annonce a WHERE a.id=?1");
			requete.setParameter(1, idChoisi.intValue());
			annonces = (Iterator<Annonce>) requete.getResultList().iterator();

		} else if (nbAnnonce != null) { // Affiche les dernières annonces

			Query requete;
			requete = entM
					.createQuery("SELECT a FROM Annonce a ORDER BY a.id DESC ");
			annonces = (Iterator<Annonce>) requete.getResultList().iterator();

		} else { // Affichage de la liste en entier

			Query requete;
			requete = entM.createQuery("SELECT a FROM Annonce a");
			annonces = (Iterator<Annonce>) requete.getResultList().iterator();
		}
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(final String titre) {
		this.titre = titre;
	}

	private boolean updateContext() {
		if (nbAnnonce == null) {
			if (annonces.hasNext()) {
				Annonce ann;
				ann = annonces.next();

				if ((ann.getDateFinAnnonce().after(dateJour))
						&& ann.getVisible().equalsIgnoreCase("Y")) {

					pageContext.setAttribute("createur", ann.getCreateur()
							.getId());
					pageContext.setAttribute(var, ann);

				} else {
					updateContext();
				}
				return true;
			}
		} else {

			if ((annonces.hasNext()) && (cpt < nbAnnonce.intValue())) {

				Annonce ann;
				ann = annonces.next();

				if ((ann.getDateFinAnnonce().after(dateJour))
						&& ann.getVisible().equalsIgnoreCase("Y")) {

					cpt++;
					pageContext.setAttribute(var, ann);

				} else {
					updateContext();
				}

				return true;
			}
		}
		return false;
	}

	public int doAfterBody() throws JspException {
		pageContext.removeAttribute(var);
		pageContext.removeAttribute("createur");
		if (updateContext()) {
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(var);
		pageContext.removeAttribute("createur");

		return super.doEndTag();
	}
}
