package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.MappingDispatchAction;

import fr.univartois.ili.fsnet.actions.utils.UserUtils;
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.CommunityFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.HubFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.InteractionFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.InterestFacade;
import java.util.ArrayList;

/**
 * Execute CRUD Actions (and more) for the entity community
 * 
 * @author Audrey Ruellan and Cerelia Besnainou
 */
public class ManageCommunities extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public ActionForward create(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
        String name = (String) dynaForm.get("name");

        logger.info("#### New Community : " + name);
        EntityManager em = factory.createEntityManager();
        CommunityFacade communityFacade = new CommunityFacade(em);
        SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);
        em.getTransaction().begin();
        Community createdCommunity = communityFacade.createCommunity(creator, name);

        String InterestsIds[] = (String[]) dynaForm.get("selectedInterests");
        InterestFacade fac = new InterestFacade(em);
        List<Interest> interests = new ArrayList<Interest>();
        int currentId;
        for (currentId = 0; currentId < InterestsIds.length; currentId++) {
            interests.add(fac.getInterest(Integer.valueOf(InterestsIds[currentId])));
        }
        InteractionFacade ifacade = new InteractionFacade(em);
        ifacade.addInterests(createdCommunity, interests);

        em.getTransaction().commit();
        em.close();

        return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
        String communityId = (String) dynaForm.get("communityId");
        logger.info("display community: " + communityId);

        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        CommunityFacade communityFacade = new CommunityFacade(em);
        Community result = communityFacade.getCommunity(Integer.parseInt(communityId));
        HubFacade hubFacade = new HubFacade(em);
        List<Hub> resultHubs = hubFacade.searchHub("", result);
        em.getTransaction().commit();
        em.close();
        request.setAttribute("hubResults", resultHubs);
        return mapping.findForward("success");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
        String communityId = (String) dynaForm.get("communityId");

        logger.info("delete community: " + communityId);

        EntityManager em = factory.createEntityManager();
        CommunityFacade communityFacade = new CommunityFacade(em);
        em.getTransaction().begin();
        communityFacade.deleteCommunity(Integer.parseInt(communityId));
        em.getTransaction().commit();
        em.close();

        return mapping.findForward("success");

    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        List<Community> result = null;
        String searchText = "";
        CommunityFacade communityFacade = new CommunityFacade(em);
        if (form != null) {
            DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
            searchText = (String) dynaForm.get("searchText");

        }
        em.getTransaction().begin();
        result = communityFacade.searchCommunity(searchText);
        em.getTransaction().commit();
        em.close();


        request.setAttribute("communitiesResult", result);
        return mapping.findForward("success");
    }
    
    public ActionForward searchYourCommunities(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	//TODO use facade
        EntityManager em = factory.createEntityManager();
        List<Community> result = null;
        String pattern = "";
        SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);

        if (form != null) {
            DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
            pattern = (String) dynaForm.get("searchText");

        }
        em.getTransaction().begin();
        
        TypedQuery<Community> query = em.createQuery("SELECT community FROM Community community WHERE community.title LIKE :pattern AND community.creator= :creator", Community.class);
        query.setParameter("pattern", "%" + pattern + "%");
        query.setParameter("creator", creator);
        result = query.getResultList();

        em.getTransaction().commit();
        em.close();


        request.setAttribute("communitiesResult", result);
        return mapping.findForward("success");
    }
}
