package fr.univartois.ili.fsnet.entities.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author romuald druelle
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AnnouncementTest.class, 
	CommunityTest.class, 
    HubTest.class,
    InterestTest.class, 
    MeetingTest.class,
    ProfileVisitTest.class,
    SocialEntityTest.class,
    TopicTest.class
})
public class AllTests {}
