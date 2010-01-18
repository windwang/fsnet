package fr.univartois.ili.fsnet.admin.tags;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.univartois.ili.fsnet.admin.tags.utils.AbstractGenericIteratorTag;
import fr.univartois.ili.fsnet.entities.Hub;

public class HubTag extends AbstractGenericIteratorTag<Hub> {

    private static final long serialVersionUID = 1L;
    private static final String DATABASE_NAME = "fsnetjpa";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
    private static final String FIND_ALL = "SELECT h FROM Hub h ORDER BY h.nomCommunaute";

    @Override
    public String getDefaultVarName() {
        return "hub";
    }

    @Override
    protected Iterator<Hub> initIterator() {
        EntityManager em = factory.createEntityManager();
        Query query;
        List<Hub> lesHubs;
        query = em.createQuery(FIND_ALL);
        lesHubs = query.getResultList();
        em.close();
        return lesHubs.iterator();
    }
}
