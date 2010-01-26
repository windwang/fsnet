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
    private transient Iterator<Interet> iterator;
    private transient List<Interet> interetsPerso;
    private transient String var;
    private Integer idLogin;

    public void setVar(final String var) {
        this.var = var;
    }

    public Integer getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(final Integer idLogin) {
        this.idLogin = idLogin;
    }

    public int doStartTag() throws JspException {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        EntityManager entM;
        entM = factory.createEntityManager();
        Query queryInt, queryEnt;
        List<Interet> lesInterets;

        queryInt = entM.createQuery(FIND_ALL);
        lesInterets = (List<Interet>) queryInt.getResultList();
        iterator = lesInterets.iterator();

        queryEnt = entM.createQuery(FIND_ENTITE);
        queryEnt.setParameter(1, idLogin.intValue());

        EntiteSociale ent;
        ent = (EntiteSociale) queryEnt.getSingleResult();
        interetsPerso = ent.getLesinterets();

        if (iterator != null && iterator.hasNext()) {
            updateContext(iterator.next());
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

    private void updateContext(final Interet interet) {
        String checked = "";
        if (interetsPerso.contains(interet)) {
            checked = "checked";
        }
        pageContext.setAttribute("check", checked);
        pageContext.setAttribute(var, interet);
    }

    public int doAfterBody() throws JspException {
        if (iterator.hasNext()) {
            updateContext(iterator.next());
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
