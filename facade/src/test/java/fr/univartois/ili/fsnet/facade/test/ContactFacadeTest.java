package fr.univartois.ili.fsnet.facade.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.facade.ContactFacade;
import fr.univartois.ili.fsnet.facade.SocialEntityFacade;

/**
 *
 * @author Aurelien Legrand
 */
public class ContactFacadeTest {

    private EntityManager em;

    @Before
    public void setUp() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
        em = entityManagerFactory.createEntityManager();

    }

    
    /**
     * try to accept a SocialEntity without having received a demand
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void acceptWithoutAskingTest() {
        ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity se1 = sef.createSocialEntity("toto", "titi", "toto@gmail.com");
        SocialEntity se2 = sef.createSocialEntity("tata", "titi", "tata@gmail.com");
        em.getTransaction().begin();
        em.persist(se1);
        em.persist(se2);
        em.getTransaction().commit();
        cf.acceptContact(se1, se2);
    }

    /**
     * try to ask someone you have already asked
     * @return an IllegalStateException 
     */
    @Test(expected = IllegalStateException.class)
    public void alreadyAskedTest() {
        ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity se1 = sef.createSocialEntity("toto", "titi", "titi@gmail.com");
        SocialEntity se2 = sef.createSocialEntity("tata", "titi", "taytaygmail.com");
        em.getTransaction().begin();
        em.persist(se1);
        em.persist(se2);
        em.getTransaction().commit();
        cf.askContact(se1, se2);
        cf.askContact(se2, se1);
    }

    /**
     * try to ask himself as new contact
     * @return an IllegalStateException
     */
    @Test(expected = IllegalArgumentException.class)
    public void askHimselfTest() {
        em.getTransaction().begin();
        ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity se1 = sef.createSocialEntity("toto", "titi", "teitei@gmail.com");
        em.persist(se1);
        cf.askContact(se1, se1);
        em.getTransaction().commit();

    }

    /**
     * try a contact remove
     */
    @Test
    public void removeContactTest() {
        em.getTransaction().begin();
        ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity se1 = sef.createSocialEntity("toto", "titi", "toto1@gmail.com");
        SocialEntity se2 = sef.createSocialEntity("tata", "titi", "tata2gmail.com");
        em.persist(se1);
        em.persist(se2);
        cf.askContact(se1, se2);
        cf.acceptContact(se2, se1);
        cf.removeContact(se1, se2);
        em.getTransaction().commit();
        assertTrue(!se1.getContacts().contains(se2));
    }

    
    /**
     * try to ask a SocialEntity who is already in your contact list
     *  @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void alreadyInContactTest() {
        em.getTransaction().begin();
        ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity se1 = sef.createSocialEntity("toitoi", "titi", "toitoi@gmail.com");
        SocialEntity se2 = sef.createSocialEntity("taitai", "titi", "taitaigmail.com");
        cf.askContact(se1, se2);
        cf.acceptContact(se2, se1);
        cf.askContact(se1, se2);
        em.getTransaction().commit();
    }

    /**
     * try to refuse without having received a demand someone who is already
     * in your contact list
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void refuseSocialEntityAlreadyInContact() {
        em.getTransaction().begin();
        ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity se1 = sef.createSocialEntity("bobo", "bibi", "bobo@gmail.com");
        SocialEntity se2 = sef.createSocialEntity("baba", "bibi", "babagmail.com");
        cf.askContact(se1, se2);
        cf.acceptContact(se2, se1);
        cf.refuseContact(se1, se2);
    }
    
    /**
     * ask a contact with a null SocialEntity asker
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAskContactWithANullAsker() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity asker = null;
        SocialEntity asked = sef.createSocialEntity("asked1", "asked1", "ad1@amail.com");
        cf.askContact(asker, asked);
        fail("Must throw an IllegalArgumentException");        
    } 
    
    /**
     * ask a contact with an null SocialEntity asked and asker not null
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAskContactWithANullAsked() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity asker = sef.createSocialEntity("asker2", "asker2", "ar2@amail.com");
        SocialEntity asked = null;
        cf.askContact(asker, asked);
        fail("Must throw an IllegalArgumentException");        
    } 
  
    /**
     * ask a contact having the member in the requests
     */
    @Test
    public void testAskContactWhenAskerhasARequestFromAsked() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity asker = sef.createSocialEntity("asker3", "asker3", "ar3@amail.com");
        SocialEntity asked = sef.createSocialEntity("asked3", "asked3", "ad3@amail.com");
        asker.getRequested().add(asked);
        cf.askContact(asker, asked);      
    } 
    
    /**
     * ask a contact when the contact has already asked
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testAskContactWhenAskedHasAskedBefore() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity asker = sef.createSocialEntity("asker4", "asker4", "ar4@amail.com");
        SocialEntity asked = sef.createSocialEntity("asked4", "asked4", "ad4@amail.com");       
        asker.getAsked().add(asked);
        asked.getAsked().add(asker);
        cf.askContact(asker, asked);    
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * refuse a contact with a null member
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRefuseContactWhithNullMember() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = null;
        SocialEntity refused = sef.createSocialEntity("refused5", "refused5", "ref5@amail.com");       
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalArgumentException");  
    } 
    
    /**
     * refuse a contact with a null refused
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRefuseContactWhithNullRefused() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member6", "member6", "mem6@amail.com");  
        SocialEntity refused = null;     
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalArgumentException");  
    } 
    
    /**
     * refuse a contact when member has already refused
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testRefuseContactWhenContactOfMemberContainRefused() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member7", "member7", "mem7@amail.com");  
        SocialEntity refused = sef.createSocialEntity("refused7", "refused7", "ref7@amail.com");      
        member.getRefused().add(refused);
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * refuse a contact when member didn't ask before
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testRefuseContactWhenMemberDidNotAsk() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member8", "member8", "mem8@amail.com");  
        SocialEntity refused = sef.createSocialEntity("refused8", "refused8", "ref8@amail.com");      
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * refuse a contact when member has a request from refused
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testRefuseContactWhenMemberHasRequestFromRefused() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member9", "member9", "mem9@amail.com");  
        SocialEntity refused = sef.createSocialEntity("refused9", "refused9", "ref9@amail.com");      
        member.getAsked().add(refused);
        member.getRequested().add(refused);
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * refuse a contact when refused hasn't a request from member
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testRefuseContactWhenRefusedHasNotRequestFromMember() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member10", "member10", "mem10@amail.com");  
        SocialEntity refused = sef.createSocialEntity("refused10", "refused10", "ref10@amail.com");      
        member.getAsked().add(refused);
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * refuse a contact when refused has member in his contacts
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testRefuseContactWhenRefusedHasMemberInContacts() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member11", "member11", "mem11@amail.com");  
        SocialEntity refused = sef.createSocialEntity("refused11", "refused11", "ref11@amail.com");      
        member.getAsked().add(refused);
        refused.getRequested().add(member);
        refused.getContacts().add(member);
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * refuse a contact when asked Member
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testRefuseContactWhenRefusedAskedMember() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member12", "member12", "mem12@amail.com");  
        SocialEntity refused = sef.createSocialEntity("refused12", "refused12", "ref12@amail.com");      
        member.getAsked().add(refused);
        refused.getRequested().add(member);
        refused.getAsked().add(member);
        cf.refuseContact(member, refused) ;
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * refuse a contact 
     */
    @Test
    public void testRefuseContact() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = sef.createSocialEntity("member21", "member21", "mem21@amail.com");  
        SocialEntity refused = sef.createSocialEntity("refused21", "refused21", "ref21@amail.com");      
        member.getAsked().add(refused);
        refused.getRequested().add(member);
        cf.refuseContact(member, refused) ;
        assertTrue(! member.getAsked().contains(refused));
        assertTrue(! refused.getRequested().contains(refused));
        assertTrue(member.getRefused().contains(refused));
    } 
    
    /**
     * accept a contact with a null member
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAcceptContactWhithNullMember() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = null;
        SocialEntity accepted = sef.createSocialEntity("accepted13", "accepted13", "acc13@amail.com");     
        cf.acceptContact(member, accepted);
        fail("Must throw an IllegalArgumentException");  
    } 
    
    /**
     * accept a contact with a null accepted
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAcceptContactWhithNullAccepted() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member =  sef.createSocialEntity("member20", "member20", "mem20@amail.com");  
        SocialEntity accepted = null;     
        cf.acceptContact(member, accepted);
        fail("Must throw an IllegalArgumentException");  
    } 
    
    /**
     * accept a contact when accepted has member in contacts
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testAcceptContactWhenAcceptedHasMemberInContacts() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member =  sef.createSocialEntity("member14", "member14", "mem14@amail.com");  
        SocialEntity accepted = sef.createSocialEntity("accepted14", "accepted14", "acc14@amail.com");            
        member.getAsked().add(accepted);
        accepted.getRequested().add(member);
        accepted.getContacts().add(member);
        cf.acceptContact(member, accepted);
        fail("Must throw an IllegalStateException");  
    } 

    /**
     * accept a contact when Member has Accepted in contacts
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testAcceptContactWhenMemberHasAcceptedInContacts() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member =  sef.createSocialEntity("member15", "member15", "mem15@amail.com");  
        SocialEntity accepted = sef.createSocialEntity("accepted15", "accepted15", "acc15@amail.com");            
        member.getAsked().add(accepted);
        accepted.getRequested().add(member);
        member.getContacts().add(accepted);
        cf.acceptContact(member, accepted);
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * accept a contact when Accepted hasn't a request from Member
     * @return an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testAcceptContactWhenAcceptedHasnotRequestFromMember() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member =  sef.createSocialEntity("member16", "member16", "mem16@amail.com");  
        SocialEntity accepted = sef.createSocialEntity("accepted16", "accepted16", "acc16@amail.com");            
        member.getAsked().add(accepted);

        cf.acceptContact(member, accepted);
        fail("Must throw an IllegalStateException");  
    } 
    
    /**
     * remove a contact with a null member
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveContactWhithNullMember() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member = null;
        SocialEntity removedEntity = sef.createSocialEntity("re17", "re17", "re17@amail.com");     
        cf.removeContact(member, removedEntity);
        fail("Must throw an IllegalArgumentException");  
    } 
    
    /**
     * remove a contact with a null removedEntity
     * @return an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveContactWhithNullRemovedEntity() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity member =  sef.createSocialEntity("member18", "member18", "mem18@amail.com");  
        SocialEntity removedEntity = null;     
        cf.removeContact(member, removedEntity);
        fail("Must throw an IllegalArgumentException");  
    } 

    /**
     * test CancelRequested
     */
    @Test
    public void testCancelRequested() {
    	ContactFacade cf = new ContactFacade(em);
        SocialEntityFacade sef = new SocialEntityFacade(em);
        SocialEntity user =  sef.createSocialEntity("user19", "user19", "user19@amail.com");  
        SocialEntity requested = sef.createSocialEntity("req19", "req19", "req19@amail.com");       
        user.getRequested().add(requested);
        requested.getAsked().add(user);
        assertTrue(user.getRequested().contains(requested));
        assertTrue(requested.getAsked().contains(user));
        cf.cancelRequested(user, requested, em); 
        assertFalse(user.getRequested().contains(requested));
        assertFalse(requested.getAsked().contains(user));
    }
}
