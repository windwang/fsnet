package fr.univartois.ili.fsnet.mobile.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.mobile.services.model.AuthInfo;
import fr.univartois.ili.fsnet.mobile.services.model.RestPrivateMessage;
@Resource
@Path("/messages")
public class Messages {

	private EntityManagerFactory factory;
	private EntityManager em;

	public Messages() {
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		em = factory.createEntityManager();
	}

	/**
	 * Return the number of unread personal messages
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	@POST
	@Path("/unread")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public GenericEntity<List<RestPrivateMessage>> getNewMessages(AuthInfo authInfo) {
		Logger.getAnonymousLogger().info(authInfo.toString());
		List<RestPrivateMessage> messages = new ArrayList<RestPrivateMessage>();
		SocialEntityFacade sef = new SocialEntityFacade(em);
		if (sef.isMember(authInfo.getLogin(), authInfo.getPassword())) {
			SocialEntity se = sef.findByEmail(authInfo.getLogin());
			TypedQuery<PrivateMessage> messageQuery = em
					.createQuery(
							"SELECT message FROM SocialEntity soc, IN(soc.receivedPrivateMessages) message where message.reed='false' and soc=:member",
							PrivateMessage.class);
			messageQuery.setParameter("member", se);
			for (PrivateMessage message : messageQuery.getResultList()) {
				messages.add(new RestPrivateMessage(message));
			}
		}
		return new GenericEntity<List<RestPrivateMessage>>(messages){};
	}

}