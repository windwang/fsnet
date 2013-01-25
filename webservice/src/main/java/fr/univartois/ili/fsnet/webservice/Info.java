package fr.univartois.ili.fsnet.webservice;

import java.util.ArrayList;
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
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

/**
 * This webservice is used by fsnet heavy clients to get informations on new
 * events, announces, mesages etc...
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
@WebService()
public class Info {

    private EntityManager em;
    private static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";


    public Info() {
        em = PersistenceProvider.createEntityManager();
    }

    // TODO check login and password for each method
    /**
     * Return the number of unread events
     *
     * @param login
     * @param password
     * @return
     */
    @WebMethod
    public Integer getNewEventsCount(
            @WebParam(name = LOGIN_PARAMETER_NAME) final String login,
            @WebParam(name = PASSWORD_PARAMETER_NAME) final String password) {
    	
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
    @WebMethod
    public Integer getNewAnnouncementCount(
            @WebParam(name = LOGIN_PARAMETER_NAME) final String login,
            @WebParam(name = PASSWORD_PARAMETER_NAME) final String password) {
    	
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
    @WebMethod
    public Integer getNewConsultationCount(
            @WebParam(name = LOGIN_PARAMETER_NAME) final String login,
            @WebParam(name = PASSWORD_PARAMETER_NAME) final String password) {
    	
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
    @WebMethod
    public Integer getNewDemandeCount(
            @WebParam(name = LOGIN_PARAMETER_NAME) final String login,
            @WebParam(name = PASSWORD_PARAMETER_NAME) final String password) {
        SocialEntityFacade sef = new SocialEntityFacade(em);
        Query ql1 = null;
        if (sef.isMember(login, password)) {
            SocialEntity se = sef.findByEmail(login);
            ql1 = em.createQuery("SELECT s.asked FROM SocialEntity s  where s.id=:member ");
            ql1.setParameter("member", se.getId());
        }
        if (ql1 != null && ql1.getResultList()!=null){
        	return ql1.getResultList().size();
        }
        return 0;
    }

    /**
     * Return the number of unread personal messages
     *
     * @param login
     * @param password
     * @return
     */
    @WebMethod
    public List<WsPrivateMessage> getNewMessages(
            @WebParam(name = LOGIN_PARAMETER_NAME) final String login,
            @WebParam(name = PASSWORD_PARAMETER_NAME) final String password) {
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
    @WebMethod
    public boolean isMember(@WebParam(name = LOGIN_PARAMETER_NAME) final String login,
            @WebParam(name = PASSWORD_PARAMETER_NAME) final String password) {
        SocialEntityFacade sef = new SocialEntityFacade(em);
        return sef.isMember(login, password);
    }
}
