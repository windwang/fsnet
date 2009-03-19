package fr.univartois.ili.fsnet.taglib;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Annonce;
import fr.univartois.ili.fsnet.entities.Information;
import fr.univartois.ili.fsnet.entities.Interaction;

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
	private List<Interaction> listInteraction;

	private EntityManagerFactory factory;
	private EntityManager em;

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
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		em = factory.createEntityManager();
		if (idChoisi != null) {

			Query requete = em
					.createQuery("SELECT a FROM Annonce a WHERE a.id=?1");
			requete.setParameter(1, idChoisi.intValue());
			an = (Iterator<Annonce>) requete.getResultList().iterator();

		} else if (nbAnnonce != null) { // Affiche les dernières annonces
			System.out.println("nb annonce " + nbAnnonce);
			Query requete = em
					.createQuery("SELECT a FROM Annonce a ORDER BY a.id DESC ");
			an = (Iterator<Annonce>) requete.getResultList().iterator();

		} else { // Affichage de la liste en entier

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
		if (nbAnnonce == null) {
			if (an.hasNext()) {
				Annonce ann;
				ann = an.next();

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

			if ((an.hasNext()) && (cpt < nbAnnonce.intValue())) {

				Annonce ann;
				ann = an.next();
				System.out.println("annonce " + ann.getNom() + " cpt " + cpt);
				if ((ann.getDateFinAnnonce().after(dateJour))
						&& ann.getVisible().equalsIgnoreCase("Y")) {

					cpt++;
					pageContext.setAttribute(var, ann);
					System.out.println("annonce envoyée OK " + ann.getNom()
							+ " cpt " + cpt);
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
		// em.close();
		return super.doEndTag();
	}
}
