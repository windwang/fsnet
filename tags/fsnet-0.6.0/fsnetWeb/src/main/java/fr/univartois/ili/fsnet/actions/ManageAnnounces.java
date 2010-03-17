package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.utils.DateUtils;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.AnnouncementFacade;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.InterestFacade;
import fr.univartois.ili.fsnet.facade.security.UnauthorizedOperationException;

/**
 * 
 * @author Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
 */
public class ManageAnnounces extends MappingDispatchAction implements
        CrudAction {

    /**
     * @return to announces view after persisting new announce
     */
    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager entityManager = PersistenceProvider.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request,
                entityManager);

        DynaActionForm formAnnounce = (DynaActionForm) form; // NOSONAR
        String title = (String) formAnnounce.get("announceTitle");
        String content = (String) formAnnounce.get("announceContent");
        String stringExpiryDate = (String) formAnnounce.get("announceExpiryDate");
        String InterestsIds[] = (String[]) formAnnounce.get("selectedInterests");

        Announcement createdAnnounce;

        try {
            Date expiryDate = DateUtils.format(stringExpiryDate);
            if (0 > DateUtils.compareToToday(expiryDate)) {
                AnnouncementFacade announcementFacade = new AnnouncementFacade(entityManager);
                entityManager.getTransaction().begin();
                createdAnnounce = announcementFacade.createAnnouncement(user, title, content, expiryDate, false);
                InterestFacade fac = new InterestFacade(entityManager);
                List<Interest> interests = new ArrayList<Interest>();
                int currentId;
                for (currentId = 0; currentId < InterestsIds.length; currentId++) {
                    interests.add(fac.getInterest(Integer.valueOf(InterestsIds[currentId])));
                }
                InteractionFacade ifacade = new InteractionFacade(entityManager);
                ifacade.addInterests(createdAnnounce, interests);
                entityManager.getTransaction().commit();
            } else {
                ActionMessages errors = new ActionErrors();
                errors.add("message", new ActionMessage("error.dateBelowDateToday"));
                saveErrors(request, errors);
                entityManager.close();
                return mapping.findForward("failer");
            }
        } catch (ParseException e) {
            servlet.log(getClass().getName()
                    + " methode:create exception when formating date ");
            return mapping.findForward("failer");
        }
        entityManager.close();
        return mapping.findForward("success");
    }

    /**
     * @return to views announce after updating it
     */
    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, UnauthorizedOperationException {
        EntityManager entityManager = PersistenceProvider.createEntityManager();
        SocialEntity user = UserUtils.getAuthenticatedUser(request, entityManager);
        DynaActionForm formAnnounce = (DynaActionForm) form;// NOSONAR
        String title = (String) formAnnounce.get("announceTitle");
        String content = (String) formAnnounce.get("announceContent");
        String stringExpiryDate = (String) formAnnounce.get("announceExpiryDate");
        Integer idAnnounce = (Integer) formAnnounce.get("idAnnounce");
        AnnouncementFacade announcementFacade = new AnnouncementFacade(entityManager);
        Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
        try {
        	
        	if(!announce.getCreator().equals(user)){
    			throw new UnauthorizedOperationException("exception.message");
    		}
        	
            Date expiryDate = DateUtils.format(stringExpiryDate);
            if (0 > DateUtils.compareToToday(expiryDate)) {
                entityManager.getTransaction().begin();
                announcementFacade.modifyAnnouncement(idAnnounce, title, content, expiryDate);
                entityManager.getTransaction().commit();

            } else {
                ActionMessages errors = new ActionMessages();
                errors.add("message", new ActionMessage(
                        "error.dateBelowDateToday"));
                saveErrors(request, errors);
            }
            request.setAttribute("announce", announce);
            request.setAttribute("owner", true);
        } catch (ParseException e) {
            servlet.log("class:ManageAnnounces methode:create exception whene formatying date ");
            return mapping.findForward("failer");
        } finally {
            entityManager.close();
        }
        return mapping.findForward("success");
    }

    /**
     *
     */
    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager entityManager = PersistenceProvider.createEntityManager();
        entityManager.getTransaction().begin();
        AnnouncementFacade announcementFacade = new AnnouncementFacade(entityManager);
        Integer idAnnounce = Integer.valueOf(request.getParameter("idAnnounce"));
        Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
        ActionMessages message = new ActionErrors();
        InteractionFacade interactionFacade = new InteractionFacade(entityManager);
        SocialEntity user = UserUtils.getAuthenticatedUser(request, entityManager);
        interactionFacade.deleteInteraction(user, announce);
        entityManager.getTransaction().commit();
        entityManager.close();
        message.add("message", new ActionMessage("success.deleteAnnounce"));
        saveMessages(request, message);
        return mapping.findForward("success");

    }

    /**
     * @return list of announce
     */
    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm seaarchForm = (DynaActionForm) form;// NOSONAR
        String textSearchAnnounce = (String) seaarchForm.get("textSearchAnnounce");
        EntityManager entityManager = PersistenceProvider.createEntityManager();
        entityManager.getTransaction().begin();
        AnnouncementFacade announcementFacade = new AnnouncementFacade(entityManager);
        
        List<Announcement> listAnnounces = announcementFacade.searchAnnouncement(textSearchAnnounce);
        entityManager.getTransaction().commit();
        entityManager.close();
        
        Paginator<Announcement> paginator = new Paginator<Announcement>(listAnnounces, request, "listAnnounces");
        
        request.setAttribute("annoucesListPaginator", paginator);
        return mapping.findForward("success");
    }

    /**
     * @return announce in request
     */
    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager entityManager = PersistenceProvider.createEntityManager();
        entityManager.getTransaction().begin();
        SocialEntity SocialEntity = UserUtils.getAuthenticatedUser(request,
                entityManager);
        Integer idAnnounce = Integer.valueOf(request.getParameter("idAnnounce"));
        AnnouncementFacade announcementFacade = new AnnouncementFacade(entityManager);
        Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
        SocialEntity SocialEntityOwner = (SocialEntity) entityManager.createQuery(
                "SELECT es FROM SocialEntity es,IN(es.interactions) e WHERE e = :announce").setParameter("announce", announce).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();

        request.setAttribute("announce", announce);
        request.setAttribute("SocialEntity", SocialEntityOwner);
        servlet.log(SocialEntityOwner.toString() + SocialEntityOwner.getName());
        if (SocialEntity.getId() == SocialEntityOwner.getId()) {
            request.setAttribute("owner", true);
        }

        return mapping.findForward("success");
    }
    
    /**
     * @return announce in request
     */
    public ActionForward displayForModify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	DynaActionForm dynaForm = (DynaActionForm) form;//NOSONAR
        EntityManager em = PersistenceProvider.createEntityManager();
        em.getTransaction().begin();
        Integer idAnnounce = Integer.valueOf(request.getParameter("idAnnounce"));
        AnnouncementFacade announcementFacade = new AnnouncementFacade(em);
        Announcement announce = announcementFacade.getAnnouncement(idAnnounce);
        em.getTransaction().commit();
        em.close();
        dynaForm.set("idAnnounce", announce.getId());
        dynaForm.set("announceTitle", announce.getTitle());
        dynaForm.set("announceContent", announce.getContent());
        dynaForm.set("announceExpiryDate", DateUtils.renderDate(announce.getEndDate()));
        return mapping.findForward("success");
    }
}
