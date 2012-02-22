package fr.univartois.ili.fsnet.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.core.LoggedUsersContainer;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

public class LoggedUsersTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoggedUsersContainer loggedUsers;
	private int userId;
	private String var;

	private List<SocialEntity> membersConnectedInteractable;

	private int index;

	public LoggedUsersTag() {

	}

	public LoggedUsersContainer getLoggedUsers() {
		return loggedUsers;
	}

	public void setLoggedUsers(LoggedUsersContainer loggedUsers) {
		this.loggedUsers = loggedUsers;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int doStartTag() throws JspException {
		if (var == null || loggedUsers == null) {
			return SKIP_BODY;
		}

		index = 0;
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade sgf = new SocialGroupFacade(em);
		SocialEntityFacade sef = new SocialEntityFacade(em);

		SocialEntity currentUser = sef.getSocialEntity(userId);
		if (currentUser != null) {
			Map<Integer, String> membersConnected = loggedUsers.getUsers();
			if (membersConnected.size() == 0) {
				return SKIP_BODY;
			}
			List<SocialEntity> userWithWhoCurrentUserCanInteract = sgf
					.getPersonsWithWhoMemberCanInteract(currentUser);
			membersConnectedInteractable = retainAllInteractablePersons(
					membersConnected, userWithWhoCurrentUserCanInteract);

			if (update()) {
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

	@Override
	public int doAfterBody() throws JspException {
		if (update()) {
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {

		return EVAL_PAGE;
	}

	private boolean update() {
		if (index < membersConnectedInteractable.size()) {
			pageContext.setAttribute(var,
					membersConnectedInteractable.get(index++));
			return true;
		}
		return false;
	}

	private List<SocialEntity> retainAllInteractablePersons(
			Map<Integer, String> connectedPersons,
			List<SocialEntity> interactablePersons) {
		List<SocialEntity> result = new ArrayList<SocialEntity>();
		for (SocialEntity se : interactablePersons) {
			if (connectedPersons.containsKey(se.getId())) {
				result.add(se);
			}
		}
		return result;
	}

}
