package fr.univartois.ili.fsnet.iliforumtags;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.forum.iliforum.IliForumFacade;

public class MessageTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String var;

	private Date dateBegin;
	private Date dateEnd;
	private transient EntiteSociale user;
	private transient Topic topic;
	private transient Iterator<MessageDTO> itetrator;

	public String getVar() {
		return var;
	}

	public void setVar(final String var) {
		this.var = var;
	}

	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(final Topic top) {
		this.topic = top;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(final Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(final Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public EntiteSociale getUser() {
		return user;
	}

	public void setUser(final EntiteSociale user) {
		this.user = user;
	}

	public int doStartTag() throws JspException {
		IliForumFacade iff;
		iff = IliForumFacade.getInstance();
		List<MessageDTO> lMessDTO;
		lMessDTO = new ArrayList<MessageDTO>();
		List<Message> lMess;
		if (dateBegin != null && dateEnd != null) {
			lMess = iff.getListMessage(dateBegin, dateEnd);
		}

		else if (user != null) {
			lMess = iff.getListMessageByEntiteSocial(user);
		}

		else if (topic != null) {
			lMess = iff.getListMessageByTopic(topic);
		} else
			lMess = iff.getListMessage();

		for (Message mess : lMess) {
			lMessDTO.add(new MessageDTO(mess));
		}

		itetrator = lMessDTO.iterator();
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private boolean updateContext() {
		if (itetrator.hasNext()) {
			MessageDTO messDTO;
			messDTO = itetrator.next();

			pageContext.setAttribute(var, messDTO);
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
		return super.doEndTag();
	}
}
