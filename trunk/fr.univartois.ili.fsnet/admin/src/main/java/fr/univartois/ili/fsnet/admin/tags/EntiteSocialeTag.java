/**
 * 
 */
package fr.univartois.ili.fsnet.admin.tags;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * @author romuald druelle
 * 
 */
public class EntiteSocialeTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private static final String FIND_ALL = "SELECT e FROM EntiteSociale e ORDER BY e.nom ASC";
	private static final String FIND_BY_ID = "SELECT e FROM EntiteSociale e WHERE e.id = ?1 ";
	private static final String DATABASE_NAME = "fsnetjpa";
	private Iterator<EntiteSociale> it;
	private String var;
	private String id;
	private String parametre;
	private String filtre;
	private DateFormat formatter = new SimpleDateFormat("dd/MM/yy");

	@Override
	public int doStartTag() throws JspException {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
        TypedQuery<EntiteSociale> query = null;
		List<EntiteSociale> lesEntites = null;
		if (filtre != null) {
			lesEntites = recherche();
		} else {
			if (id == null) {

				query = em.createQuery(FIND_ALL, EntiteSociale.class);
			} else {
				query = em.createQuery(FIND_BY_ID, EntiteSociale.class);
				query.setParameter(1, Integer.parseInt(id));
			}
			lesEntites = query.getResultList();
		}
		if (lesEntites == null) {
			it = null;
		} else {
			it = lesEntites.iterator();
		}
		if (it != null && it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	@Override
	public int doAfterBody() throws JspException {
		if (it.hasNext()) {
			updateContext(it.next());
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(var);
		return SKIP_BODY;
	}

	public List<EntiteSociale> recherche() {
		if (filtre.equals("nom")) {
			return searchNom(parametre);
		}
		if (filtre.equals("prenom")) {
			return searchPrenom(parametre);
		}
		if (filtre.equals("dateEntree")) {
			try {
				return searchDateEntree(formatter.parse(parametre));
			} catch (ParseException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "erreur de date",
						e);
			}
		}
		return null;
	}

	private void updateContext(EntiteSociale entite) {
		pageContext.setAttribute(var, entite);
	}

	public List<EntiteSociale> searchNom(String param) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		TypedQuery<EntiteSociale> query = em.createQuery(
				"SELECT e FROM EntiteSociale e WHERE e.nom=?1",
				EntiteSociale.class);
		query.setParameter(1, param);
		return (List<EntiteSociale>) query.getResultList();
	}

	public List<EntiteSociale> searchPrenom(String param) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		TypedQuery<EntiteSociale> query = em.createQuery(
				"SELECT e FROM EntiteSociale e WHERE e.prenom=?1",
				EntiteSociale.class);
		query.setParameter(1, param);
		return (List<EntiteSociale>) query.getResultList();

	}

	public List<EntiteSociale> searchDateEntree(Date param) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(DATABASE_NAME);
		EntityManager em = factory.createEntityManager();
		TypedQuery<EntiteSociale> query = em.createQuery(
				"SELECT e FROM EntiteSociale e WHERE e.dateEntree=?1",
				EntiteSociale.class);
		query.setParameter(1, param);
		return (List<EntiteSociale>) query.getResultList();

	}

	public void setFiltre(final String filtre) {
		this.filtre = filtre;
	}

	public void setParametre(final String parametre) {
		this.parametre = parametre;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
