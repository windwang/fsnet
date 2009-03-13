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
	private Integer idChoisi;
	private Date dateFin;
	private Date dateJour = new Date();
	private String titre;
	private Iterator<Annonce> an;
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

	public void setIdChoisi(int idChoisi) {
		this.idChoisi = idChoisi;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateBegin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int doStartTag() throws JspException {
		cpt = 0;
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("fsnetjpa");
		EntityManager em = factory.createEntityManager();

		if (idChoisi != null) {

			Query requete = em
					.createQuery("SELECT a FROM Annonce a WHERE a.id=?1");
			requete.setParameter(1, idChoisi.intValue());
			an = (Iterator<Annonce>) requete.getResultList().iterator();
		} else if (nbAnnonce != null) { // Limite le nombre d'annonces
			System.out.println("nb annonce " + nbAnnonce);
			Query requete = em
					.createQuery("SELECT a FROM Annonce a ORDER BY a.id DESC ");
			an = (Iterator<Annonce>) requete.getResultList().iterator();

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

	/**
	 * while (it.hasNext()) { Annonce a = (Annonce) it.next();
	 * System.out.println("                          dateJour " + aujourdhui +
	 * " date ann " + a.getDateFinAnnonce()); if
	 * (a.getDateFinAnnonce().before(aujourdhui)) { System.out
	 * .println("                           je supprime " + a.getNom());
	 * it.remove(); }
	 * 
	 * }
	 */

	private boolean updateContext() {
		if (nbAnnonce == null) {
			if (an.hasNext()) {
				Annonce ann;
				ann = an.next();
				if (ann.getDateFinAnnonce().before(dateJour)) {
					an.remove();
					return true;
				}
				pageContext.setAttribute(var, ann);
				return true;
			}
		} else {
			if ((an.hasNext()) && (cpt < nbAnnonce.intValue())) {

				Annonce ann;
				ann = an.next();
				if (ann.getDateFinAnnonce().before(dateJour)) {
					an.remove();
					return true;
				}
				cpt++;
				pageContext.setAttribute(var, ann);
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
