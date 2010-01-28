package fr.univartois.ili.fsnet.facade.forum.iliforum;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.SocialEntity;

/**
 *
 * @author Aurelien Legrand
 */
public class ContactFacade {

    EntityManager em;

    public ContactFacade(EntityManager em) {
        this.em = em;

    }

    /**
     * Ask a SocialEntity to be your friend
     *
     * @param asker
     * @param asked
     */
    public void askContact(SocialEntity asker, SocialEntity asked) {
        if (asker.equals(asked)) {
            throw new IllegalArgumentException();
        }
        if (!(asker.getAsked().contains(asked)
                || asker.getRequested().contains(asked)
                || asker.getContacts().contains(asked)
                || asked.getAsked().contains(asker)
                || asked.getRequested().contains(asker)
                || asked.getContacts().contains(asker))) {
            asker.getRequested().add(asked);
            asked.getAsked().add(asker);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Refuse a contact after having received a demand
     *
     * @param currentUser
     * @param refused
     */
    public void refuseContact(SocialEntity member, SocialEntity refused) {
        if (member == null || refused == null) {
            throw new IllegalArgumentException();
        }
        if (member.getContacts().contains(refused)
                || member.getRefused().contains(refused)
                || !member.getAsked().contains(refused)
                || member.getRequested().contains(refused)
                || !refused.getRequested().contains(member)
                || refused.getContacts().contains(member)
                || refused.getRefused().contains(member)
                || refused.getAsked().contains(member)) {
            throw new IllegalStateException();
        }

        member.getAsked().remove(refused);
        refused.getRequested().remove(member);
        member.getRefused().add(refused);
        em.merge(member);
        em.merge(refused);
    }

    /**
     * Accept a contact after having received a demand
     *
     * @param member
     * @param accepted
     */
    public void acceptContact(SocialEntity member, SocialEntity accepted) {
        if (member == null || accepted == null) {
            throw new IllegalArgumentException();
        }
        if (member.getAsked().contains(accepted)
                && accepted.getRequested().contains(member)
                && !member.getContacts().contains(accepted)
                && !accepted.getContacts().contains(member)) {
            member.getAsked().remove(accepted);
            accepted.getRequested().remove(member);
            member.getContacts().add(accepted);
            em.merge(member);
            em.merge(accepted);
        } else {
            throw new IllegalStateException();
        }

    }

    /**
     * Remove a contact to your contact list
     *
     * @param member
     * @param removedEntity
     */
    public void removeContact(SocialEntity member, SocialEntity removedEntity) {
        if (member == null || removedEntity == null) {
            throw new IllegalArgumentException();
        }
        member.getContacts().remove(removedEntity);
        removedEntity.getContacts().remove(member);

    }

    /**
     * Delete a contact on the DataBase
     *
     * @param deletedEntity
     */
    public void deleteContact(int id) {

        SocialEntity deletedEntity = em.find(SocialEntity.class, id);
        if (deletedEntity == null) {
            return;
        }
        em.remove(deletedEntity);
    }
}
