package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import fr.univartois.ili.fsnet.entities.ConsultationVote;
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
//		String[] maxVoters = dynaForm.getStrings("maxVoters");
		String consultationType = dynaForm.getString("consultationType");
		String consultationIfNecessaryWeight = dynaForm.getString("consultationIfNecessaryWeight");
//		String nbVotersPerChoiceBox = dynaForm.getString("nbVotersPerChoiceBox");
//		String limitChoicesPerVoter = dynaForm.getString("limitChoicesPerVoter");
//		String minChoicesVoter = dynaForm.getString("minChoicesVoter");
//		String maxChoicesVoter = dynaForm.getString("maxChoicesVoter");
//		String showBeforeClosing = dynaForm.getString("showBeforeClosing");
//		String showBeforeAnswer = dynaForm.getString("showBeforeAnswer");
//		String deadline = dynaForm.getString("deadline");
//		String closingAtMaxVoters = dynaForm.getString("closingAtMaxVoters");
		
		// TODO chercher le moyen de valider les choix avec struts
		for (String cs : consultationChoices){
			if ("".equals(cs)){
				request.setAttribute("errorChoice", true);
				return new ActionRedirect(mapping.findForward("error"));
			}
		}
//		for(int i = 0;i<maxVoters.length;i++){
//			if("".equals(maxVoters[i]))
//				maxVoters[i] = "-1"; // Unlimited
//			else if(!IntegerValidator.getInstance().isValid(maxVoters[i])){
//				request.setAttribute("errorMaxVotersPerChoice", true);
//				return new ActionRedirect(mapping.findForward("error"));
//			}
//		}
		//END TODO
		
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.createConsultation(member,consultationTitle,consultationDescription,consultationChoices,Consultation.TypeConsultation.valueOf(consultationType));
//		for(int i = 0; i < maxVoters.length; i++)
//			consultation.getChoices().get(i).setMaxVoters(Integer.valueOf(maxVoters[i]));
//		if(!"".equals(nbVotersPerChoiceBox))
//			consultation.setLimitParticipantPerChoice(true);
//		
//		if(!"".equals(showBeforeAnswer))
//			consultation.setShowBeforeAnswer(false);
//		
//		if(!"".equals(showBeforeClosing))
//			consultation.setShowBeforeClosing(false);
//		
//		if(!"".equals(deadline)){
//			consultation.setClosingAtDate(true);
//			try {
//				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//				consultation.setMaxDate(dateFormat.parse(deadline));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		if(!"".equals(closingAtMaxVoters)){
//			consultation.setClosingAtMaxVoters(true);
//			consultation.setMaxVoters(Integer.valueOf(closingAtMaxVoters));
//		}
//		
		if(!"".equals(consultationIfNecessaryWeight)){
			consultation.setIfNecessaryWeight(Double.valueOf(consultationIfNecessaryWeight));
		}
				
		
//		if(!"".equals(limitChoicesPerVoter)){
//			consultation.setLimitChoicesPerParticipant(true);
//			if(!"".equals(maxChoicesVoter)){
//				consultation.setLimitChoicesPerParticipantMax(Integer.valueOf(maxChoicesVoter));
//			}
//			if(!"".equals(minChoicesVoter)){
//				consultation.setLimitChoicesPerParticipantMin(Integer.valueOf(minChoicesVoter));
//			}
//		}
		
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
		String voteOther = (String) dynaForm.get("voteOther");
		Integer idConsultation = (Integer) dynaForm.get("id");
		String[] voteChoices  = dynaForm.getStrings("voteChoice");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.getConsultation(idConsultation);
		if (isAllowedToVote(consultation, member)){
			consultationFacade.voteForConsultation(member, consultation, voteComment, voteOther, Arrays.asList(voteChoices));
			em.getTransaction().commit();
		}
		em.close();
		return displayAConsultation(mapping, dynaForm, request, response);
	}
	
	public ActionForward deleteVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String idConsultation = request.getParameter("consultation");
		String idVote = request.getParameter("vote");
		if (idConsultation != null && !"".equals(idConsultation)){
			request.setAttribute("id", idConsultation);
			if (idVote != null && !"".equals(idVote)){
				EntityManager em = PersistenceProvider.createEntityManager();
				SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
				ConsultationFacade consultationFacade = new ConsultationFacade(em);
				Consultation consultation = consultationFacade.getConsultation(Integer.valueOf(idConsultation));
				ConsultationVote vote = consultationFacade.getVote(Integer.valueOf(idVote));
				if (consultation.isOpened()){ // TODO max date 
					em.getTransaction().begin();
					consultationFacade.deleteVote(consultation,member,vote);
					em.getTransaction().commit();
				}
				em.close();
			}
		}
		return displayAConsultation(mapping, form, request, response);
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
			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
			request.setAttribute("member", member);
			ConsultationFacade consultationFacade = new ConsultationFacade(em);
			Consultation consultation = consultationFacade.getConsultation(Integer.valueOf(idConsultation));
			em.close();
			request.setAttribute("consultation", consultation);
			if (isAllowedToVote(consultation, member))
				request.setAttribute("allowedToVote", true);
			else
				request.setAttribute("allowedToVote", false);
		}
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		return redirect;
	}
	
	public ActionForward closeConsultation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String idConsultation = request.getParameter("id");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.getConsultation(Integer.valueOf(idConsultation));
		if(member.equals(consultation.getCreator())){
			em.getTransaction().begin();
			consultationFacade.closeConsultation(consultation);
			em.getTransaction().commit();
			em.close();		
		}
		return displayAConsultation(mapping, form, request, response);
	}
	
	public ActionForward openConsultation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String idConsultation = request.getParameter("id");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.getConsultation(Integer.valueOf(idConsultation));
		if(member.equals(consultation.getCreator())){
			em.getTransaction().begin();
			consultationFacade.openConsultation(consultation);
			em.getTransaction().commit();
			em.close();		
		}
		return displayAConsultation(mapping, form, request, response);
	}
	
	public ActionForward deleteConsultation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		String idConsultation = request.getParameter("id");
		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		ConsultationFacade consultationFacade = new ConsultationFacade(em);
		Consultation consultation = consultationFacade.getConsultation(Integer.valueOf(idConsultation));
		if(member.equals(consultation.getCreator())){
			em.getTransaction().begin();
			consultationFacade.deleteConsultation(consultation,member);
			em.getTransaction().commit();
			em.close();		
		}
		ActionRedirect redirect = new ActionRedirect(mapping.findForward("success"));
		return redirect;
	}
	
	public boolean isAllowedToVote(Consultation consultation, SocialEntity member) {
		return consultation.isOpened() && !consultation.isVoted(member);
	}
	
}
