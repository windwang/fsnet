package fr.univartois.ili.fsnet.facade.iliforumtags;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

public class HubTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;

	private Date dateBegin;
	private Date dateEnd;
	private EntiteSociale decideur;
	
	private Iterator<Hub> it;
	private IliForumFacade iff;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public EntiteSociale getDecideur() {
		return decideur;
	}

	public void setDecideur(EntiteSociale decideur) {
		this.decideur = decideur;
	}

	public int doStartTag() throws JspException {
		System.err.println("je suis ds la balise Hub");
		iff = new IliForumFacade();

		
		if (dateBegin != null && dateEnd != null) {
			it = iff.getListHub(dateBegin, dateEnd).iterator();
		}

		else if (decideur != null) {
			it = iff.getListHubByEntiteSociale(decideur).iterator();
		}

		else
			it = iff.getListHub().iterator();
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private boolean updateContext() {
		if (it.hasNext()) {
			Hub hub;
			hub = it.next();

			pageContext.setAttribute(var, hub);
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
		if (iff != null) {
			iff.close();
		}
		return super.doEndTag();
	}
}
