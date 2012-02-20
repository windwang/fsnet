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
		session.removeAttribute("idGroup");
		List<SocialEntity> allMembers = null;
		List<SocialGroup> allGroups = null;
		SocialGroup parentGroup = null;
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		if (form != null) {

			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String name = (String) dynaForm.get("name");
			dynaForm.set("name", "");
			String description = (String) dynaForm.get("description");
			dynaForm.set("description", "");
			String parent = (String) dynaForm.get("parentId");
			dynaForm.set("parentId", "");
			String owner = (String) dynaForm.get("socialEntityId");
			dynaForm.set("socialEntityId", "");
			String[] membersAccepted = (String[]) dynaForm
					.get("memberListRight");
			String[] rigthsAccepted = (String[]) dynaForm.get("rigthListRight");

			SocialEntity masterGroup = socialEntityFacade
					.getSocialEntity(Integer.valueOf(owner));
			if (!parent.equals(""))
				parentGroup = socialGroupFacade.getSocialGroup(Integer
						.parseInt(parent));
			List<SocialElement> socialElements = createSocialElement(em,
					membersAccepted, masterGroup, parentGroup);
			try {
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
			} catch (RollbackException e) {
				ActionErrors errors = new ActionErrors();
				errors.add("name", new ActionMessage("groups.name.exists"));
				saveErrors(request, errors);
				return mapping.findForward("errors");
			}
			em.close();
			dynaForm.set("name", "");
			dynaForm.set("description", "");
			dynaForm.set("socialEntityId", "");
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
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"groups.success.on.create"));

		return mapping.findForward("success");
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
		// TODO Auto-generated method stub

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		Integer socialGroupId = Integer.parseInt(dynaForm.getString("id"));
		String name = (String) dynaForm.get("name");
		dynaForm.set("name", "");
		String description = (String) dynaForm.get("description");
		dynaForm.set("description", "");
		String owner = (String) dynaForm.get("socialEntityId");
		dynaForm.set("socialEntityId", "");

		String[] membersAccepted = (String[]) dynaForm.get("memberListRight");
		String[] membersRefused = (String[]) dynaForm.get("memberListLeft");
		String[] rigthsAccepted = (String[]) dynaForm.get("rigthListRight");

		SocialGroup socialGroup = socialGroupFacade
				.getSocialGroup(socialGroupId);

		SocialEntity masterGroup = socialEntityFacade.getSocialEntity(Integer
				.valueOf(owner));
		SocialEntity oldMasterGroup = socialGroup.getMasterGroup();

		List<SocialElement> acceptedSocialEntity = createObjectSocialEntity(em,
				membersAccepted);

		List<SocialElement> refusedSocialEntity = createObjectSocialEntity(em,
				membersRefused);

		try {

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

			em.getTransaction().begin();
			em.merge(socialGroup);
			em.getTransaction().commit();

			request.getSession(true).removeAttribute("idGroup");
			if (!oldMasterGroup.equals(masterGroup)) {
				request.getSession().setAttribute("isMasterGroup", false);
				return mapping.findForward("toHome");
			}

		} catch (RollbackException e) {
			ActionErrors errors = new ActionErrors();
			errors.add("name", new ActionMessage("groups.name.exists"));
			saveErrors(request, errors);
			return mapping.findForward("errors");
		}
		em.close();
		dynaForm.set("name", "");
		dynaForm.set("description", "");
		dynaForm.set("nameParent", "");
		dynaForm.set("socialEntityId", "");

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"groups.success.on.modify"));

		return mapping.findForward("success");

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
		// TODO Auto-generated method stub
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

		String GroupSelected = request.getParameter("groupSelected");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		em.getTransaction().begin();
		int socialGroupId = Integer.parseInt(GroupSelected);
		socialGroupFacade.switchState(socialGroupId);
		em.getTransaction().commit();
		em.close();

		return mapping.findForward("success");
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
		HttpSession session = request.getSession(true);
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;

		List<SocialEntity> allMembers = null;
		List<SocialEntity> refusedMembers = null;

		List<SocialEntity> acceptedMembers = null;
		Set<Right> acceptedRigths = null;
		Set<Right> refusedRigths = new HashSet<Right>();

		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		String id = request.getParameter("idGroup");

		if (id == null)
			id = (String) session.getAttribute("idGroup");
		else
			session.setAttribute("idGroup", id);

		try {
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
			dynaForm.set("name", group.getName());
			dynaForm.set("description", group.getDescription());
			if (parentGroup != null)
				request.setAttribute("nameParent", parentGroup.getName());
			request.setAttribute("masterGroup", masterGroup);

			request.setAttribute("acceptedMembers", acceptedMembers);
			request.setAttribute("allMembers",
					getSimpleMember(em, socialGroupFacade, group));

			request.setAttribute("refusedMembers", refusedMembers);

			request.setAttribute("refusedRigths", refusedRigths);
			request.setAttribute("acceptedRigths", acceptedRigths);

			em.close();

			return mapping.findForward("success");

		} catch (Exception e) {
			return mapping.findForward("toHome");
		}

	}

	/**
	 * @param groupsAccepted
	 * @return
	 */
	private Set<Right> getAcceptedRigth(String[] groupsAccepted) {
		Set<Right> rights = new HashSet<Right>();

		for (String string : groupsAccepted)
			rights.add(Right.valueOf(string));

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

		for (String members : membersAccepted)
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(members)));

		if (!socialElements.contains(masterGroup))
			socialElements.add(masterGroup);
		if (parentGroup != null)
			socialElements.remove(parentGroup);
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

		for (String member : members)
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(member)));
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
			if (se.getGroup() == null)
				resulEntities.add(se);
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
			return mapping.findForward("success");
		} catch (NullPointerException e) {
			return mapping.findForward("toHome");
		}

	}

	/**
	 * @param em
	 * @param sgf
	 * @param socialGroup
	 * @return
	 */
	private List<SocialEntity> getSimpleMember(EntityManager em,
			SocialGroupFacade sgf, SocialGroup socialGroup) {
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
		EntityManager em = PersistenceProvider.createEntityManager();
		String idGroup = request.getParameter("idGroup");
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialGroup socialGroup;
		if (idGroup == null || idGroup.isEmpty())
			socialGroup = null;
		else {
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

		return mapping.findForward("success");
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
				Right.MODIFY_PICTURE))
			return new ActionRedirect(mapping.findForward("unauthorized"));

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
					return mapping.findForward("success");
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
		return mapping.findForward("success");
	}
}
