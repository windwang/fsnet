package fr.univartois.ili.fsnet.core;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Right;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

/**
 * This servlet is instantiated by the container at the load of the application.
 * It's used to put the LoggedUsersContainer singleton in application scope as
 * an attribute
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */

@WebListener
public class InitializerServlet implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * Put the LoggedUsersContainer singleton in application scope as an
	 * attribute
	 */
	@Override
	public void contextInitialized(ServletContextEvent s) {

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			String groupName = "Default group";
			String lastName = "admin";
			String firstName = "admin";
			String mail = "admin@fsnet.com";
			SocialEntity ent = new SocialEntity(lastName, firstName, mail);
			SocialGroup sg = new SocialGroup(ent, groupName,
					"Default group for user without group");
			em.getTransaction().begin();
			em.persist(ent);
			em.persist(sg);
			em.getTransaction().commit();
			em.close();
		} catch (RollbackException e) {
			Logger.getAnonymousLogger()
					.log(Level.WARNING, "",
							"The statement was aborted because it would have caused a duplicate key value");
		} 
		s.getServletContext().setAttribute("loggedUsers",
				LoggedUsersContainer.getInstance());
	}
}
