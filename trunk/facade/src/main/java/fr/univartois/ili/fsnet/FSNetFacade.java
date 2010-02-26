package fr.univartois.ili.fsnet;

import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 *
 * @author The Facade Team { Helios }
 */
public interface FSNetFacade {

    SocialEntity createSocialEntity(String name, String firstName, String email);

}
