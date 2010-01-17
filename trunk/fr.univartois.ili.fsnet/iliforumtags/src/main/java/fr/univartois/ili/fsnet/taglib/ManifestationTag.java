package fr.univartois.ili.fsnet.taglib;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.Manifestation;

public class ManifestationTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private transient String var;
    private transient Iterator<Manifestation> manif;
    private transient Integer nbEven;
    private transient Integer idEven;
    private transient int cpt;

    public void setNbEven(final Integer nbEven) {
        this.nbEven = nbEven;
    }

    public void setIdEven(final Integer idEven) {
        this.idEven = idEven;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    public int doStartTag() throws JspException {

        cpt = 0;
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("fsnetjpa");
        EntityManager entM;
        entM = factory.createEntityManager();
        if (idEven != null) {

            Query requete;
            requete = entM.createQuery("SELECT m FROM Manifestation m WHERE m.id=?1 AND m.visible='Y' ");
            requete.setParameter(1, idEven.intValue());
            manif = (Iterator<Manifestation>) requete.getResultList().iterator();
        } else if (nbEven != null) {
            // Limite le nombre d'evenements

            Query requete;
            requete = entM.createQuery("SELECT m FROM Manifestation m WHERE  m.visible='Y' ORDER BY m.id DESC ");

            manif = (Iterator<Manifestation>) requete.getResultList().iterator();

        } else {

            Query requete;
            requete = entM.createQuery("SELECT m FROM Manifestation m WHERE  m.visible='Y'");
            manif = (Iterator<Manifestation>) requete.getResultList().iterator();
        }
        if (updateContext()) {
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

    private boolean updateContext() {

        if (nbEven == null) {
            if (manif.hasNext()) {
                Manifestation man;
                man = manif.next();
                pageContext.setAttribute(var, man);
                return true;
            }
        } else {
            if ((manif.hasNext()) && (cpt < nbEven.intValue())) {
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
