package fr.univartois.ili.fsnet.facade.forum.iliforum;

import fr.univartois.ili.fsnet.entities.PrivateMessage;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import javax.persistence.EntityManager;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class PrivateMessageFacade {

    private final EntityManager em;

    public PrivateMessageFacade(EntityManager em) {
        this.em = em;
    }

    public final PrivateMessage sendPrivateMessage(String body, SocialEntity from, String subject, SocialEntity to) {
        PrivateMessage pm = new PrivateMessage(body, from, subject, to);
        em.persist(pm);
        from.getSentPrivateMessages().add(pm);
        to.getReceivedPrivateMessages().add(pm);
        return pm;
    }

    public final void deletePrivateMessage(SocialEntity entity, PrivateMessage message) {
        if (entity == null || message == null) {
            throw new IllegalArgumentException();
        }
        // TODO implement equals or error when detached
        if (message.getFrom().equals(entity)) {
            entity.getSentPrivateMessages().remove(message);
        }
        if (message.getTo().equals(entity)) {
            entity.getReceivedPrivateMessages().remove(message);
        }
    }
}
