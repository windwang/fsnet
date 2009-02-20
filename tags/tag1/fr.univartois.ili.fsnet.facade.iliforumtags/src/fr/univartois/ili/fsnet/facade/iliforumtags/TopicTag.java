package fr.univartois.ili.fsnet.facade.iliforumtags;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

public class TopicTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;

	private Date dateBegin;
	private Date dateEnd;
	private EntiteSociale user;
	private Hub hub;
	
	private Iterator<Topic> it;
	private IliForumFacade iff;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setHub(Hub myHub){
		this.hub=myHub;
	}
	
	public Hub getHub(){
		return this.hub;
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

	public EntiteSociale getUser() {
		return user;
	}

	public void setUser(EntiteSociale user) {
		this.user = user;
	}

	public int doStartTag() throws JspException {
		iff = new IliForumFacade();

		if (dateBegin != null && dateEnd != null) {
			it = iff.getListTopic(dateBegin, dateEnd).iterator();
		}

		else if (user != null) {
			it = iff.getListTopicByEntiteSociale(user).iterator();
		}

		else if(hub != null ){
			it=iff.getListTopicByHub(hub).iterator();
		}
		else
			it = iff.getListTopic().iterator();
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private boolean updateContext() {
		if (it.hasNext()) {
			Topic top = it.next();

			pageContext.setAttribute(var, top);
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
