package fr.univartois.ili.fsnet.facade.iliforumtags;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

public class MessageTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;

	private Date dateBegin;
	private Date dateEnd;
	private EntiteSociale user;
	private Topic topic;
	private Iterator<Message> it;
	private IliForumFacade iff;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Topic getTopic(){
		return this.topic;
	}
	
	public void setTopic(Topic t){
		this.topic=t;
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
			it = iff.getListMessage(dateBegin, dateEnd).iterator();
		}

		else if (user != null) {
			it = iff.getListMessageByEntiteSocial(user).iterator();
		}

		else if(topic != null) {
			it = iff.getListMessageByTopic(topic).iterator();
		}
		else
			it = iff.getListMessage().iterator();
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private boolean updateContext() {
		if (it.hasNext()) {
			Message mess = it.next();

			pageContext.setAttribute(var, mess);
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
