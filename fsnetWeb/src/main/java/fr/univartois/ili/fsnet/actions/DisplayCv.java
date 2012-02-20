package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Curriculum;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.CvFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InteractionRoleFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;

public class DisplayCv extends MappingDispatchAction{
	
	
	private void addRightToRequest(HttpServletRequest request) {
		SocialEntity socialEntity = UserUtils.getAuthenticatedUser(request);
		Right rightAddEvent = Right.ADD_EVENT;
		Right rightRegisterEvent = Right.REGISTER_EVENT;
		request.setAttribute("rightAddEvent", rightAddEvent);
		request.setAttribute("rightRegisterEvent", rightRegisterEvent);
		request.setAttribute("socialEntity", socialEntity);
	}
	
	
	
	public static final void refreshNumNewEvents(HttpServletRequest request,
			EntityManager em) {
		HttpSession session = request.getSession();
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		InteractionFacade inf = new InteractionFacade(em);
		List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);
		int numNonReedEvents = Interaction.filter(list, Meeting.class).size();
		session.setAttribute("numNonReedEvents", numNonReedEvents);
	}
	
	 public ActionForward display(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws IOException, ServletException {
		 
		 
		 
		 EntityManager em = PersistenceProvider.createEntityManager();
			addRightToRequest(request);

		em.getTransaction().begin();
			
		CvFacade cvfacade=new CvFacade(em);	
		 
//		 BeanCv test=new BeanCv();
//		 test.setId(1);
//		 test.setTitle("titre");
//		 test.setFirstName("ayoub");
		 List<Curriculum> result=cvfacade.listAllCv();
		 
		 
		
		 em.close();
		 
		 request.setAttribute("CVsList", result);
		 
//		 DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
//			String eventId = (String) dynaForm.get("eventId");
//			EntityManager em = PersistenceProvider.createEntityManager();
//			addRightToRequest(request);
//			em.getTransaction().begin();
//
//			SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
//			request.setAttribute("member", member);
//
//			MeetingFacade meetingFacade = new MeetingFacade(em);
//			try {
//				Meeting event = meetingFacade.getMeeting(Integer.parseInt(eventId));
//				member.addInteractionRead(event);
//
//				InteractionRoleFacade interactionRoleFacade = new InteractionRoleFacade(
//						em);
//				boolean isSubscriber = interactionRoleFacade.isSubsriber(member,
//						event);
//				Set<SocialEntity> subscribers = interactionRoleFacade
//						.getSubscribers(event);
//
//				refreshNumNewEvents(request, em);
//				em.getTransaction().commit();
//
//				em.close();
//
//				// TODO find a solution to paginate a Set
//
//				request.setAttribute("subscribers", subscribers);
//				request.setAttribute("subscriber", isSubscriber);
//				request.setAttribute("event", event);
//			} catch (NumberFormatException e) {
//			}
		 
		 
		 return mapping.findForward("success");
		 
	 }
}
