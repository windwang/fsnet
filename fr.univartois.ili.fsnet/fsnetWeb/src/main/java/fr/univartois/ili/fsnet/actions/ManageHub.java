package fr.univartois.ili.fsnet.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Interest;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.entities.TopicMessage;
import fr.univartois.ili.fsnet.facade.forum.iliforum.HubFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.InteractionFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.InterestFacade;

/**
 * 
 * @author Cerelia Besnainou and Audrey Ruellan
 */
public class ManageHub extends MappingDispatchAction implements CrudAction {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("fsnetjpa");
    private static final Logger logger = Logger.getAnonymousLogger();
    
	@Override
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		
		DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String hubName = (String) dynaForm.get("hubName");
		String communityId = (String) dynaForm.get("communityId");
		EntityManager em = factory.createEntityManager();
		Community community = em.find(Community.class, Integer.parseInt(communityId));
	
		
        //
        String InterestsIds[] = (String[]) dynaForm.get("selectedInterests");
        InterestFacade fac = new InterestFacade(em);
        List<Interest> interests = new ArrayList<Interest>();
        int currentId;
        for (currentId = 0; currentId < InterestsIds.length; currentId++) {
            interests.add(fac.getInterest(Integer.valueOf(InterestsIds[currentId])));
        }
        //
        
    	HubFacade hubFacade = new HubFacade(em);
		logger.info("new hub: " + hubName);
		SocialEntity user = UserUtils.getAuthenticatedUser(request, em);
		em.getTransaction().begin();
		Hub createdHub = hubFacade.createHub(community, user, hubName);
	
        InteractionFacade ifacade = new InteractionFacade(em);
        ifacade.addInterests(createdHub, interests);
        em.getTransaction().commit();
        em.close();

        return mapping.findForward("success");
    }

    @Override
    public ActionForward modify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActionForward delete(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
		String hubId = (String) dynaForm.get("hubId");

        logger.info("delete hub: " + hubId);

        EntityManager em = factory.createEntityManager();
        HubFacade hubFacade = new HubFacade(em);
        em.getTransaction().begin();
        hubFacade.delete(Integer.parseInt(hubId));
        em.getTransaction().commit();
        em.close();
        return mapping.findForward("success");
    }
    
    @Override
    public ActionForward search(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String communityId = (String) dynaForm.get("communityId");
			String hubName = (String) dynaForm.get("hubName");
					
			if (hubName == null) {
				hubName = "";
			} 
			logger.info("search hub: " + hubName);
			EntityManager em = factory.createEntityManager();
			Community community = em.find(Community.class, Integer.parseInt(communityId));
			HubFacade hubFacade = new HubFacade(em);
			em.getTransaction().begin();
			List<Hub> result = hubFacade.searchHub(hubName,community);
			em.getTransaction().commit();
			em.close();
			request.setAttribute("hubResults", result);

        return mapping.findForward("success");
    }

    @Override
    public ActionForward display(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    	DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
        String hubId = (String) dynaForm.get("hubId");
        logger.info("display hub: " + hubId);
        Map<Topic, Message> topicsLastMessage = new HashMap<Topic, Message>();
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        HubFacade hubFacade = new HubFacade(em);
        Hub result = hubFacade.getHub(Integer.parseInt(hubId));

        for (Topic t : result.getTopics()) {
            List<TopicMessage> messages = t.getMessages();
            Message lastMessage = null;
            if (messages.size() > 0) {
                lastMessage = messages.get(messages.size() - 1);
            }
            topicsLastMessage.put(t, lastMessage);
        }
        em.getTransaction().commit();
        em.close();
        request.setAttribute("hubResult", result);
        request.setAttribute("topicsLastMessage", topicsLastMessage);
        return mapping.findForward("success");
    }

    public ActionForward getAllInterest(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        EntityManager em = factory.createEntityManager();
        InterestFacade fac = new InterestFacade(em);
        List<Interest> listInterests = fac.getInterests();
        request.setAttribute("Interests", listInterests);
        em.close();
        return mapping.findForward("success");
    }
    
    public ActionForward searchYourHubs(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    		//TODO use facade
			DynaActionForm dynaForm = (DynaActionForm) form; // NOSONAR
			String pattern = (String) dynaForm.get("hubName");
			String communityId = (String) dynaForm.get("communityId");
			
			if (pattern==null) {
				pattern = "";
			}
			
			logger.info("search hub: " + pattern);
			
			EntityManager em = factory.createEntityManager();
			Community community = em.find(Community.class, Integer.parseInt(communityId));
			SocialEntity creator = UserUtils.getAuthenticatedUser(request, em);
			
			em.getTransaction().begin();
			List<Hub> hubs = em.createQuery("SELECT hub FROM Hub hub WHERE hub.title LIKE :hubName AND hub.community = :com AND hub.creator = :creator",
					Hub.class).setParameter("hubName", "%" + pattern + "%").setParameter("com", community).setParameter("creator", creator).getResultList();
	
			em.getTransaction().commit();
			em.close();
			request.setAttribute("hubResults", hubs);

        return mapping.findForward("success");
    }
    
    
    
}
