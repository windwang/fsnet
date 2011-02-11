package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ConsultationFacade;

public class ManageConsultations extends MappingDispatchAction {
	
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; 
		String consultationTitle = (String) dynaForm.get("consultationTitle");
		String consultationDescription = (String) dynaForm.get("consultationDescription");	
		String[] consultationChoices  = dynaForm.getStrings("consultationChoice");
		for (String cs : consultationChoices){
			if ("".equals(cs)){
				request.setAttribute("errorChoice", true);
				return new ActionRedirect(mapping.findForward("error"));
			}
		}
	
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.createConsultation(member,consultationTitle,consultationDescription,consultationChoices);
		em.getTransaction().commit();
		em.close();
		request.setAttribute("id", consultation.getId());
		return displayAConsultation(mapping, dynaForm, request, response);
	}
	
	
	public ActionForward vote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		DynaActionForm dynaForm = (DynaActionForm) form; 
		String voteComment = (String) dynaForm.get("voteComment");	
		Integer idConsultation = (Integer) dynaForm.get("id");
		String[] voteChoices  = dynaForm.getStrings("voteChoice");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		consultationFacade.voteForConsultation(member, idConsultation, voteComment, "", Arrays.asList(voteChoices));
		em.getTransaction().commit();
		em.close();
		return displayAConsultation(mapping, dynaForm, request, response);
	}
	

	public ActionForward searchYourConsultations(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		EntityManager em = PersistenceProvider.createEntityManager();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		List<Consultation> listConsultations = consultationFacade.getUserConsultations(member);
		Paginator<Consultation> paginator = new Paginator<Consultation>(listConsultations, request, "listConsultations");
		request.setAttribute("consultationsListPaginator", paginator);
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		return redirect;
	}
	
	public ActionForward displayAConsultation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String idConsultation = request.getParameter("id");
		if (idConsultation == null || "".equals(idConsultation)){
			idConsultation = String.valueOf(request.getAttribute("id"));
		} 
		if (idConsultation != null){
			EntityManager em = PersistenceProvider.createEntityManager();
			ConsultationFacade consultationFacade = new ConsultationFacade(em);
			Consultation consultation = consultationFacade.getConsultation(Integer.valueOf(idConsultation));
			em.close();
			request.setAttribute("consultation", consultation);
		}
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		return redirect;
	}
	
}
