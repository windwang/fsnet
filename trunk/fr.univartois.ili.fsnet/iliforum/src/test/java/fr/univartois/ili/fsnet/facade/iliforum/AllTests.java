package fr.univartois.ili.fsnet.facade.iliforum;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({fr.univartois.ili.fsnet.facade.iliforum.SocialEntityFacadeTest.class, fr.univartois.ili.fsnet.facade.iliforum.ContactTest.class})
public class AllTests {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}
