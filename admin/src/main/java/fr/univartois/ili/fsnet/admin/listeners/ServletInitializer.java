package fr.univartois.ili.fsnet.admin.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.SocialElement;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.SocialGroupFacade;

@WebListener
public class ServletInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		Logger.getAnonymousLogger().log(Level.INFO, "Admin webapp shutdown!");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		try {
			EntityManager em = PersistenceProvider.createEntityManager();
			String groupName = "Default group";
			String lastName = "admin";
			String firstName = "admin";
			String mail = "admin@fsnet.com";
			SocialEntity ent = new SocialEntity(lastName, firstName, mail);
			// TODO change this password or let the admin do it on the web
			// interface ?
			ent.setPassword(Encryption.getEncodedPassword("admin"));
			em.getTransaction().begin();
			em.persist(ent);
			em.getTransaction().commit();
			// Admin OK the group is remaining
			SocialGroup sg = createSocialGroup(ent, groupName);
			em.getTransaction().begin();
			em.persist(sg);
			em.getTransaction().commit();
			em.close();
		} catch (RollbackException e) {
			Logger.getAnonymousLogger().log(Level.WARNING, "", "The statement was aborted because it would have caused a duplicate key value");
		}
	}

	private SocialGroup createSocialGroup(SocialEntity ent, String groupName) {
		SocialGroup res = new SocialGroup(ent, groupName, "Default group for user without group");
		res.setColor("#000000");
		res.setMasterGroup(ent);
		return res;
	}
}
