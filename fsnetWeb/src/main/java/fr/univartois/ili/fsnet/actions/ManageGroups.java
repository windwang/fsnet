package fr.univartois.ili.fsnet.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import fr.univartois.ili.fsnet.actions.utils.ImageManager;
import fr.univartois.ili.fsnet.actions.utils.PictureType;
import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * @author FSNet
 * 
 */
public class ManageGroups extends MappingDispatchAction implements CrudAction {

	private static final String GROUP_NAME_FORM_FIELD_NAME = "name";
	private static final String GROUP_DESCRIPTION_FORM_FIELD_NAME = "description";
	private static final String GROUP_SOCIAL_ENTITY_ID_FORM_FIELD_NAME = "socialEntityId";
	private static final String GROUP_PARENT_ID_FORM_FIELD_NAME = "parentId";
	private static final String GROUP_MEMBER_LIST_FORM_FIELD_NAME = "memberListRight";
	private static final String GROUP_RIGHT_LIST_FORM_FIELD_NAME = "rigthListRight";
	private static final String GROUP_ID_GROUP_ATTRIBUTE_NAME = "idGroup";
	private static final String GROUP_ATTRIBUTE_COLOR = "color";

	private static final String SUCCES_ACTION_NAME = "success";
	private static final String FAILED_ACTION_NAME = "failed";
	private static final String TO_HOME_ACTION_NAME = "toHome";

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
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		session.removeAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME);
		List<SocialEntity> allMembers = null;
		List<SocialGroup> allGroups = null;
		SocialGroup parentGroup = null;
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		if (form != null) {

			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String name = (String) dynaForm.get(GROUP_NAME_FORM_FIELD_NAME);
			dynaForm.set(GROUP_NAME_FORM_FIELD_NAME, "");
			String description = (String) dynaForm
					.get(GROUP_DESCRIPTION_FORM_FIELD_NAME);
			dynaForm.set(GROUP_DESCRIPTION_FORM_FIELD_NAME, "");
			String parent = (String) dynaForm
					.get(GROUP_PARENT_ID_FORM_FIELD_NAME);
			dynaForm.set(GROUP_PARENT_ID_FORM_FIELD_NAME, "");
			String owner = (String) dynaForm
					.get(GROUP_SOCIAL_ENTITY_ID_FORM_FIELD_NAME);
			dynaForm.set(GROUP_SOCIAL_ENTITY_ID_FORM_FIELD_NAME, "");
			String[] membersAccepted = (String[]) dynaForm
					.get(GROUP_MEMBER_LIST_FORM_FIELD_NAME);
			String[] rigthsAccepted = (String[]) dynaForm
					.get(GROUP_RIGHT_LIST_FORM_FIELD_NAME);

			try {
				SocialEntity masterGroup = socialEntityFacade
						.getSocialEntity(Integer.valueOf(owner));
				if (!parent.equals("")) {
					parentGroup = socialGroupFacade.getSocialGroup(Integer
							.parseInt(parent));
				}
				List<SocialElement> socialElements = createSocialElement(em,
						membersAccepted, masterGroup, parentGroup);

				SocialGroup socialGroup = socialGroupFacade.createSocialGroup(
						masterGroup, name, description, socialElements);

				socialGroup.addRights(getAcceptedRigth(rigthsAccepted));

				em.getTransaction().begin();
				em.persist(socialGroup);
				if (parentGroup != null) {
					parentGroup.addSocialElement(socialGroup);
					em.merge(parentGroup);
				}
				em.getTransaction().commit();

				dynaForm.set(GROUP_NAME_FORM_FIELD_NAME, "");
				dynaForm.set(GROUP_DESCRIPTION_FORM_FIELD_NAME, "");
				dynaForm.set(GROUP_SOCIAL_ENTITY_ID_FORM_FIELD_NAME, "");
			} catch (RollbackException e) {
				ActionErrors errors = new ActionErrors();
				errors.add(GROUP_NAME_FORM_FIELD_NAME, new ActionMessage(
						"groups.name.exists"));
				saveErrors(request, errors);
				return mapping.findForward(FAILED_ACTION_NAME);
			} catch (NumberFormatException e) {
				return mapping.findForward(FAILED_ACTION_NAME);
			} finally {
				em.close();
			}
		} else {
			allMembers = getRefusedSocialMember(socialGroupFacade,
					socialEntityFacade.getAllSocialEntity(), null);
			request.setAttribute("allMembers", allMembers);
			request.setAttribute("refusedMembers", allMembers);
			request.setAttribute("refusedRigths", Right.values());
			allGroups = socialGroupFacade.getAllChildGroups(UserUtils
					.getHisGroup(request));
			request.setAttribute("allGroups", allGroups);
		}

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
				request.getLocale(), "groups.success.on.create"));

		return mapping.findForward(SUCCES_ACTION_NAME);
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
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity user = UserUtils.getAuthenticatedUser(request,
				em);
		
		
		try {
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			Integer socialGroupId = Integer.parseInt(dynaForm.getString("id"));
			String name = (String) dynaForm.get(GROUP_NAME_FORM_FIELD_NAME);
			dynaForm.set(GROUP_NAME_FORM_FIELD_NAME, "");
			String description = (String) dynaForm.get(GROUP_DESCRIPTION_FORM_FIELD_NAME);
			dynaForm.set(GROUP_DESCRIPTION_FORM_FIELD_NAME, "");
			String owner = (String) dynaForm.get(GROUP_SOCIAL_ENTITY_ID_FORM_FIELD_NAME);
			dynaForm.set(GROUP_SOCIAL_ENTITY_ID_FORM_FIELD_NAME, "");
			String color = (String) dynaForm.get(GROUP_ATTRIBUTE_COLOR);
			dynaForm.set(GROUP_ATTRIBUTE_COLOR, "");
			
			String[] membersAccepted = (String[]) dynaForm.get(GROUP_MEMBER_LIST_FORM_FIELD_NAME);
			String[] membersRefused = (String[]) dynaForm.get("memberListLeft");
			String[] rigthsAccepted = (String[]) dynaForm.get(GROUP_RIGHT_LIST_FORM_FIELD_NAME);

			SocialGroup socialGroup = socialGroupFacade.getSocialGroup(socialGroupId);

			SocialEntity masterGroup = socialEntityFacade.getSocialEntity(Integer.valueOf(owner));
			SocialEntity oldMasterGroup = socialGroup.getMasterGroup();

			List<SocialElement> acceptedSocialEntity = createObjectSocialEntity(em, membersAccepted);

			List<SocialElement> refusedSocialEntity = createObjectSocialEntity(em, membersRefused);

			SocialGroup oldSocialGroup22 = masterGroup.getGroup();

			if (oldSocialGroup22 != null) {
				em.getTransaction().begin();
				oldSocialGroup22.removeSocialElement(masterGroup);
				em.merge(oldSocialGroup22);
				em.getTransaction().commit();
			}

			socialGroup.setName(name);
			socialGroup.setDescription(description);
			socialGroup.setMasterGroup(masterGroup);
			socialGroup.setRights(getAcceptedRigth(rigthsAccepted));
			socialGroup.removeAllSocialElements(refusedSocialEntity);
			socialGroup.addAllSocialElements(acceptedSocialEntity);
			socialGroup.addSocialElement(masterGroup);
			socialGroup.setColor(color);
			
			em.getTransaction().begin();
			em.merge(socialGroup);
			em.getTransaction().commit();

			request.getSession(true).removeAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME);
			if (!oldMasterGroup.equals(masterGroup)) {
				request.getSession().setAttribute("isMasterGroup", false);
				return mapping.findForward(TO_HOME_ACTION_NAME);
			}

			dynaForm.set(GROUP_NAME_FORM_FIELD_NAME, "");
			dynaForm.set(GROUP_DESCRIPTION_FORM_FIELD_NAME, "");
			dynaForm.set(GROUP_SOCIAL_ENTITY_ID_FORM_FIELD_NAME, "");

			MessageResources bundle = MessageResources
					.getMessageResources("FSneti18n");
			request.setAttribute(SUCCES_ACTION_NAME, bundle.getMessage(
					request.getLocale(), "groups.success.on.modify"));
			
			if(user.getGroup().getId() == socialGroupId){
				request.getSession().setAttribute("color", color);
			}
			
			return mapping.findForward(SUCCES_ACTION_NAME);
		} catch (RollbackException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(GROUP_NAME_FORM_FIELD_NAME, new ActionMessage(
					"groups.name.exists"));
			saveErrors(request, errors);
			return mapping.findForward(FAILED_ACTION_NAME);
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}
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
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		return null;
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
	public ActionForward switchState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			String groupSelected = request.getParameter("groupSelected");
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			em.getTransaction().begin();
			int socialGroupId = Integer.parseInt(groupSelected);
			socialGroupFacade.switchState(socialGroupId);
			em.getTransaction().commit();
		} catch (NumberFormatException e) {
			return mapping.findForward(FAILED_ACTION_NAME);
		} finally {
			em.close();
		}

		return mapping.findForward(SUCCES_ACTION_NAME);
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
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		try {
			HttpSession session = request.getSession(true);
			DynaActionForm dynaForm = (DynaActionForm) form;

			List<SocialEntity> allMembers = null;
			List<SocialEntity> refusedMembers = null;

			List<SocialEntity> acceptedMembers = null;
			Set<Right> acceptedRigths = null;
			Set<Right> refusedRigths = new HashSet<Right>();

			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

			String id = request.getParameter(GROUP_ID_GROUP_ATTRIBUTE_NAME);

			if (id == null) {
				id = (String) session
						.getAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME);
			} else {
				session.setAttribute(GROUP_ID_GROUP_ATTRIBUTE_NAME, id);
			}

			Integer idGroup;
			idGroup = Integer.valueOf(id);
			SocialGroup group = socialGroupFacade.getSocialGroup(idGroup);
			SocialEntity masterGroup = group.getMasterGroup();
			SocialGroup parentGroup = group.getGroup();

			allMembers = socialEntityFacade.getAllSocialEntity();

			acceptedMembers = socialGroupFacade
					.getAcceptedSocialElementsByFilter(group,
							SocialEntity.class);
			refusedMembers = getRefusedSocialMember(socialGroupFacade,
					allMembers, group);
			acceptedRigths = group.getRights();
			for (Right right : Right.values()) {
				refusedRigths.add(right);
			}

			refusedRigths.removeAll(acceptedRigths);

			dynaForm.set("id", id);
			dynaForm.set(GROUP_NAME_FORM_FIELD_NAME, group.getName());
			dynaForm.set(GROUP_DESCRIPTION_FORM_FIELD_NAME,
					group.getDescription());
			if (parentGroup != null) {
				request.setAttribute("nameParent", parentGroup.getName());
			}
			request.setAttribute("masterGroup", masterGroup);

			request.setAttribute("acceptedMembers", acceptedMembers);
			request.setAttribute("allMembers", getSimpleMember(em, group));

			request.setAttribute("refusedMembers", refusedMembers);

			request.setAttribute("refusedRigths", refusedRigths);
			request.setAttribute("acceptedRigths", acceptedRigths);

			return mapping.findForward(SUCCES_ACTION_NAME);
		} catch (NumberFormatException e) {
			return mapping.findForward(TO_HOME_ACTION_NAME);
		} finally {
			em.close();
		}

	}

	/**
	 * @param groupsAccepted
	 * @return
	 */
	private Set<Right> getAcceptedRigth(String[] groupsAccepted) {
		Set<Right> rights = new HashSet<Right>();

		for (String string : groupsAccepted){
			rights.add(Right.valueOf(string));
		}

		return rights;
	}

	/**
	 * @param em
	 * @param membersAccepted
	 * @param masterGroup
	 * @param parentGroup
	 * @return
	 */
	private List<SocialElement> createSocialElement(EntityManager em,
			String[] membersAccepted, SocialEntity masterGroup,
			SocialGroup parentGroup) {

		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		for (String members : membersAccepted) {
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(members)));
		}

		if (!socialElements.contains(masterGroup)) {
			socialElements.add(masterGroup);
		}
		if (parentGroup != null) {
			socialElements.remove(parentGroup);
		}
		return socialElements;
	}

	/**
	 * @param em
	 * @param members
	 * @return
	 */
	private List<SocialElement> createObjectSocialEntity(EntityManager em,
			String[] members) {

		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		for (String member : members) {
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(member)));
		}

		return socialElements;
	}

	/**
	 * @param sgf
	 * @param allMembers
	 * @param socialGroup
	 * @return
	 */
	private List<SocialEntity> getRefusedSocialMember(SocialGroupFacade sgf,
			List<SocialEntity> allMembers, SocialGroup socialGroup) {
		if (sgf == null) {
			throw new IllegalArgumentException();
		}
		List<SocialEntity> resulEntities = new ArrayList<SocialEntity>();
		List<SocialEntity> members = allMembers;
		if (socialGroup != null) {
			members.removeAll(sgf.getAcceptedSocialElementsByFilter(
					socialGroup, SocialEntity.class));
		}
		for (SocialEntity se : allMembers) {
			if (se.getGroup() == null) {
				resulEntities.add(se);
			}
		}
		return resulEntities;

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
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialGroup socialGroup;
		try {
			socialGroup = UserUtils.getHisGroup(request);
			List<SocialGroup> resultOthersList = socialGroupFacade
					.getAllChildGroups(socialGroup);

			request.setAttribute("groupsList", resultOthersList);
			return mapping.findForward(SUCCES_ACTION_NAME);
		} catch (NullPointerException e) {
			return mapping.findForward(TO_HOME_ACTION_NAME);
		}
	}

	/**
	 * @param em
	 * @param socialGroup
	 * @return
	 */
	private List<SocialEntity> getSimpleMember(EntityManager em,
			SocialGroup socialGroup) {
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		List<SocialEntity> allMastersGroup = socialGroupFacade
				.getAllMasterGroupes();

		List<SocialEntity> allOrphanMembers = socialEntityFacade
				.getAllOrphanMembers();

		List<SocialEntity> allSocialchildEntity = socialGroupFacade
				.getAllChildMembers(socialGroup);

		allSocialchildEntity.removeAll(allMastersGroup);
		allSocialchildEntity.add(socialGroup.getMasterGroup());
		allSocialchildEntity.addAll(allOrphanMembers);

		return allSocialchildEntity;
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
	public ActionForward displayInformationGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			EntityManager em = PersistenceProvider.createEntityManager();

			String idGroup = request
					.getParameter(GROUP_ID_GROUP_ATTRIBUTE_NAME);
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			SocialGroup socialGroup;
			
			if (idGroup == null || idGroup.isEmpty()) {
				socialGroup = null;
			} else {
				int id = Integer.valueOf(idGroup);
				socialGroup = socialGroupFacade.getSocialGroup(id);
			}
			
			request.setAttribute("socialGroup", socialGroup);

			List<SocialEntity> allMembers = socialGroupFacade
					.getMembersFromGroup(socialGroup);
			request.setAttribute("allMembers", allMembers);

			List<SocialGroup> listOfAntecedantGroup = socialGroupFacade
					.getAllAntecedentSocialGroups(socialGroup);
			java.util.Collections.reverse(listOfAntecedantGroup);
			request.setAttribute("antecedantsOfGroup", listOfAntecedantGroup);

			List<SocialGroup> listOfChildGroup = socialGroupFacade
					.getAllChildGroups(socialGroup);
			listOfChildGroup.remove(socialGroup);
			request.setAttribute("childsOfGroup", listOfChildGroup);
		} catch (NumberFormatException e) {

		}

		return mapping.findForward(SUCCES_ACTION_NAME);
	}

	/**
	 * @param request
	 * @param key
	 */
	private void sendPictureError(HttpServletRequest request, String key) {
		ActionErrors errors = new ActionErrors();
		errors.add("Logo", new ActionMessage(key));
		saveErrors(request, errors);
	}

	private static final int MAX_PICTURE_SIZE = 500000;

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward changeLogo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade fascade = new SocialGroupFacade(em);
		
		if (!fascade.isAuthorized(UserUtils.getAuthenticatedUser(request, em),
				Right.MODIFY_PICTURE)) {
			return new ActionRedirect(mapping.findForward("unauthorized"));
		}

		int groupId = UserUtils.getHisGroup(request).getId();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		FormFile file = (FormFile) dynaForm.get("Logo");
		
		if (file.getFileData().length != 0) {
			PictureType pictureType = null;
			for (PictureType pt : PictureType.values()) {
				if (pt.getMimeType().equals(file.getContentType())) {
					pictureType = pt;
					break;
				}
			}
			
			if (pictureType != null) {

				if (file.getFileSize() > MAX_PICTURE_SIZE) {
					sendPictureError(request, "groups.logo.maxsize");
					return mapping.findForward(SUCCES_ACTION_NAME);
				}

				try {
					ImageManager.createLogo(groupId, file.getInputStream(),
							pictureType);
				} catch (FileNotFoundException e) {
					sendPictureError(request, "groups.logo.fatal");
				} catch (IOException e) {
					sendPictureError(request, "groups.logo.fatal");
				} catch (IllegalStateException e) {
					sendPictureError(request, "groups.logo.fatal");
				}
			} else {
				sendPictureError(request, "groups.logo.type");
			}
		}
		
		return mapping.findForward(SUCCES_ACTION_NAME);
	}
}
