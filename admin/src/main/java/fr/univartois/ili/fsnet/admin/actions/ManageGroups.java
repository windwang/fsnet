package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;
import org.apache.struts.util.MessageResources;

import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * Execute CRUD Actions (and more) for the entity SocialGroup
 * 
 * @author SAID mohamed
 * @author Bouragba mohamed
 */
public class ManageGroups extends MappingDispatchAction implements CrudAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#create(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
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
		List<SocialGroup> refusedGroups = null;
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
			String[] groupsAccepted = (String[]) dynaForm.get("groupListRight");
			String[] rigthsAccepted = (String[]) dynaForm.get("rigthListRight");

			SocialEntity masterGroup = socialEntityFacade
					.getSocialEntity(Integer.valueOf(owner));
			if (!parent.equals("")){
				parentGroup = socialGroupFacade.getSocialGroup(Integer
						.parseInt(parent));
			}
			List<SocialElement> socialElements = createSocialElement(em,
					membersAccepted, groupsAccepted, masterGroup, parentGroup);
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
			allGroups = socialGroupFacade.getAllSocialGroups();
			refusedGroups = getRefusedSocialGroup(socialGroupFacade, null);
			request.setAttribute("refusedGroups", refusedGroups);
			request.setAttribute("allGroups", allGroups);
		}

		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"groups.success.on.create"));

		return mapping.findForward("success");
	}

	/**
	 * @author SAID Mohamed
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#modify(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
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
		SocialGroup newParentGroup = null;
		SocialGroup oldParentGroup = null;
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		Integer socialGroupId = Integer.parseInt(dynaForm.getString("id"));
		String name = (String) dynaForm.get("name");
		dynaForm.set("name", "");
		String description = (String) dynaForm.get("description");
		dynaForm.set("description", "");
		String owner = (String) dynaForm.get("socialEntityId");
		dynaForm.set("socialEntityId", "");
		String parent = (String) dynaForm.get("parentId");
		dynaForm.set("parentId", "");
		if (!parent.equals("")){
			newParentGroup = socialGroupFacade.getSocialGroup(Integer
					.parseInt(parent));
		}
		String[] membersAccepted = (String[]) dynaForm.get("memberListRight");
		String[] groupsAccepted = (String[]) dynaForm.get("groupListRight");
		String[] rigthsAccepted = (String[]) dynaForm.get("rigthListRight");

		SocialEntity masterGroup = socialEntityFacade.getSocialEntity(Integer
				.valueOf(owner));
		MessageResources bundle = MessageResources
				.getMessageResources("FSneti18n");
		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"interest.success.on.create"));
		// SocialGroup oldParentGrouoOfMasterGroup = masterGroup.getGroup();

		List<SocialElement> socialElements = createSocialElement(em,
				membersAccepted, groupsAccepted, masterGroup, newParentGroup);

		try {

			SocialGroup socialGroup = socialGroupFacade
					.getSocialGroup(socialGroupId);

			socialGroup.setName(name);
			socialGroup.setDescription(description);
			socialGroup.setMasterGroup(masterGroup);
			socialGroup.setSocialElements(new ArrayList<SocialElement>());
			socialGroup.setRights(getAcceptedRigth(rigthsAccepted));
			oldParentGroup = socialGroup.getGroup();
			// oldParentGrouoOfMasterGroup.removeSocialElement(masterGroup);
			em.getTransaction().begin();

			// em.merge(socialGroup);
			socialGroup.setSocialElements(socialElements);
			if (oldParentGroup != null) {
				oldParentGroup.removeSocialElement(socialGroup);
				em.merge(oldParentGroup);
			}
			if (newParentGroup != null) {
				newParentGroup.addSocialElement(socialGroup);
				em.merge(newParentGroup);
			}
			// em.merge(oldParentGrouoOfMasterGroup);
			em.merge(socialGroup);
			em.getTransaction().commit();
			request.getSession(true).removeAttribute("idGroup");
		} catch (RollbackException e) {
			ActionErrors errors = new ActionErrors();
			errors.add("name", new ActionMessage("groups.name.exists"));
			saveErrors(request, errors);
			return mapping.findForward("errors");
		}
		em.close();
		dynaForm.set("name", "");
		dynaForm.set("description", "");
		dynaForm.set("parentId", "");
		dynaForm.set("socialEntityId", "");

		request.setAttribute("success", bundle.getMessage(request.getLocale(),
				"groups.success.on.modify"));

		return mapping.findForward("success");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#delete(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
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
	 * @author SAID Mohamed
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#search(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		em.getTransaction().begin();
		TypedQuery<SocialGroup> query = null;
		Set<SocialGroup> resultOthers = null;

		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String searchText = (String) dynaForm.get("searchText");
			SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
			resultOthers = socialGroupFacade.searchGroup(searchText);
			em.getTransaction().commit();
			em.close();
			if (resultOthers != null) {

				List<SocialGroup> resultOthersList = new ArrayList<SocialGroup>(
						resultOthers);
				Paginator<SocialGroup> paginator = new Paginator<SocialGroup>(
						resultOthersList, request, "groupsList");
				request.setAttribute("groupsListPaginator", paginator);
			} else {
				request.setAttribute("groupsListPaginator", null);
			}
		} else {
			query = em
					.createQuery(
							"SELECT gs FROM SocialGroup gs ORDER BY gs.name,gs.description",
							SocialGroup.class);
			List<SocialGroup> resultOthersList = query.getResultList();
			em.getTransaction().commit();
			em.close();

			Paginator<SocialGroup> paginator = new Paginator<SocialGroup>(
					resultOthersList, request, "groupsList");

			request.setAttribute("groupsListPaginator", paginator);
		}

		return mapping.findForward("success");
	}

	/**
	 * @author SAID Mohamed
	 */
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

	/**
	 * @author SAID Mohamed
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.univartois.ili.fsnet.admin.actions.CrudAction#display(org.apache.struts
	 * .action.ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		DynaActionForm dynaForm = (DynaActionForm) form;

		List<SocialGroup> allGroups = null;
		List<SocialEntity> allMembers = null;
		List<SocialEntity> refusedMembers = null;
		List<SocialGroup> refusedGroups = null;
		List<SocialGroup> acceptedGroups = null;
		List<SocialEntity> acceptedMembers = null;
		Set<Right> acceptedRigths = null;
		Set<Right> refusedRigths = new HashSet<Right>();

		String id = request.getParameter("idGroup");

		if (id == null){
			id = (String) session.getAttribute("idGroup");
		}else{
			session.setAttribute("idGroup", id);
		}
		Integer idGroup = Integer.valueOf(id);
		SocialGroup group = socialGroupFacade.getSocialGroup(idGroup);
		SocialEntity masterGroup = group.getMasterGroup();
		SocialGroup parentGroup = group.getGroup();
		allGroups = socialGroupFacade.getAllSocialGroups();
		allGroups.removeAll(socialGroupFacade.getAllChildGroups(group));

		allMembers = socialEntityFacade.getAllSocialEntity();

		acceptedGroups = socialGroupFacade.getAcceptedSocialElementsByFilter(
				group, SocialGroup.class);
		acceptedMembers = socialGroupFacade.getAcceptedSocialElementsByFilter(
				group, SocialEntity.class);
		refusedMembers = getRefusedSocialMember(socialGroupFacade, allMembers,
				group);
		acceptedRigths = group.getRights();
		for (Right right : Right.values()) {
			refusedRigths.add(right);
		}

		refusedRigths.removeAll(acceptedRigths);

		refusedGroups = getRefusedSocialGroup(socialGroupFacade, group);

		dynaForm.set("id", id);
		dynaForm.set("name", group.getName());
		dynaForm.set("description", group.getDescription());
		request.setAttribute("parentGroup", parentGroup);
		request.setAttribute("masterGroup", masterGroup);
		request.setAttribute("acceptedGroups", acceptedGroups);
		request.setAttribute("acceptedMembers", acceptedMembers);
		request.setAttribute("allMembers",
				getSimpleMember(em, socialGroupFacade, group));
		request.setAttribute("allGroups", allGroups);
		request.setAttribute("refusedMembers", refusedMembers);
		request.setAttribute("refusedGroups", refusedGroups);
		request.setAttribute("refusedRigths", refusedRigths);
		request.setAttribute("acceptedRigths", acceptedRigths);

		em.close();

		return mapping.findForward("success");
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
	 * @param groupsAccepted
	 * @param masterGroup
	 * @param parentGroup
	 * @return
	 */
	private List<SocialElement> createSocialElement(EntityManager em,
			String[] membersAccepted, String[] groupsAccepted,
			SocialEntity masterGroup, SocialGroup parentGroup) {

		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		for (String members : membersAccepted){
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(members)));
		}
		for (String groups : groupsAccepted){
			socialElements.add(socialGroupFacade.getSocialGroup(Integer
					.valueOf(groups)));
		}
		if (!socialElements.contains(masterGroup)){
			socialElements.add(masterGroup);
		}
		if (parentGroup != null){
			socialElements.remove(parentGroup);
		}
		return socialElements;
	}

	/**
	 * @param sgf
	 * @param allMembers
	 * @param socialGroup
	 * @return
	 */
	public List<SocialEntity> getRefusedSocialMember(SocialGroupFacade sgf,
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
			if (se.getGroup() == null){
				resulEntities.add(se);
			}
		}
		return resulEntities;

	}

	/**
	 * @param sgf
	 * @param socialGroup
	 * @return
	 */
	public List<SocialGroup> getRefusedSocialGroup(SocialGroupFacade sgf,
			SocialGroup socialGroup) {
		if (sgf == null) {
			throw new IllegalArgumentException();
		}
		List<SocialGroup> resulGroups = new ArrayList<SocialGroup>();
		List<SocialGroup> allGroups = sgf.getAllSocialGroups();

		if (socialGroup != null) {
			List<SocialGroup> groups = sgf.getAcceptedSocialElementsByFilter(
					socialGroup, SocialGroup.class);
			allGroups.removeAll(groups);
			allGroups.removeAll(sgf.getAllAntecedentSocialGroups(socialGroup));
		}

		for (SocialGroup sg : allGroups) {
			if (sg.getGroup() == null && !sg.equals(socialGroup)){
				resulGroups.add(sg);
			}
		}

		return resulGroups;
	}

	/**
	 * @param em
	 * @param sgf
	 * @param socialGroup
	 * @return
	 */
	public List<SocialEntity> getSimpleMember(EntityManager em,
			SocialGroupFacade sgf, SocialGroup socialGroup) {
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		TypedQuery<SocialEntity> query = null;
		query = em.createQuery("SELECT g.masterGroup FROM SocialGroup g",
				SocialEntity.class);
		List<SocialEntity> allOrphanMembers = socialEntityFacade
				.getAllOrphanMembers();
		List<SocialEntity> mastersGroup = query.getResultList();
		mastersGroup.remove(socialGroup.getMasterGroup());
		List<SocialEntity> allMembers = sgf.getAllChildMembers(socialGroup);
		allMembers.removeAll(mastersGroup);
		allMembers.addAll(allOrphanMembers);

		return allMembers;
	}

}
