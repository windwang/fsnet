package fr.univartois.ili.fsnet.admin.actions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
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
import org.eclipse.persistence.exceptions.DatabaseException;

import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.facade.InterestFacade;

/**
 * Execute CRUD Actions (and more) for the entity interet
 * 
 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
 */
public class ManageInterests extends MappingDispatchAction implements
		CrudAction {
	private static final Logger logger = Logger.getAnonymousLogger();
	private static final String SEPARATOR_PARENT_CHILD = "=";
	private static final String SEPARATOR_MAP = ";";

	 public void creation(DynaActionForm dynaForm,InterestFacade facade,String interestName,EntityManager em,HttpServletRequest request){
	        if (dynaForm.get("parentInterestId") != null
	                && !((String) dynaForm.get("parentInterestId")).isEmpty()) {
	            facade.createInterest(interestName, Integer.valueOf((String) dynaForm.get("parentInterestId")));
	        } else {
	        	facade.createInterest(interestName); 
	        }

	    }
	
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		InterestFacade facade = new InterestFacade(em);
		String interestName = (String) dynaForm.get("createdInterestName");
		String[] interestNameTmp;
		
		logger.info("new interest: " + interestName);

		try {
        	em.getTransaction().begin();
            if(interestName.contains(";")){
            	interestNameTmp=interestName.split(";");
            	for(String myInterestName : interestNameTmp){
            		myInterestName=myInterestName.trim();
            		if(!myInterestName.isEmpty()){
            			creation(dynaForm,facade,myInterestName,em,request);
            		}
            	}
            }else{
            	creation(dynaForm,facade,interestName,em,request);
            }
            em.getTransaction().commit();    
            
        } catch (RollbackException ex) {
            ActionErrors actionErrors = new ActionErrors();
            ActionMessage msg = new ActionMessage("interest.alreadyExists");
            actionErrors.add("createdInterestName", msg);
            saveErrors(request, actionErrors);
        }

		em.close();
		dynaForm.set("createdInterestName","");
		return mapping.findForward("success");
	}

	@Override
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		int interestId = Integer.valueOf((String) dynaForm
				.get("modifiedInterestId"));
		String interestName = (String) dynaForm.get("modifiedInterestName");
		InterestFacade facade = new InterestFacade(em);

		Interest interest = facade.getInterest(interestId);

		if (interest != null) {
			logger.info("interest modification: " + interestName);

			try {
				em.getTransaction().begin();
				if (dynaForm.get("parentInterestId") != null
						&& !((String) dynaForm.get("parentInterestId"))
								.isEmpty()) {
					facade.modifyInterest(interestName, interest,
							Integer.valueOf(((String) dynaForm
									.get("parentInterestId"))));
				} else {
					facade.modifyInterest(interestName, interest);
				}
				em.getTransaction().commit();
			} catch (DatabaseException ex) {
				ActionErrors actionErrors = new ActionErrors();
				ActionMessage msg = new ActionMessage("interest.alreadyExists");
				actionErrors.add("modifiedInterestName", msg);
				saveErrors(request, actionErrors);
			}
		}

		em.close();
		
		dynaForm.set("modifiedInterestId", "");
		dynaForm.set("modifiedInterestName", "");
		if (dynaForm.get("parentInterestId") != null
				&& !((String) dynaForm.get("parentInterestId"))
						.isEmpty()) {
			dynaForm.set("parentInterestId", "");
		}
		
		return mapping.findForward("success");
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();

		// TODO verify if user has the right to do delete

		int interestId = Integer.valueOf(request
				.getParameter("deletedInterestId"));
		InterestFacade facade = new InterestFacade(em);
		Interest interest = facade.getInterest(interestId);

		if (interest != null) {
			logger.info("interest deleted: id=" + interestId);

			try {
				em.getTransaction().begin();
				facade.deleteInterest(interest);
				em.getTransaction().commit();
			} catch (RollbackException ex) {
				ActionErrors actionErrors = new ActionErrors();
				ActionMessage msg = new ActionMessage("interest.notExists");
				actionErrors.add("error.interest.delete", msg);
				saveErrors(request, actionErrors);
			}
		}
		em.close();

		return mapping.findForward("success");
	}

	@Override
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR
		String interestName = "";

		if (dynaForm.get("searchInterestName") != null) {
			interestName = (String) dynaForm.get("searchInterestName");
		}
		InterestFacade facade = new InterestFacade(em);
		logger.info("search interest: " + interestName);

		List<Interest> result = facade.searchInterest(interestName);
		em.close();

		Paginator<Interest> paginator = new Paginator<Interest>(result, request, 25, "search");
		
		request.setAttribute("interestSearchPaginator", paginator);

		return mapping.findForward("success");
	}

	@Override
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		InterestFacade facade = new InterestFacade(em);
		logger.info("Displaying interests");

		List<Interest> listAllInterests = facade.getInterests();
		String allInterestsId = concatInterest(listAllInterests);
		em.close();
		
		Paginator<Interest> paginator = new Paginator<Interest>(listAllInterests, request, 25, "search");
		
		request.setAttribute("interestSearchPaginator", paginator);
		request.setAttribute("allInterests", listAllInterests);		
		request.setAttribute("allInterestsId", allInterestsId);
		
		return mapping.findForward("success");
	}

	public ActionForward informations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		InterestFacade facade = new InterestFacade(em);
		logger.info("Displaying interest's informations");
		DynaActionForm dynaForm = (DynaActionForm) form;// NOSONAR

		int interestId = Integer.valueOf((String) dynaForm
				.get("infoInterestId"));

		Interest interest = facade.getInterest(interestId);
		Map<String, List<Interaction>> resultMap = facade
				.getInteractions(interestId);
		em.close();

		if (interest != null) {
			request.setAttribute("interest", interest);
			for (Map.Entry<String, List<Interaction>> interactionClass : resultMap
					.entrySet()) {
				request.setAttribute(interactionClass.getKey(),
						interactionClass.getValue());
			}
		}

		return mapping.findForward("success");
	}
	
	private String concatInterest(List<Interest> listAllInterests){
		String ids = "";
		if(listAllInterests == null)
			return "";
		for(Interest interest : listAllInterests){
			Interest parent = interest.getParentInterest();
			String id = String.valueOf( interest.getId() );
			String idParent;
			String tmp = "";
			if(parent == null)
				idParent = "-1";
			else idParent = String.valueOf( parent.getId() );
			tmp = tmp.concat( id );
			tmp = tmp.concat( SEPARATOR_PARENT_CHILD );
			tmp = tmp.concat( idParent );
			ids = ids.concat( tmp );
			ids = ids.concat( SEPARATOR_MAP);
		}
		return ids;
	}
}