package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.logging.Logger;

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
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;

public class ManageInteractions extends MappingDispatchAction {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public ActionForward removeInterest(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR
        int interactionId = Integer.parseInt((String) dynaForm.get("interactionId"));
        int interestId = Integer.parseInt((String) dynaForm.get("interestId"));
        
        EntityManager em = PersistenceProvider.createEntityManager();
        em.getTransaction().begin();
        
		SocialEntity member = UserUtils.getAuthenticatedUser(request, em);
		
		InteractionFacade interactionFacade = new InteractionFacade(em);
        Interaction interaction = interactionFacade.getInteraction(interactionId);
        
        InterestFacade interestFacade = new InterestFacade(em);
        Interest interest = interestFacade.getInterest(interestId);
        
        interactionFacade.removeInterest(member, interaction, interest);
        em.getTransaction().commit();
        LOGGER.info("removeInterestOfInteraction : interactionId:" + interactionId + ";interestId:" + interestId);
        return new ActionRedirect(request.getHeader("referer"));
    }
}
