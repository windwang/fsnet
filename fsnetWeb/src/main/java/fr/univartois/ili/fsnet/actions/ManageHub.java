package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.HubFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;

/**
 * 
 * @author Cerelia Besnainou and Audrey Ruellan
 */
public class ManageHub extends ActionSupport implements CrudAction,
		ServletRequestAware {

	private static final long serialVersionUID = 1L;

	private static final String SUCCES_ACTION_NAME = "success";

	private HttpServletRequest request;

	private static final String COMMUNITY_ID_ATTRIBUTE_NAME = "communityId";
	private static final String HUB_NAME_FORM_FIELD_NAME = "hubName";
	private static final String HUB_NEW_NAME_FORM_FIELD_NAME = "newHubName";

	private String hubName;
	private int communityId;
	private int hubId;
	private String oldHubId;
	private String interestsIds[];
	private String pattern;
	private String newHubName;

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
	public String create() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();
		try {
			Community community = em.find(Community.class, communityId);
			HubFacade hubFacade = new HubFacade(em);
			boolean doesNotExists = false;
			try {
				hubFacade.getHubByName(hubName, community);
			} catch (NoResultException e) {
				doesNotExists = true;
			}

			if (doesNotExists) {
				InterestFacade fac = new InterestFacade(em);
				List<Interest> interests = new ArrayList<Interest>();
				int currentId;
				for (currentId = 0; currentId < interestsIds.length; currentId++) {
					interests.add(fac.getInterest(Integer
							.valueOf(interestsIds[currentId])));
				}

				SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
				em.getTransaction().begin();
				Hub createdHub = hubFacade.createHub(community, user, hubName);

				InteractionFacade ifacade = new InteractionFacade(em);
				ifacade.addInterests(createdHub, interests);
				em.getTransaction().commit();
			} else {
				addFieldError(HUB_NAME_FORM_FIELD_NAME, "hubs.alreadyExists");
			}
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}
		return SUCCESS;
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
	public String modify() throws Exception {
		boolean doesNotExists = false;
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			HubFacade facade = new HubFacade(em);
			CommunityFacade facadeCommunity = new CommunityFacade(em);

			Community community = facadeCommunity.getCommunity(communityId);
			Hub myHub = facade.getHubById(hubId, community);

			if (myHub != null) {
				try {
					facade.getHubByName(hubName, community);
				} catch (NoResultException e) {
					doesNotExists = true;
				}

				if (doesNotExists) {
					try {
						em.getTransaction().begin();
						facade.modifyName(hubName, myHub);
						em.getTransaction().commit();
					} catch (DatabaseException ex) {
						addFieldError(HUB_NEW_NAME_FORM_FIELD_NAME,
								"hubs.alreadyExists");
					}
				} else {
					addFieldError(HUB_NEW_NAME_FORM_FIELD_NAME,
							"hubs.alreadyExists");
				}
			}
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		setOldHubId("");
		setNewHubName("");

		return SUCCES_ACTION_NAME;
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
	public String delete() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
			HubFacade hubFacade = new HubFacade(em);
			CommunityFacade communityFacade = new CommunityFacade(em);
			InteractionFacade interactionFacade = new InteractionFacade(em);
			em.getTransaction().begin();

			Hub hub = hubFacade.getHub(hubId);
			Community community = communityFacade.getCommunity(communityId);
			community.getHubs().remove(hub);
			for (SocialEntity se : hub.getFollowingEntitys()) {
				se.getFavoriteInteractions().remove(hub);
			}
			interactionFacade.deleteInteraction(user, hub);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

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
	public String search() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			if (hubName == null) {
				hubName = "";
			}

			Community community = em.find(Community.class, communityId);
			HubFacade hubFacade = new HubFacade(em);
			em.getTransaction().begin();
			List<Hub> result = hubFacade.searchHub(hubName, community);
			em.getTransaction().commit();
			Paginator<Hub> paginator = new Paginator<Hub>(result, request,
					"hubList", COMMUNITY_ID_ATTRIBUTE_NAME);

			request.setAttribute("listHubPaginator", paginator);
			request.setAttribute("Community", community);
			request.setAttribute("hubResults", result);
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

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
	public String display() throws Exception {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			Map<Topic, Message> topicsLastMessage = new HashMap<Topic, Message>();
			em.getTransaction().begin();
			HubFacade hubFacade = new HubFacade(em);
			Hub result = hubFacade.getHub(hubId);

			for (Topic t : result.getTopics()) {
				List<TopicMessage> messages = t.getMessages();
				Message lastMessage = null;
				if (messages.size() > 0) {
					lastMessage = messages.get(messages.size() - 1);
				}
				topicsLastMessage.put(t, lastMessage);
			}

			em.getTransaction().commit();

			request.setAttribute("hubResult", result);
			request.setAttribute("topicsLastMessage", topicsLastMessage);
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		// TODO modify paginator for accepting HasMap
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
	public String getAllInterest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		InterestFacade fac = new InterestFacade(em);
		List<Interest> listInterests = fac.getInterests();
		request.setAttribute("Interests", listInterests);

		em.close();

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
	public String searchYourHubs(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// TODO use facade
		EntityManager em = PersistenceProvider.createEntityManager();

		try {

			if (pattern == null) {
				pattern = "";
			}

			Community community = em.find(Community.class, communityId);
			SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);

			em.getTransaction().begin();
			List<Hub> hubs = em
					.createQuery(
							"SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName AND hub.community = :com AND hub.creator = :creator",
							Hub.class)
					.setParameter("hubName", "%" + pattern + "%")
					.setParameter("com", community)
					.setParameter("creator", creator).getResultList();

			em.getTransaction().commit();

			request.setAttribute("hubResults", hubs);
		} catch (NumberFormatException e) {

		} finally {
			em.close();
		}

		return SUCCES_ACTION_NAME;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public String getOldHubId() {
		return oldHubId;
	}

	public void setOldHubId(String oldHubId) {
		this.oldHubId = oldHubId;
	}

	public String getNewHubName() {
		return newHubName;
	}

	public void setNewHubName(String newHubName) {
		this.newHubName = newHubName;
	}

}
