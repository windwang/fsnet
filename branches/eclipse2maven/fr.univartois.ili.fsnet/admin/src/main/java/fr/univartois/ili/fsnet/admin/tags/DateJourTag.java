package fr.univartois.ili.fsnet.admin.tags;

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
	private Date date;
	private int cpt = 0;
	private String dateJour;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
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
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			int jour = calendar.get(GregorianCalendar.DAY_OF_MONTH);
			int mois = calendar.get(GregorianCalendar.MONTH) + 1;
			int année = calendar.get(GregorianCalendar.YEAR);
			dateJour = jour + "/" + mois + "/" + année;
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
