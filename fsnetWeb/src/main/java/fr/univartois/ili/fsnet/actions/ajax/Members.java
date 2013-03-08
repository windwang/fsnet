package fr.univartois.ili.fsnet.actions.ajax;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class Members extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private String searchText;

	@Override
	public String execute() throws Exception {
		if (form != null) {
			int index = searchText.lastIndexOf(',');
			String completeUser = (index == -1) ? ("") : (searchText.substring(
					0, index + 1));
			String searchText = (index == -1) ? (searchText) : (searchText
					.substring(index + 1));
			EntityManager em = PersistenceProvider.createEntityManager();
			SocialEntityFacade sef = new SocialEntityFacade(em);
			SocialGroupFacade sgf = new SocialGroupFacade(em);
			Set<SocialEntity> listSE = sef.searchSocialEntity(searchText);
			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			List<SocialEntity> membersWithCurrentMemberCanInteract = sgf
					.getPersonsWithWhoMemberCanInteract(member);
			em.close();
			listSE.retainAll(membersWithCurrentMemberCanInteract);
			request.setAttribute("matchesSocialEntity", listSE);
			request.setAttribute("completeUsers", completeUser);
		}
		return SUCCESS;
	}

	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * @param searchText
	 *            the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
