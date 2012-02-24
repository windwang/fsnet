package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.AnnouncementFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 * 
 * @author Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
 */
public class ManageAnnounces extends MappingDispatchAction implements
		CrudAction {

	private static final String SUCCES_ATTRIBUTE_NAME = "success";
	private static final String FAILER_ACTION_NAME = "failer";
	private static final String ID_ANNOUNCE_FORM_FIELD_NAME = "idAnnounce";
	
	/**
	 * @return to announces view after persisting new announce
	 */
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				entityManager);
		SocialGroupFacade fascade = new SocialGroupFacade(entityManager);
		if(!fascade.isAuthorized(user, Right.ADD_ANNOUNCE))
		{
			entityManager.close();
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}
		
		DynaActionForm formAnnounce = (DynaActionForm) form; // NOSONAR
		String title = (String) formAnnounce.get("announceTitle");
		String content = (String) formAnnounce.get("announceContent");
		String stringExpiryDate = (String) formAnnounce
				.get("announceExpiryDate");
		String InterestsIds[] = (String[]) formAnnounce
				.get("selectedInterests");

		Announcement createdAnnounce;
		addRightToRequest(request);

		try {
			Date expiryDate = DateUtils.format(stringExpiryDate);
			if (0 > DateUtils.compareToToday(expiryDate)) {
				AnnouncementFacade announcementFacade = new AnnouncementFacade(
						entityManager);
				entityManager.getTransaction().begin();
				createdAnnounce = announcementFacade.createAnnouncement(user,
						title, content, expiryDate, false);
				InterestFacade fac = new InterestFacade(entityManager);
				List<Interest> interests = new ArrayList<Interest>();
				int currentId;
				for (currentId = 0; currentId < InterestsIds.length; currentId++) {
					interests.add(fac.getInterest(Integer
							.valueOf(InterestsIds[currentId])));
				}
				InteractionFacade ifacade = new InteractionFacade(entityManager);
				ifacade.addInterests(createdAnnounce, interests);
				entityManager.getTransaction().commit();
			} else {
				ActionMessages errors = new ActionErrors();
				errors.add("message", new ActionMessage(
						"date.error.dateBelowDateToday"));
				saveErrors(request, errors);
				entityManager.close();
				return mapping.findForward(FAILER_ACTION_NAME);
			}
		} catch (ParseException e) {
			servlet.log(getClass().getName()
					+ " methode:create exception when formating date ");
			return mapping.findForward(FAILER_ACTION_NAME);
		}
		entityManager.close();
		return new ActionRedirect(mapping.findForward(SUCCES_ATTRIBUTE_NAME));
	}

	/**
	 * @return to views announce after updating it
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException,
			UnauthorizedOperationException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				entityManager);
		DynaActionForm formAnnounce = (DynaActionForm) form;// NOSONAR
		String title = (String) formAnnounce.get("announceTitle");
		String content = (String) formAnnounce.get("announceContent");
		String stringExpiryDate = (String) formAnnounce
				.get("announceExpiryDate");
		Integer idAnnounce = (Integer) formAnnounce.get(ID_ANNOUNCE_FORM_FIELD_NAME);
		AnnouncementFacade announcementFacade = new AnnouncementFacade(
				entityManager);
		Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
		addRightToRequest(request);
		try {

			if (!announce.getCreator().equals(user)) {
				throw new UnauthorizedOperationException("exception.message");
			}

			Date expiryDate = DateUtils.format(stringExpiryDate);
			if (0 > DateUtils.compareToToday(expiryDate)) {
				entityManager.getTransaction().begin();
				announcementFacade.modifyAnnouncement(idAnnounce, title,
						content, expiryDate);
				entityManager.getTransaction().commit();

			} else {
				ActionMessages errors = new ActionMessages();
				errors.add("message", new ActionMessage(
						"date.error.dateBelowDateToday"));
				saveErrors(request, errors);
			}
			request.setAttribute("announce", announce);
			request.setAttribute("owner", true);
		} catch (ParseException e) {
			servlet.log("class:ManageAnnounces methode:create exception whene formatying date ");
			return mapping.findForward(FAILER_ACTION_NAME);
		} finally {
			entityManager.close();
		}
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
     *
     */
	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		entityManager.getTransaction().begin();
		AnnouncementFacade announcementFacade = new AnnouncementFacade(
				entityManager);
		Integer idAnnounce = Integer
				.valueOf(request.getParameter(ID_ANNOUNCE_FORM_FIELD_NAME));
		Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
		ActionMessages message = new ActionErrors();
		InteractionFacade interactionFacade = new InteractionFacade(
				entityManager);
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				entityManager);
		addRightToRequest(request);
		if (announce != null){
			interactionFacade.deleteInteraction(user, announce);
		}
		entityManager.getTransaction().commit();
		entityManager.close();
		message.add("message", new ActionMessage("announce.message.delete.success"));
		saveMessages(request, message);
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);

	}

	/**
	 * @return list of announce
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm seaarchForm = (DynaActionForm) form;// NOSONAR
		String textSearchAnnounce = (String) seaarchForm
				.get("textSearchAnnounce");
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		entityManager.getTransaction().begin();
		AnnouncementFacade announcementFacade = new AnnouncementFacade(
				entityManager);

		List<Announcement> listAnnounces = announcementFacade
				.searchAnnouncement(textSearchAnnounce);
		/* filter list announce */
		if(listAnnounces !=null && !listAnnounces.isEmpty()) {
			FilterInteractionByUserGroup filter = new FilterInteractionByUserGroup(entityManager);
			SocialEntity se = UserUtils.getAuthenticatedUser(request);
			listAnnounces = filter.filterInteraction(se, listAnnounces);
		}
		
		addRightToRequest(request);
		
		SocialEntity member = UserUtils.getAuthenticatedUser(request, entityManager);
		InteractionFacade interactionFacade = new InteractionFacade(entityManager);
		List<Integer> unreadInteractionsId = interactionFacade.getUnreadInteractionsIdForSocialEntity(member);
		refreshNumNewAnnonces(request, entityManager);
		entityManager.getTransaction().commit();
		entityManager.close();


		request.setAttribute("annoucesList", listAnnounces);
		
		request.setAttribute("unreadInteractionsId", unreadInteractionsId);
		
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @return announce in request
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		entityManager.getTransaction().begin();
		SocialEntity SocialEntity = UserUtils.getAuthenticatedUser(request,
				entityManager);
		try{
		Integer idAnnounce = Integer
				.valueOf(request.getParameter(ID_ANNOUNCE_FORM_FIELD_NAME));
		AnnouncementFacade announcementFacade = new AnnouncementFacade(
				entityManager);
		Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
		SocialEntity SocialEntityOwner = (SocialEntity) entityManager
				.createQuery(
						"SELECT es FROM SocialEntity es,IN(es.interactions) e WHERE e = :announce")
				.setParameter("announce", announce).getSingleResult();
		addRightToRequest(request);
		
		SocialEntity member = UserUtils.getAuthenticatedUser(request, entityManager);
		member.addInteractionRead(announce);
		refreshNumNewAnnonces(request, entityManager);
		entityManager.getTransaction().commit();
		entityManager.close();

		request.setAttribute("announce", announce);
		request.setAttribute("SocialEntity", SocialEntityOwner);
		servlet.log(SocialEntityOwner.toString() + SocialEntityOwner.getName());
		if (SocialEntity.getId() == SocialEntityOwner.getId()) {
			request.setAttribute("owner", true);
		}
		}catch(NumberFormatException e){
		}
		
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}

	/**
	 * @return announce in request
	 */
	public ActionForward displayForModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		Integer idAnnounce = Integer
				.valueOf(request.getParameter(ID_ANNOUNCE_FORM_FIELD_NAME));
		try{
		AnnouncementFacade announcementFacade = new AnnouncementFacade(em);
		Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
		addRightToRequest(request);
		em.getTransaction().commit();
		em.close();
		dynaForm.set(ID_ANNOUNCE_FORM_FIELD_NAME, announce.getId());
		dynaForm.set("announceTitle", announce.getTitle());
		dynaForm.set("announceContent", announce.getContent());
		dynaForm.set("announceExpiryDate",
				DateUtils.renderDateWithHours(announce.getEndDate()));
		}catch(NullPointerException e){
			return mapping.findForward(FAILER_ACTION_NAME);
		}
		return mapping.findForward(SUCCES_ATTRIBUTE_NAME);
	}
	
	private void addRightToRequest(HttpServletRequest request){
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightAddAnnounce = Right.ADD_ANNOUNCE;
		request.setAttribute("rightAddAnnounce", rightAddAnnounce);
		request.setAttribute("socialEntity",socialEntity);
	}
	
	
	/**
	 * Store the number of non reed  announces
	 * 
	 * @param request
	 * @param em
	 */
	public static final void refreshNumNewAnnonces(HttpServletRequest request,
			EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade inf = new InteractionFacade(em);
		List<Interaction> list = inf .getUnreadInteractionsForSocialEntity(user);
		int numNonReedAnnounces =Interaction.filter(list, Announcement.class).size();
		session.setAttribute("numNonReedAnnounces",
				numNonReedAnnounces);
	}

	/**
	 * @author stephane gronowski
	 * Access to the jsp to create an {@link Announcement}.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward displayCreateAnnounce(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager entityManager = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				entityManager);
		entityManager.close();
		SocialGroupFacade fascade = new SocialGroupFacade(entityManager);
		if(!fascade.isAuthorized(user, Right.ADD_ANNOUNCE)){
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}
		
		return new ActionRedirect(mapping.findForward(SUCCES_ATTRIBUTE_NAME));
	}

	
}
