package fr.univartois.ili.fsnet.facade.forum.iliforum;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade;
import fr.univartois.ili.fsnet.facade.forum.iliforum.SocialEntityFacade.SearchResult;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SocialEntityFacadeTest {

    private EntityManager em;
    private SocialEntityFacade sef;

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
        sef = new SocialEntityFacade(em);
    }

    @Test
    public void testCreate() {
//        String name = "name";
//        String firstName = "first";
//        String email = "email@email.com";
//        SocialEntity es = sef.createSocialEntity(name, firstName, email);
//        SocialEntity esp = em.find(SocialEntity.class, es.getId());
//        assertEquals(esp.getName(), es.getName());
//        assertEquals(esp.getFirstName(), es.getFirstName());
//        assertEquals(esp.getEmail(), es.getEmail());
    }

    @Test
    public void testSearch() {
//        SocialEntity ent1 = new SocialEntity("zaza", "zaza", "zaza@gmail.com");
//        SocialEntity ent2 = new SocialEntity("zozo", "zozo", "zaza@gmail.com");
//        SocialEntity ent3 = new SocialEntity("zozi", "zozi", "zizi@gmail.com");
//        String searchText = "zo";
//        HashMap<SearchResult, List<SocialEntity>> result = sef.searchSocialEntity(searchText, ent1.getId());
//        List<SocialEntity> l = result.get(SearchResult.Contacts);
//        SocialEntity ent21 = l.get(0);
//        assertEquals(ent2.getName(), ent21.getName());
//        assertEquals(ent2.getFirstName(), ent21.getFirstName());
//        assertEquals(ent2.getEmail(), ent21.getEmail());
    }
}
