package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.commons.security.Encryption;
import fr.univartois.ili.fsnet.entities.Address;
import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 * 
 * profile facade
 * @author geoffrey boulay
 *
 */


public class ProfileFacade {

    private EntityManager em;
    private static Logger loger = Logger.getAnonymousLogger();

    /**
     * create a facade profile
     * @param em entity manager to use
     */
    public ProfileFacade(EntityManager em) {
        this.em = em;
    }

    /**
     * change the password from a user
     * @param socialEntity user
     * @param oldPassword old password
     * @param newPassword new password
     * @return true if success
     */
    public final boolean changePassword(SocialEntity socialEntity, String oldPassword, String newPassword) {
    	if(socialEntity == null || oldPassword == null || newPassword == null){
    		throw new IllegalArgumentException();
    	}
    	String encryptOldPassword = Encryption.getEncodedPassword(oldPassword);
    	String encryptNewPassword = Encryption.getEncodedPassword(newPassword);
        if (!encryptOldPassword.equals(socialEntity.getPassword())) {
            loger.log(Level.SEVERE, "Formular validation error");
            return false;
        }
        socialEntity.setPassword(encryptNewPassword);
        em.merge(socialEntity);
        em.flush();
        return true;
    }

    /**
     * change an social Entity profile
     * @param socialEntity the social entity
     * @param name his name
     * @param firstName his firstnaem
     * @param address his adress
     * @param dateOfBirth his birth day
     * @param sexe his sex
     * @param job his job
     * @param mail his mail
     * @param phone his phone
     */
    public final void editProfile(SocialEntity socialEntity, String name, String firstName, Address address, Date dateOfBirth,
            String sexe, String job, String mail, String phone) {
        socialEntity.setName(name);
        socialEntity.setFisrtname(firstName);
        socialEntity.setAddress(address);
        socialEntity.setBirthDate(dateOfBirth);
        socialEntity.setSex(sexe);
        socialEntity.setProfession(job);
        socialEntity.setEmail(mail);
        socialEntity.setPhone(phone);
        em.merge(socialEntity);
        em.flush();
    }
}
