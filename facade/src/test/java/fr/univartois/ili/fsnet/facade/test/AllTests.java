package fr.univartois.ili.fsnet.facade.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 * @author SAID Mohamed <simo.said09 at gmail.com>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AnnouncementFacadeTest.class,
    CommunityFacadeTest.class,
    ContactFacadeTest.class,
    HubFacadeTest.class,
    InteractionFacadeTest.class,
    InterestFacadeTest.class,
    MeetingFacadeTest.class,
    PrivateMessageFacadeTest.class,
    ProfileFacadeTest.class,
    ProfileVisiteFacadeTest.class,
    SocialEntityFacadeTest.class,
    SocialGroupFacadeTest.class,
    TopicMessageFacadeTest.class,
    TopicFacadeTest.class,
    CvFacadeTest.class
})
public class AllTests {}
