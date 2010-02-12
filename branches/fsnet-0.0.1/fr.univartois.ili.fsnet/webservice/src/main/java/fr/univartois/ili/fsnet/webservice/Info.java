package fr.univartois.ili.fsnet.webservice;

import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * This webservice is used by fsnet heavy clients to get informations
 * on new events, announces, mesages etc...
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
@WebService()
public class Info {

    private EntityManagerFactory factory;
    private EntityManager em;
    private Date dt;

    public Info() {
        factory = Persistence.createEntityManagerFactory("fsnetjpa");
        em = factory.createEntityManager();
        this.dt = new Date();
    }

    //TODO check login and password for each method
    /**
     * Return the number of unread events
     * @param login
     * @param password
     * @return
     */
    @WebMethod(operationName = "getNewEventsCount")
    public Integer getNewEventsCount(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
        Query ql = em.createQuery("SELECT m FROM Meeting m where m.creationDate=?1 ");
        ql.setParameter(1, dt);
        return ql.getResultList().size();
    }

    /**
     * Return the number of unread announcement
     * @param login
     * @param password
     * @return
     */
    @WebMethod(operationName = "getNewAnnouncementCount")
    public Integer getNewAnnouncementCount(
            @WebParam(name = "login") final String login,
            @WebParam(name = "password") final String password) {
        Query ql1 = em.createQuery("SELECT m FROM Announcement m where type(m) in (Announcement) and m.creationDate=?1 ");
        ql1.setParameter(1, dt);
        return ql1.getResultList().size();
    }

    /**
     * Return the number of unread personal messages
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
                    "SELECT message FROM PrivateMessage message where message.reed='false' and message.to=:member",
                    PrivateMessage.class);
            messageQuery.setParameter("member", se);
            for (PrivateMessage message : messageQuery.getResultList()) {
                messages.add(new WsPrivateMessage(message));
            }
        }
        return messages;
    }
}
