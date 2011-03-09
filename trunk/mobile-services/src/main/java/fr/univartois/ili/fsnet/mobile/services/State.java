package fr.univartois.ili.fsnet.mobile.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

@Path("/state")
public class State {

	private EntityManagerFactory factory;
	private EntityManager em;

	public State() {
		factory = Persistence.createEntityManagerFactory("fsnetjpa");
		em = factory.createEntityManager();
	}

	/**
	 * Return the unread personal messages
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	@GET
	@Path("/isuser")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean isUser( //
			@QueryParam("login") String login, //
			@QueryParam("pwd") String password) {
		SocialEntityFacade sef = new SocialEntityFacade(em);
		return sef.isMember(login, password);
	}
}
