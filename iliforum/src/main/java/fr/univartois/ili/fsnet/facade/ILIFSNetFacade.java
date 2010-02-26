/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univartois.ili.fsnet.facade;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.FSNetFacade;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 *
 * @author The Facade Team { Helios }
 */
public class ILIFSNetFacade implements FSNetFacade {

    private final EntityManager em;

    public ILIFSNetFacade(EntityManager em) {
        this.em = em;
    }

    @Override
    public final SocialEntity createSocialEntity(String name, String firstName, String email) {
        SocialEntityFacade sef = new SocialEntityFacade(em);
        return sef.createSocialEntity(name, firstName, email);
    }
}
