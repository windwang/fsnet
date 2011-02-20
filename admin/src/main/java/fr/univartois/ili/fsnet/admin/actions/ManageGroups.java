package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * Execute CRUD Actions (and more) for the entity SocialGroup
 * 
 * @author Bouragba mohamed
 * @author SAID Mohamed
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

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return mapping.findForward("success");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = factory.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;
		List<SocialEntity> allMembers = null;
		List<SocialEntity> refusedMembers = null;
		List<SocialGroup> refusedGroups = null;
		List<SocialGroup> acceptedGroups = null;
		List<SocialEntity> acceptedMembers = null;

		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);

		Integer idGroup = Integer.valueOf(request.getParameter("idGroup"));
		SocialGroup group = socialGroupFacade.getSocialGroup(idGroup);
		SocialEntity masterGroup = group.getMasterGroup();
		allMembers = socialEntityFacade.getAllSocialEntity();
		acceptedGroups = socialGroupFacade.getAcceptedSocialGroup(group);
		acceptedMembers = socialGroupFacade.getAcceptedSocialEntity(group);
		refusedMembers = getRefusedSocialMember(socialGroupFacade, allMembers,
				group);
		refusedGroups = getRefusedSocialGroup(socialGroupFacade, group);

		dynaForm.set("name", group.getName());
		dynaForm.set("description", group.getDescription());
		request.setAttribute("id", group.getId());
		request.setAttribute("masterGroup", masterGroup);
		request.setAttribute("acceptedGroups", acceptedGroups);
		request.setAttribute("acceptedMembers", acceptedMembers);
		request.setAttribute("allMembers", socialEntityFacade.getAllSocialEntity());
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
