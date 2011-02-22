package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * Execute CRUD Actions (and more) for the entity SocialGroup
 * 
 * @author Bouragba mohamed
 */
public class ManageGroups extends MappingDispatchAction implements CrudAction {
	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("fsnetjpa");

	// private static final Logger logger = Logger.getAnonymousLogger();

	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		session.removeAttribute("idGroup");
		List<SocialEntity> allMembers = null;
		List<SocialGroup> allGroups = null;
		EntityManager em = factory.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		if (form != null) {

			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String name = (String) dynaForm.get("name");
			dynaForm.set("name", "");
			String description = (String) dynaForm.get("description");
			dynaForm.set("description", "");
			String owner = (String) dynaForm.get("socialEntityId");
			dynaForm.set("socialEntityId", "");
			String[] membersAccepted = (String[]) dynaForm
					.get("memberListRight");
			String[] groupsAccepted = (String[]) dynaForm.get("groupListRight");

			SocialEntity masterGroup = socialEntityFacade
					.getSocialEntity(Integer.valueOf(owner));
			List<SocialElement> socialElements = createSocialElement(em,
					membersAccepted, groupsAccepted);
			try {
				SocialGroup socialGroup = socialGroupFacade.createSocialGroup(
						masterGroup, name, description, socialElements);
				em.getTransaction().begin();
				em.persist(socialGroup);
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

			allMembers = socialEntityFacade.getAllSocialEntity();
			request.setAttribute("allMembers", allMembers);
			request.setAttribute("refusedMembers", allMembers);
			allGroups = socialGroupFacade.getAllSocialEntity();
			request.setAttribute("refusedGroups", allGroups);

		}

		return mapping.findForward("success");
	}
	/** 
	 * @author SAID Mohamed
	 */
	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		List<SocialEntity> allMembers = null;
		List<SocialGroup> allGroups = null;
		EntityManager em = factory.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);

		if (form != null) {
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			Integer socialGroupId = Integer.parseInt(dynaForm.getString("id"));
			String name = (String) dynaForm.get("name");
			dynaForm.set("name", "");
			String description = (String) dynaForm.get("description");
			dynaForm.set("description", "");
			String owner = (String) dynaForm.get("socialEntityId");
			dynaForm.set("socialEntityId", "");
			String[] membersAccepted = (String[]) dynaForm
					.get("memberListRight");
			String[] groupsAccepted = (String[]) dynaForm.get("groupListRight");

			SocialEntity masterGroup = socialEntityFacade
					.getSocialEntity(Integer.valueOf(owner));
			List<SocialElement> socialElements = createSocialElement(em,
					membersAccepted, groupsAccepted);
			try {

				SocialGroup socialGroup = socialGroupFacade
						.getSocialGroup(socialGroupId);

				socialGroup.setName(name);

				socialGroup.setDescription(description);
				socialGroup.setMasterGroup(masterGroup);
				socialGroup.setSocialElements(new ArrayList<SocialElement>());
				em.getTransaction().begin();

				em.merge(socialGroup);
				socialGroup.setSocialElements(socialElements);
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
			dynaForm.set("socialEntityId", "");
		} else {

			allMembers = socialEntityFacade.getAllSocialEntity();
			request.setAttribute("allMembers", allMembers);
			request.setAttribute("refusedMembers", allMembers);
			allGroups = socialGroupFacade.getAllSocialEntity();
			request.setAttribute("refusedGroups", allGroups);

		}

		return mapping.findForward("success");

	}

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
	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
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
				
				List<SocialGroup> resultOthersList = new ArrayList<SocialGroup>( resultOthers);
				//Collections.sort(resultOthersList);
				Paginator<SocialGroup> paginator = new Paginator<SocialGroup>(
						resultOthersList, request, "groupsList");
				request.setAttribute("groupsListPaginator", paginator);
			} else
				request.setAttribute("groupsListPaginator", null);
		} else {
			query = em.createQuery("SELECT gs FROM SocialGroup gs ORDER BY gs.name,gs.description",
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
	public ActionForward switchState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String GroupSelected = request.getParameter("groupSelected");
		EntityManager em = factory.createEntityManager();
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
	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		List<SocialEntity> allMembers = null;
		List<SocialEntity> refusedMembers = null;
		List<SocialGroup> refusedGroups = null;
		List<SocialGroup> acceptedGroups = null;
		List<SocialEntity> acceptedMembers = null;

		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		String id = request.getParameter("idGroup");

		if (id == null)
			id = (String) session.getAttribute("idGroup");
		else
			session.setAttribute("idGroup", id);
		Integer idGroup = Integer.valueOf(id);
		SocialGroup group = socialGroupFacade.getSocialGroup(idGroup);
		SocialEntity masterGroup = group.getMasterGroup();
		allMembers = socialEntityFacade.getAllSocialEntity();
		acceptedGroups = socialGroupFacade.getAcceptedSocialGroup(group);
		acceptedMembers = socialGroupFacade.getAcceptedSocialEntity(group);
		refusedMembers = getRefusedSocialMember(socialGroupFacade, allMembers,
				group);
		refusedGroups = getRefusedSocialGroup(socialGroupFacade, group);
		dynaForm.set("id", id);
		dynaForm.set("name", group.getName());
		dynaForm.set("description", group.getDescription());

		request.setAttribute("masterGroup", masterGroup);
		request.setAttribute("acceptedGroups", acceptedGroups);
		request.setAttribute("acceptedMembers", acceptedMembers);
		request.setAttribute("allMembers",
				socialEntityFacade.getAllSocialEntity());
		request.setAttribute("refusedMembers", refusedMembers);
		request.setAttribute("refusedGroups", refusedGroups);

		em.close();

		return mapping.findForward("success");
	}

	private List<SocialElement> createSocialElement(EntityManager em,
			String[] membersAccepted, String[] groupsAccepted) {

		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		for (String members : membersAccepted)
			socialElements.add(socialEntityFacade.getSocialEntity(Integer
					.valueOf(members)));
		for (String groups : groupsAccepted)
			socialElements.add(socialGroupFacade.getSocialGroup(Integer
					.valueOf(groups)));

		return socialElements;
	}

	public List<SocialEntity> getRefusedSocialMember(SocialGroupFacade sgf,
			List<SocialEntity> allMembers, SocialGroup socialGroup) {
		if (socialGroup == null || sgf == null) {
			throw new IllegalArgumentException();
		}

		List<SocialEntity> members = allMembers;
		members.removeAll(sgf.getAcceptedSocialEntity(socialGroup));

		return members;

	}

	public List<SocialGroup> getRefusedSocialGroup(SocialGroupFacade sgf,
			SocialGroup socialGroup) {
		if (socialGroup == null || sgf == null) {
			throw new IllegalArgumentException();
		}

		List<SocialGroup> groups = sgf.getAcceptedSocialGroup(socialGroup);
		List<SocialGroup> allGroups = sgf.getAllSocialEntity();
		allGroups.removeAll(groups);

		return allGroups;
	}

}
