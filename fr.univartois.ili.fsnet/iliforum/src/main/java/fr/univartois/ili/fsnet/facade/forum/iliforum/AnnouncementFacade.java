package fr.univartois.ili.fsnet.facade.forum.iliforum;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.entities.Announcement;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class AnnouncementFacade {

    /**
     * @author mickael watrelot - micgamers@gmail.com
     */
    private final EntityManager em;

    public AnnouncementFacade(EntityManager em) {
        this.em = em;
    }

    public Announcement createAnnouncement(SocialEntity member, String annName,
            String annDescription, Date endDate, Boolean isPrivate) {
        Announcement announce = new Announcement(member, annName,
                annDescription, endDate, isPrivate);

        em.persist(announce);

        return announce;
    }

    public Announcement modifyAnnouncement(int idAnnounce, String annName,
            String annDescription, Date endDate) {
        Announcement announce = em.find(Announcement.class, idAnnounce);
        announce.setTitle(annName);
        announce.setContent(annDescription);
        announce.setEndDate(endDate);
        em.merge(announce);
        return announce;
    }

    public void deleteAnnouncement(int idAnnounce) {
        Announcement announce = em.find(Announcement.class, idAnnounce);
        em.remove(announce);
        em.flush();
    }

    public List<Announcement> searchAnnouncement(String textSearchAnnounce) {
        List<Announcement> listAnnounces;
        List resultList = em.createQuery(
                "SELECT a FROM Announcement a WHERE  TYPE(a) IN(Announcement) AND ( a.title LIKE :textSearchAnnounce OR a.content LIKE :textSearchAnnounce) ").setParameter("textSearchAnnounce",
                "%" + textSearchAnnounce + "%").getResultList();
        listAnnounces = resultList;

        return listAnnounces;

    }
}
