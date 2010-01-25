package fr.univartois.ili.fsnet.entities.test;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Community;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.test.utils.TestEntityManagerProvider;

public class CommunityTest {

    private EntityManager em;

    @Before
    public void setUp() {
        em = TestEntityManagerProvider.getInstance().getEntityManager();
    }

    @After
    public void tearDown() {
    }

    // TODO TEST-LOL galileo
    @Test
    public void testPersist() {
        Community communaute = new Community(new SocialEntity("test5", "test5", "test5@test.com"),"Ma communaute");
        em.getTransaction().begin();
        em.persist(communaute);
        em.getTransaction().commit();
        int monId = communaute.getId();
        assertNotNull("id not null", monId);
    }
}
