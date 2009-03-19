package fr.univartois.ili.fsnet.facade.iliforumtags;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.eclipse.persistence.internal.jpa.parsing.SubstringNode;

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

	private Iterator<HubDTO> it;

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
		IliForumFacade iff = IliForumFacade.getInstance();
		List<HubDTO> lHubDTO = new ArrayList<HubDTO>();
		List<Hub> lHub;

		if (dateBegin != null && dateEnd != null) {
			lHub = iff.getListHub(dateBegin, dateEnd);
		}

		else if (decideur != null) {
			lHub = iff.getListHubByEntiteSociale(decideur);
		}

		else
			lHub = iff.getListHub();

		for (Hub hub : lHub) {
			lHubDTO.add(new HubDTO(hub));
		}

		it = lHubDTO.iterator();
		if (updateContext()) {
			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	private boolean updateContext() {
		if (it.hasNext()) {
			HubDTO hubDTO;
			hubDTO = it.next();

			pageContext.setAttribute(var, hubDTO);

			if (hubDTO.getHub().getNomCommunaute().length() <= 7) {
				pageContext.setAttribute("svar", hubDTO.getHub()
						.getNomCommunaute());
			} else {
				pageContext.setAttribute("svar", hubDTO.getHub()
						.getNomCommunaute().substring(0, 7)
						+ "...");
			}

			if (hubDTO.getLastMessage() != null) {
				if (hubDTO.getLastMessage().getTopic().getSujet().length() <= 17) {
					pageContext.setAttribute("svar2", hubDTO.getLastMessage()
							.getTopic().getSujet());
				} else {
					pageContext.setAttribute("svar2", hubDTO.getLastMessage()
							.getTopic().getSujet().substring(0, 17)
							+ "...");
				}
			}
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
