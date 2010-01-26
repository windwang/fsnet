/**
 * 
 */
package fr.univartois.ili.fsnet.admin.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Interest;

/**
 * @author romuald druelle
 * 
 */
public class InteretTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private static final String FIND_ALL = "SELECT i FROM Interest i ORDER BY i.name ASC";
    private static final String FIND_BY_NOMINTERET = "SELECT i FROM Interest i WHERE i.name=?1";
    private static final String DATABASE_NAME = "fsnetjpa";
    private static final int SIZE = 70;
    private Iterator<Interest> it;
    private String var;
    private String parametre;

    @Override
    public int doStartTag() throws JspException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager em = factory.createEntityManager();
        TypedQuery<Interest> query;
        List<Interest> lesInterets;

        if (parametre != null) {
            lesInterets = recherche(parametre);

        } else {
            query = em.createQuery(FIND_ALL, Interest.class);
            lesInterets = query.getResultList();
        }
        if ((lesInterets == null) || (lesInterets.isEmpty())) {
            pageContext.setAttribute("vide", "vide");
        } else {
            it = lesInterets.iterator();
            pageContext.setAttribute("vide", "nonVide");
        }
        it = lesInterets.iterator();
        if (it != null && it.hasNext()) {
            updateContext(it.next());
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

    private void updateContext(Interest interet) {
        pageContext.setAttribute(var, interet);
        String intitule = interet.getName();
        if (intitule.length() < SIZE) {
            pageContext.setAttribute("svarInteret", intitule);
        } else {
            pageContext.setAttribute("svarInteret", intitule.substring(0,
                    SIZE - 1)
                    + "...");
        }
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

    public List<Interest> recherche(String param) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager em = factory.createEntityManager();
        List<Interest> lesI;
        TypedQuery<Interest> query = em.createQuery(FIND_BY_NOMINTERET, Interest.class);
        query.setParameter(1, param);
        lesI = query.getResultList();
        return lesI;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    public void setParametre(final String parametre) {
        this.parametre = parametre;
    }
}
