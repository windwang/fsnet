/**
 * 
 */
package fr.univartois.ili.fsnet.admin.tags;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	private static final String FIND_LAST_BY_STATE = "SELECT i FROM Inscription i WHERE i.etat = ?1";

	private static final String FIND_BY_ENTITY = "SELECT i FROM Inscription i WHERE i.entite.id = ?1 ";

	private static final String DATABASE_NAME = "fsnetjpa";

	private static final String WAITING = "En attente d'inscription";

	private static final String REGISTERED = "Inscrit";

	private static final String CONDITION_AWAITING_REGISTRATION = "ORDER BY i.entite.nom ASC";

	private static final String CONDITION_REGISTERED = "ORDER BY i.id DESC";

	private static final int MAX_MEMBERS = 7;

	private Iterator<Inscription> it;
	private String var;
	private String etat;
	private String entite;
	private String parametre;
	private String filtre;

	private DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

	public void setFiltre(final String filtre) {
		this.filtre = filtre;
	}

	public void setParametre(final String parametre) {
		this.parametre = parametre;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public void setEtat(final String etat) {
		this.etat = etat;
	}

	public void setEntite(final String entite) {
		this.entite = entite;
	}

	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Inscription> lesInscriptions;
		String condition = null;
		if (filtre != null)
			lesInscriptions = recherche();
		else {
			if (etat == null) {
				if (entite == null) {
					query = em.createQuery(FIND_ALL);

				} else {
					query = em.createQuery(FIND_BY_ENTITY);
					query.setParameter(1, Integer.parseInt(entite));
				}
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
			}
		}

		if ((lesInscriptions == null) || (lesInscriptions.isEmpty())) {
			pageContext.setAttribute("vide", "vide");
		} else {
			it = lesInscriptions.iterator();
			pageContext.setAttribute("vide", "nonVide");
		}
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
		// pageContext.removeAttribute("vide");
		return super.doEndTag();
	}

	public List<Inscription> recherche() {
		if (filtre.equals("nom")) {
			return searchNom(parametre);
		}
		if (filtre.equals("prenom")) {
			return searchPrenom(parametre);
		}
		if (filtre.equals("dateEntree")) {
			try {
				return searchDateEntree(formatter.parse(parametre));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				System.out.println("erreur de date");
			}
		}
		return null;
	}

	public List<Inscription> searchNom(String param) {
		System.out.println("je passe dans searchNom");
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		List<Inscription> lesES = new ArrayList<Inscription>();
		Query query = em
				.createQuery("SELECT i FROM Inscription i WHERE i.entite.nom=?1");
		query.setParameter(1, param);

		System.out.println("Taille pour nom " + query.getResultList().size());
		lesES = query.getResultList();
		return lesES;
	}

	public List<Inscription> searchPrenom(String param) {
		System.out.println("je passe dans searchPrenom");
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		List<Inscription> lesES = new ArrayList<Inscription>();
		Query query = em
				.createQuery("SELECT i FROM Inscription i WHERE i.entite.prenom=?1");
		query.setParameter(1, param);
		lesES = query.getResultList();
		return lesES;

	}

	public List<Inscription> searchDateEntree(Date param) {
		System.out.println("je passe dans searchDateEntree");
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		List<Inscription> lesES = new ArrayList<Inscription>();
		Query query = em
				.createQuery("SELECT i FROM Inscription i WHERE i.entite.dateEntree=?1");
		query.setParameter(1, param);
		lesES = query.getResultList();
		return lesES;

	}

}
