package fr.univartois.ili.fsnet.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.Meeting;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.InteractionFacade;
import fr.univartois.ili.fsnet.facade.MeetingFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

/**
 * This webservice is used by fsnet heavy clients to get informations on new
 * events, announces, mesages etc...
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
@WebService()
public class Info {

    private EntityManager em;
    private Date dt;
    
    private SocialEntityFacade socialEntityFacade;

    public Info() {
        em = PersistenceProvider.createEntityManager();
        this.dt = new Date();
    }

    // TODO check login and password for each method
    /**
     * Return the number of unread events
     *
     * @param login
     * @param password
     * @return
     */
    @WebMethod(operationName = "getNewEventsCount")
    public Integer getNewEventsCount(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
    	
    	SocialEntityFacade sef = new SocialEntityFacade(em);
        InteractionFacade inf = new InteractionFacade(em);
        if (sef.isMember(login, password)) {
       		SocialEntity user = sef.getSocialEntityByEmail(login);
       		if(user!=null){
       			List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);
       			return Interaction.filter(list, Meeting.class).size();
       		}
        }
        return 0;
    }

    /**
     * Return the number of unread announcement since the last user's connection
     *
     * @param login
     * @param password
     * @return
     */
    @WebMethod(operationName = "getNewAnnouncementCount")
    public Integer getNewAnnouncementCount(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
    	
    	 SocialEntityFacade sef = new SocialEntityFacade(em);
    	 InteractionFacade inf = new InteractionFacade(em);
         if (sef.isMember(login, password)) {
        		SocialEntity user = sef.getSocialEntityByEmail(login);
        		if(user!=null){
        			List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);
        			return Interaction.filter(list, Announcement.class).size();
        		}
         }
         return 0;
    }
    
    /**
     * Return the number of unread consultations since the last user's connection
     *
     * @param login
     * @param password
     * @return
     */
    @WebMethod(operationName = "getNewConsultationCount")
    public Integer getNewConsultationCount(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
    	
    	 SocialEntityFacade sef = new SocialEntityFacade(em);
    	 InteractionFacade inf = new InteractionFacade(em);
         if (sef.isMember(login, password)) {
        		SocialEntity user = sef.getSocialEntityByEmail(login);
        		if(user!=null){
        			List<Interaction> list = inf.getUnreadInteractionsForSocialEntity(user);
        			return Interaction.filter(list, Consultation.class).size();
        		}
         }
         return 0;
    }
  

    /**
     * Return the number of new demande contact
     *
     * @param login
     * @param password
     * @return
     */
    @WebMethod(operationName = "getNewDemandeCount")
    public Integer getNewDemandeCount(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
        SocialEntityFacade sef = new SocialEntityFacade(em);
        Query ql1 = null;
        if (sef.isMember(login, password)) {
            SocialEntity se = sef.findByEmail(login);
            ql1 = em.createQuery("SELECT s.asked FROM SocialEntity s  where s.id=:member ");
            ql1.setParameter("member", se.getId());
        }
        return ql1.getResultList().size();
    }

    /**
     * Return the number of unread personal messages
     *
     * @param login
     * @param password
     * @return
     */
    @WebMethod(operationName = "getNewMessages")
    public List<WsPrivateMessage> getNewMessages(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
        List<WsPrivateMessage> messages = new ArrayList<WsPrivateMessage>();
        SocialEntityFacade sef = new SocialEntityFacade(em);
        if (sef.isMember(login, password)) {
            SocialEntity se = sef.findByEmail(login);
            TypedQuery<PrivateMessage> messageQuery = em.createQuery(
                    "SELECT message FROM SocialEntity soc, IN(soc.receivedPrivateMessages) message where message.reed='false' and soc=:member",
                    PrivateMessage.class);
            messageQuery.setParameter("member", se);
            for (PrivateMessage message : messageQuery.getResultList()) {
                messages.add(new WsPrivateMessage(message));
            }
        }
        return messages;
    }

    /**
     *
     * @param login
     *            the login to check
     * @param password
     *            the password corresponding to the login
     * @return true if informations correspond to a member
     */
    public boolean isMember(@WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
        SocialEntityFacade sef = new SocialEntityFacade(em);
        return sef.isMember(login, password);
    }
}
