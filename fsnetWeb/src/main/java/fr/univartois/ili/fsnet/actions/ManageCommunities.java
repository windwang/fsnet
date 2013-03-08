package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 * Execute CRUD Actions (and more) for the entity community
 * 
 * @author Audrey Ruellan and Cerelia Besnainou
 */
public class ManageCommunities extends ActionSupport implements
		CrudAction,ServletRequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SUCCES_ACTION_NAME = "success";
	private static final String UNAUTHORIZED_ACTION_NAME = "unauthorized";
	private static final String FAILED_ACTION_NAME = "failed";

	private String newCommunityName;
	private String oldCommunityName;
	private String communityName;
	private Integer communityId;
	private String searchText ;
	private String searchYourText;
	private String[] selectedInterests;
	private String[] selectedCommunities;
	private HttpServletRequest request;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#create(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String create()
			throws Exception {
		/*HttpSession session = request.getSession(true);*/
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);

		addRightToRequest(request);
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		if (!fascade.isAuthorized(creator, Right.CREATE_COMMUNITY)) {
			em.close();
			return UNAUTHORIZED_ACTION_NAME;
		}

		CommunityFacade communityFacade = new CommunityFacade(em);

		boolean doesNotExists = false;
		try {
			communityFacade.getCommunityByName(communityName);
		} catch (NoResultException e) {
			doesNotExists = true;
		}

		try {
			if (doesNotExists) {
		
				InterestFacade fac = new InterestFacade(em);
				List<Interest> interests = new ArrayList<Interest>();
				int currentId;
				for (currentId = 0; currentId < selectedInterests.length; currentId++) {
					interests.add(fac.getInterest(Integer
							.valueOf(selectedInterests[currentId])));
				}

				em.getTransaction().begin();
				Community createdCommunity = communityFacade.createCommunity(
						creator,communityName );
				InteractionFacade ifacade = new InteractionFacade(em);
				ifacade.addInterests(createdCommunity, interests);
				em.getTransaction().commit();
			} else {
				addFieldError("communityName","communities.alreadyExists");
			}
		} catch (NumberFormatException e) {
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		communityName="";

		return SUCCES_ACTION_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#display(org.apache.struts.
	 * action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String display()
			throws Exception {
		throw new UnsupportedOperationException("Not supported yet");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#delete(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String delete()
			throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			addRightToRequest(request);
			CommunityFacade communityFacade = new CommunityFacade(em);
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			InteractionFacade interactionFacade = new InteractionFacade(em);
			em.getTransaction().begin();
			Community community = communityFacade.getCommunity(communityId);
			interactionFacade.deleteInteraction(user, community);
			community.getCreator().getInteractions().remove(community);
			if (community.getParentCommunity() != null) {
				community.getParentCommunity().getChildrenCommunities()
						.remove(community);
			}
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return FAILED_ACTION_NAME;
		} finally {
			em.close();
		}

		return SUCCES_ACTION_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#modify(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String modify()
			throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		CommunityFacade facade = new CommunityFacade(em);
		boolean doesNotExist = false;
		Community community = facade.getCommunityByName(communityName);

		if (community != null) {
			try {
				facade.getCommunityByName(newCommunityName);
			} catch (NoResultException e) {
				doesNotExist = true;
			}

			if (doesNotExist) {
				try {
					em.getTransaction().begin();
					facade.modifyCommunity(newCommunityName, community);
					em.getTransaction().commit();
				} catch (DatabaseException ex) {
					addFieldError("newCommunityName","communities.alreadyExists");
					em.close();

					return FAILED_ACTION_NAME;
				}
			} else {
				addFieldError("newCommunityName","communities.alreadyExists");
				em.close();

				return FAILED_ACTION_NAME;
			}
		}

		em.close();

		setOldCommunityName("");
		newCommunityName="";

		return SUCCES_ACTION_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.actions.CrudAction#search(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public String search()
			throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> result = null;
	
		searchText = "";
		CommunityFacade communityFacade = new CommunityFacade(em);
		addRightToRequest(request);
		em.getTransaction().begin();
		result = communityFacade.searchCommunity(searchText);
		/* filter list communities */
		if (result != null && !result.isEmpty()) {
			FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
					em);
			SocialEntity se = UserUtils.getAuthenticatedUser(request);
			result = filter.filterInteraction(se, result);
		}
		em.getTransaction().commit();
		em.close();

		request.setAttribute("communitiesSearch", result);
		return SUCCES_ACTION_NAME;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String searchYourCommunities() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> result = null;
		String pattern = "";
		SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);
		addRightToRequest(request);

			pattern =searchYourText;

		em.getTransaction().begin();

		TypedQuery<Community> query = em
				.createQuery(
						"SELECT community FROM Community community WHERE community.title LIKE :pattern AND community.creator= :creator",
						Community.class);
		query.setParameter("pattern", "%" + pattern + "%");
		query.setParameter("creator", creator);
		result = query.getResultList();

		em.getTransaction().commit();
		em.close();

		request.setAttribute("myCommunities", result);

		return SUCCES_ACTION_NAME;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String deleteMulti(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			
			addRightToRequest(request);
			SocialEntity authenticatedUser = UserUtils.getAuthenticatedUser(
					request, em);
			CommunityFacade communityFacade = new CommunityFacade(em);
			InteractionFacade interactionFacade = new InteractionFacade(em);

			em.getTransaction().begin();
			for (int i = 0; i < selectedCommunities.length; i++) {
				Community community = communityFacade.getCommunity(Integer
						.valueOf(selectedCommunities[i]));
				interactionFacade.deleteInteraction(authenticatedUser,
						community);

				community.getCreator().getInteractions().remove(community);
				if (community.getParentCommunity() != null) {
					community.getParentCommunity().getChildrenCommunities()
							.remove(community);
				}
			}
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}

		return SUCCES_ACTION_NAME;
	}

	/**
	 * @param request
	 */
	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightCreateCommunity = Right.CREATE_COMMUNITY;
		request.setAttribute("rightCreateCommunity", rightCreateCommunity);
		request.setAttribute("socialEntity", socialEntity);
	}

	public String getOldCommunityName() {
		return oldCommunityName;
	}

	public void setOldCommunityName(String oldCommunityName) {
		this.oldCommunityName = oldCommunityName;
	}

	public String getNewCommunityName() {
		return newCommunityName;
	}

	public void setNewCommunityName(String newCommunityName) {
		this.newCommunityName = newCommunityName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchYourText() {
		return searchYourText;
	}

	public void setSearchYourText(String searchYourText) {
		this.searchYourText = searchYourText;
	}

	public String[] getSelectedInterests() {
		return selectedInterests;
	}

	public void setSelectedInterests(String[] selectedInterests) {
		this.selectedInterests = selectedInterests;
	}

	public String[] getSelectedCommunities() {
		return selectedCommunities;
	}

	public void setSelectedCommunities(String[] selectedCommunities) {
		this.selectedCommunities = selectedCommunities;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

}
