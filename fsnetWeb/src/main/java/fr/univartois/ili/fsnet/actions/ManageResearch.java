package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.AnnouncementFacade;
import fr.univartois.ili.fsnet.facade.CommunityFacade;
import fr.univartois.ili.fsnet.facade.ConsultationFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade.SearchResult;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

public class ManageResearch extends ActionSupport implements
		ServletRequestAware {

	private HttpServletRequest request;
	private String searchText;
	private String[] selectedResearch;

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String search() throws Exception {

		request.setAttribute("searchTous", false);
		request.setAttribute("searchMembers", false);
		request.setAttribute("searchAnnonce", false);
		request.setAttribute("searchEvents", false);
		request.setAttribute("searchConsultations", false);
		request.setAttribute("searchCommunauties", false);

//		String searchText = (String) dynaForm.get("searchText");
//		String[] selectedModes = (String[]) dynaForm.get("selectedResearch");

		Set<String> modes = new HashSet<String>();
		for (int i = 0; i < selectedResearch.length; i++) {
			modes.add(selectedResearch[i]);
		}
		if (modes.contains("tous") || modes.size() == 0) {
			request.setAttribute("searchTous", true);
			modes.add("members");
			modes.add("consultations");
			modes.add("annonces");
			modes.add("evenements");
			modes.add("communaute");
		}

		searchMembers(request, searchText, modes);
		searchConsultation(request, searchText, modes);
		searchAnnounce(request, searchText, modes);
		searchEvents(request, searchText, modes);
		searchCommunities(request, searchText, modes);

		return SUCCESS;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String[] getSelectedResearch() {
		return selectedResearch;
	}

	public void setSelectedResearch(String[] selectedResearch) {
		this.selectedResearch = selectedResearch;
	}

	private void searchCommunities(HttpServletRequest request,
			String searchText, Set<String> checkboxModes) {
		Boolean recherche = checkboxModes.contains("communaute");
		request.setAttribute("searchCommunauties", recherche);

		EntityManager em = PersistenceProvider.createEntityManager();
		List<Community> result = null;
		CommunityFacade communityFacade = new CommunityFacade(em);
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightCreateCommunity = Right.CREATE_COMMUNITY;
		request.setAttribute("rightCreateCommunity", rightCreateCommunity);
		request.setAttribute("socialEntity", socialEntity);

		em.getTransaction().begin();
		if (recherche) {
			result = communityFacade.searchCommunity(searchText);
			if (result != null && !result.isEmpty()) {
				FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
						em);
				SocialEntity se = UserUtils.getAuthenticatedUser(request);
				result = filter.filterInteraction(se, result);
			}
		}
		em.getTransaction().commit();
		em.close();

		request.setAttribute("communitiesResult", result);
	}

	/**
	 * @param request
	 * @param em
	 * @param member
	 * @param searchText
	 * @param checkboxModes
	 */
	private void searchEvents(HttpServletRequest request, String searchText,
			Set<String> checkboxModes) {
		Boolean recherche = checkboxModes.contains("evenements");
		request.setAttribute("searchEvents", recherche);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		MeetingFacade meetingFacade = new MeetingFacade(em);
		em.getTransaction().begin();
		List<Meeting> results = null;
		if (recherche) {
			results = meetingFacade.searchMeeting(searchText);

			if (results != null && !results.isEmpty()) {
				FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
						em);
				results = filter.filterInteraction(member, results);
			}
		}
		InteractionFacade interactionFacade = new InteractionFacade(em);
		List<Integer> unreadInteractionsId = interactionFacade
				.getUnreadInteractionsIdForSocialEntity(member);
		em.getTransaction().commit();
		em.close();

		request.setAttribute("eventsResult", results);
		request.setAttribute("unreadInteractionsId", unreadInteractionsId);
	}

	/**
	 * @param request
	 * @param em
	 * @param searchText
	 * @param checkboxModes
	 */
	private void searchAnnounce(HttpServletRequest request, String searchText,
			Set<String> checkboxModes) {
		Boolean recherche = checkboxModes.contains("annonces");
		request.setAttribute("searchAnnonce", recherche);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		em.getTransaction().begin();
		AnnouncementFacade announcementFacade = new AnnouncementFacade(em);
		List<Announcement> listAnnounces = null;
		if (recherche) {
			listAnnounces = announcementFacade.searchAnnouncement(searchText);
			/* filter list announce */
			if (listAnnounces != null && !listAnnounces.isEmpty()) {
				FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
						em);
				listAnnounces = filter.filterInteraction(member, listAnnounces);
			}
		}
		em.close();
		request.setAttribute("annoncesResult", listAnnounces);
	}

	/**
	 * @param request
	 * @param em
	 * @param member
	 * @param searchText
	 * @param checkboxModes
	 */
	private void searchConsultation(HttpServletRequest request,
			String searchText, Set<String> checkboxModes) {
		List<Consultation> searchConsultations = null;
		EntityManager em = PersistenceProvider.createEntityManager();

		Boolean recherche = checkboxModes.contains("consultations");
		request.setAttribute("searchConsultations", recherche);
		if (recherche) {
			ConsultationFacade consultationFacade = new ConsultationFacade(em);
			searchConsultations = consultationFacade
					.getConsultationsContaining(searchText);
			if (searchConsultations != null && !searchConsultations.isEmpty()) {
				FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(
						em);
				SocialEntity se = UserUtils.getAuthenticatedUser(request);
				searchConsultations = filter.filterInteraction(se,
						searchConsultations);
			}
		}

		request.setAttribute("consultationsResult", searchConsultations);
	}

	/**
	 * @param request
	 * @param em
	 * @param member
	 * @param searchText
	 * @param checkboxModes
	 */
	private void searchMembers(HttpServletRequest request, String searchText,
			Set<String> checkboxModes) {
		Set<SocialEntity> resultOthers = null;
		Set<SocialEntity> resultContacts = null;
		Set<SocialEntity> resultRequested = null;
		Set<SocialEntity> resultAsked = null;
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);

		Boolean recherche = checkboxModes.contains("members");
		request.setAttribute("searchMembers", recherche);
		em.getTransaction().begin();
		if (recherche) {
			SocialEntityFacade sef = new SocialEntityFacade(em);
			SocialGroupFacade sgf = new SocialGroupFacade(em);
			List<SocialEntity> membersVisibleByCurrentMember = sgf
					.getPersonsWithWhoMemberCanInteract(member);
			Map<SearchResult, Set<SocialEntity>> results = sef
					.searchSocialEntity(searchText, member);
			resultContacts = results.get(SearchResult.Contacts);
			resultContacts.retainAll(membersVisibleByCurrentMember);
			resultRequested = results.get(SearchResult.Requested);
			resultRequested.retainAll(membersVisibleByCurrentMember);
			resultAsked = results.get(SearchResult.Asked);
			resultAsked.retainAll(membersVisibleByCurrentMember);
			resultOthers = results.get(SearchResult.Others);
			resultOthers.retainAll(membersVisibleByCurrentMember);
			em.getTransaction().commit();
			if (sgf.isMasterGroup(member)) {
				request.getSession(true).setAttribute("isMasterGroup", true);
			} else {
				request.getSession(true).setAttribute("isMasterGroup", false);
			}
			if (sgf.isGroupResponsible(member)) {
				request.getSession(true).setAttribute("isGroupResponsible",
						true);
			} else {
				request.getSession(true).setAttribute("isGroupResponsible",
						false);
			}
			em.close();
		}

		request.setAttribute("membersResult", resultOthers);
		request.setAttribute("membersContactsResult", resultContacts);
		request.setAttribute("membersRequestedResult", resultRequested);
		request.setAttribute("membersAskedResult", resultAsked);
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
