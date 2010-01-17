package fr.univartois.ili.fsnet.taglib;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;

public class LoginTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private String var;
    private transient Integer idLogin;
    private transient Iterator<EntiteSociale> entSoc;

    public String getVar() {
        return var;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    public void setIdLogin(final int idLogin) {
        this.idLogin = idLogin;
    }

    public int doStartTag() throws JspException {
        EntityManagerFactory factory;
        factory = Persistence.createEntityManagerFactory("fsnetjpa");
        EntityManager entM;
        entM = factory.createEntityManager();
        if (idLogin != null) {

            Query requete;
            requete = entM.createQuery("SELECT e FROM EntiteSociale e WHERE e.id=?1");
            requete.setParameter(1, idLogin.intValue());
            entSoc = (Iterator<EntiteSociale>) requete.getResultList().iterator();
        }

        if (updateContext()) {
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

    private boolean updateContext() {
        String checkedM = "";
        String checkedF = "";
        if (entSoc.hasNext()) {
            EntiteSociale entSociale;
            entSociale = entSoc.next();

            if ("M".equalsIgnoreCase(entSociale.getSexe())) {
                checkedM = "checked";
                pageContext.setAttribute("civilite", "M.");

            }
            if ("F".equalsIgnoreCase(entSociale.getSexe())) {
                checkedF = "checked";
                pageContext.setAttribute("civilite", "Mme.");
            }

            pageContext.setAttribute("checkM", checkedM);
            pageContext.setAttribute("checkF", checkedF);
            pageContext.setAttribute(var, entSociale);

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
        pageContext.removeAttribute("checkM");
        pageContext.removeAttribute("checkF");
        pageContext.removeAttribute("civilite");
        return super.doEndTag();
    }
}
