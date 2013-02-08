package fr.univartois.ili.fsnet.admin.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
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

		EntityManager em = PersistenceProvider.createEntityManager();
		SocialEntityFacade socialEntityFacade = new SocialEntityFacade(em);
		SocialGroupFacade groupFacade = new SocialGroupFacade(em);
		
		SocialEntity admin = socialEntityFacade.createSocialEntity("admin", "admin","admin@fsnet.com");
		admin.setPassword(Encryption.getEncodedPassword("admin"));
		em.getTransaction().begin();
		em.merge(admin);
		em.getTransaction().commit();
		List<SocialElement> members = new ArrayList<>();
		members.add(admin);
		
		SocialGroup socialG = groupFacade.createSocialGroup(admin, "Default group", "Default group for user without group",members);
		socialG.setMasterGroup(admin);
		em.getTransaction().begin();
		em.merge(socialG);
		em.getTransaction().commit();
	}

}
