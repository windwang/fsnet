/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.FSNetFacade;

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
    public SocialEntity createSocialEntity(String name, String firstName, String email) {
        SocialEntityFacade sef = new SocialEntityFacade(em);
        return sef.createSocialEntity(name, firstName, email);
    }
}
