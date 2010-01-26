package fr.univartois.ili.fsnet.taglib;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class DateJourTag extends TagSupport {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String var;
    private transient Date date;
    private transient int cpt = 0;
    private transient String dateJour;

    public String getVar() {
        return var;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    public int doStartTag() throws JspException {

        date = new Date();

        if (updateContext()) {
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }

    private boolean updateContext() {

        if (cpt == 0) {
            cpt++;
            Calendar calendar;
            calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            int jour;
            jour = calendar.get(GregorianCalendar.DAY_OF_MONTH);
            int mois;
            mois = calendar.get(GregorianCalendar.MONTH) + 1;
            int annee;
            annee = calendar.get(GregorianCalendar.YEAR);
            dateJour = jour + "/" + mois + "/" + annee;
            pageContext.setAttribute(var, dateJour);
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
        cpt = 0;
        pageContext.removeAttribute(var);

        return super.doEndTag();
    }
}
