package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
 *
 */
public class ManageGroups extends MappingDispatchAction implements CrudAction {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
	private static final Logger logger = Logger.getAnonymousLogger();
	
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
		
		if(form!=null){
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String name = (String) dynaForm.get("name");
			dynaForm.set("name", "");
			String description = (String) dynaForm.get("description");
			dynaForm.set("description", "");
			String owner = (String) dynaForm.get("socialEntityId");
			dynaForm.set("socialEntityId", "");
			String[] membersAccepted = (String[]) dynaForm.get("memberListRight");
			String[] groupsAccepted = (String[]) dynaForm.get("groupListRight");
			
			SocialEntity masterGroup = socialEntityFacade.getSocialEntity(Integer.valueOf(owner));
			List<SocialElement> socialElements = createSocialElement(em,membersAccepted,groupsAccepted);
			try {
				SocialGroup socialGroup = socialGroupFacade.createSocialGroup(masterGroup, name, description, socialElements);
				em.getTransaction().begin();
				em.persist(socialGroup);
				em.getTransaction().commit();
			}catch (RollbackException e) {
				ActionErrors errors = new ActionErrors();
				errors.add("name", new ActionMessage("groups.name.exists"));
				saveErrors(request, errors);
				return mapping.findForward("errors");
			} 
			em.close();
			dynaForm.set("name", "");
			dynaForm.set("description", "");
			dynaForm.set("socialEntityId", "");
		}else{
			allMembers = socialEntityFacade.getAllSocialEntity();
			request.setAttribute("allMembers", allMembers);
			allGroups = socialGroupFacade.getAllSocialEntity();
			request.setAttribute("allGroups", allGroups);
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
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<SocialElement> createSocialElement(EntityManager em,String[] membersAccepted,String[] groupsAccepted){
		List<SocialElement> socialElements = new ArrayList<SocialElement>();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade socialGroupFacade = new SocialGroupFacade(em);
		for(String members : membersAccepted)
			socialElements.add( socialEntityFacade.getSocialEntity(Integer.valueOf( members )) );
		for(String groups : groupsAccepted)
			socialElements.add( socialGroupFacade.getSocialGroup(Integer.valueOf(groups)) );
		return socialElements;
	}

}
