package fr.univartois.ili.fsnet.admin.tags;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.univartois.ili.fsnet.admin.tags.utils.AbstractSingleLoopTag;

public class ActivityReportTag extends AbstractSingleLoopTag {

    private static final long serialVersionUID = 1L;
    private static final String DATABASE_NAME = "fsnetjpa";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DATABASE_NAME);
    private EntityManager em;
    private Date date = new Date();

    @Override
    public void retrieveInfos(Map<String, Object> infos) {
        em = emf.createEntityManager();
        infos.put("nbInscrit", nombreInscrit());
        infos.put("nbAnnoncesTot", nombreAnnonces());
        infos.put("nbHubTot", nombreTotalHub());
        infos.put("nbAnnoncesPub", nombreAnnoncesValides());
        infos.put("nbEvenementTot", nombreEvenements());
        infos.put("nbEvenementPub", nombreEvenementValides());
        if (em != null) {
            em.close();
        }
    }

    private Object nombreInscrit() {
        Query query = em.createQuery("SELECT count(en) FROM EntiteSociale en");
        return query.getSingleResult();
    }

    private Object nombreAnnonces() {
        Query query = em.createQuery("SELECT count(a) FROM Annonce a");
        return query.getSingleResult();
    }

    private Object nombreAnnoncesValides() {
        Query query = em.createQuery("SELECT count(a) FROM Annonce a WHERE a.dateFinAnnonce>?1");
        query.setParameter(1, date);
        return query.getSingleResult();
    }

    private Object nombreEvenements() {
        Query query = em.createQuery("SELECT count(e) FROM Manifestation e");
        return query.getSingleResult();
    }

    private Object nombreEvenementValides() {
        Query query = em.createQuery("SELECT count(e) FROM Manifestation e WHERE e.dateManifestation>?1");
        query.setParameter(1, date);
        return query.getSingleResult();
    }

    private Object nombreTotalHub() {
        Query query = em.createQuery("SELECT count(h) FROM Hub h");
        return query.getSingleResult();
    }
}
