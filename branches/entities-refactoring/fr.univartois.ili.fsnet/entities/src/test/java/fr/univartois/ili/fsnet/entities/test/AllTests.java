package fr.univartois.ili.fsnet.entities.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author romuald druelle
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AnnouncementTest.class, CommunityTest.class,
    SocialEntityTest.class, HubTest.class,
    InterestTest.class, MeetingTest.class,
    ActivityReportTest.class, TopicTest.class})
public class AllTests {
}
