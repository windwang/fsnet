package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * @author FSNet
 *
 */
public class ManageFavorites extends MappingDispatchAction {

	
	private static final String INTERACTION_ID_FORM_FIELD_NAME = "interactionId";

	
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR
        int interactionId = Integer.parseInt((String) dynaForm.get(INTERACTION_ID_FORM_FIELD_NAME));
        EntityManager em = PersistenceProvider.createEntityManager();
        em.getTransaction().begin();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        Interaction interaction = em.find(Interaction.class, interactionId);
        if (interaction != null) {
            user.getFavoriteInteractions().add(interaction);
            interaction.getFollowingEntitys().add(user);
        }
        em.getTransaction().commit();
        em.close();
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
    public ActionForward remove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR
        int interactionId = Integer.parseInt((String) dynaForm.get(INTERACTION_ID_FORM_FIELD_NAME));
        EntityManager em = PersistenceProvider.createEntityManager();
        em.getTransaction().begin();

        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        Interaction interaction = em.find(Interaction.class, interactionId);

        if (interaction != null) {
        	interaction.getFollowingEntitys().remove(user);
            user.getFavoriteInteractions().remove(interaction);
            em.flush();
        }
        em.getTransaction().commit();
        em.close();
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
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR
        int interactionId = Integer.parseInt((String) dynaForm.get(INTERACTION_ID_FORM_FIELD_NAME));
        EntityManager em = PersistenceProvider.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        Interaction interaction = em.find(Interaction.class, interactionId);
        if (user.getFavoriteInteractions().contains(interaction)) {
            request.setAttribute("isFavorite", true);
        } else {
            request.setAttribute("isFavorite", false);
        }
        em.close();
        request.setAttribute(INTERACTION_ID_FORM_FIELD_NAME, interaction.getId());
        return mapping.findForward("success");
    }
}
