package fr.univartois.ili.fsnet.filter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import fr.univartois.ili.fsnet.entities.Consultation;
import fr.univartois.ili.fsnet.entities.Consultation.TypeConsultation;
import fr.univartois.ili.fsnet.entities.Interaction;
import fr.univartois.ili.fsnet.entities.InteractionGroups;
import fr.univartois.ili.fsnet.entities.SocialEntity;
import fr.univartois.ili.fsnet.entities.SocialGroup;
import fr.univartois.ili.fsnet.filter.FilterInteractionByUserGroup;

public class FilterInteractionByUserGroupTest {
	private EntityManager em;
	private SocialEntity mouloud;
	private SocialEntity alan;
	private SocialEntity anicet;
	private SocialGroup group;
	private SocialGroup group2;
	private Consultation consultation;
	private Consultation consultation2;
	
	@Before
	public void setUp() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("TestPU");
		em = emf.createEntityManager();	
		
		mouloud = new SocialEntity("Goutal", "Mouloud", "ouai@aaaa.fr");
		alan = new SocialEntity("Delahaye", "Alan", "casser@ledos.fr");
		anicet = new SocialEntity("Bart", "Anicet", "ouai@ouai.fr");
		group = new SocialGroup(alan, "group", "description");
		group2 = new SocialGroup(mouloud, "group2", "description");	
		consultation = new Consultation(mouloud, "titre", "description", TypeConsultation.YES_NO , null);
		consultation2 = new Consultation(mouloud, "titre2", "description2", TypeConsultation.YES_NO , null);
	}
	
	@Test
	public void fitlerAnInteractionTest() {		
		group.setId(1);
		group2.setId(2);
		anicet.setGroup(group);		
		
		FilterInteractionByUserGroup filterGroup = new FilterInteractionByUserGroup(em);
		
		assertEquals(filterGroup.filterAnInteraction(mouloud, consultation),consultation);
		assertNull(filterGroup.filterAnInteraction(alan, consultation));
		filterGroup.filterAnInteraction(anicet, consultation);
		
		Interaction inte = consultation;
		Interaction inte2 = consultation2;		
		InteractionGroups ig = new InteractionGroups(inte, group2);
		InteractionGroups ig2 = new InteractionGroups(inte2, group2);
		InteractionGroups ig3 = new InteractionGroups(inte, group);
		
		inte.getInteractionGroups().add(ig);
		inte.getInteractionGroups().add(ig2);
		
		assertNull(filterGroup.filterAnInteraction(anicet, consultation));
		inte.getInteractionGroups().add(ig3);
		assertEquals(inte,filterGroup.filterAnInteraction(anicet, consultation));			
	}

}
