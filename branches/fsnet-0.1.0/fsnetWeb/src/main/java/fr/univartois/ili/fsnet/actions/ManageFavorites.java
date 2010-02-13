package fr.univartois.ili.fsnet.actions;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class ManageFavorites extends MappingDispatchAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");

    public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR
        int interactionId = Integer.parseInt((String) dynaForm.get("interactionId"));
        EntityManager em = factory.createEntityManager();
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

    public ActionForward remove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR
        int interactionId = Integer.parseInt((String) dynaForm.get("interactionId"));
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        Interaction interaction = em.find(Interaction.class, interactionId);

        if (interaction != null) {
            user.getFavoriteInteractions().remove(interaction);
            em.flush();
        }
        em.getTransaction().commit();
        em.close();
        return null;
    }

    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; //NOSONAR
        int interactionId = Integer.parseInt((String) dynaForm.get("interactionId"));
        EntityManager em = factory.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
        Interaction interaction = em.find(Interaction.class, interactionId);
        if (user.getFavoriteInteractions().contains(interaction)) {
            request.setAttribute("isFavorite", "true");
        } else {
            request.setAttribute("isFavorite", "false");
        }
        request.setAttribute("interactionId", interaction.getId());
        return mapping.findForward("success");
    }
}
