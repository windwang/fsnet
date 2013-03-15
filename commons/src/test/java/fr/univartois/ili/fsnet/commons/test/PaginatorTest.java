package fr.univartois.ili.fsnet.commons.test;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.univartois.ili.fsnet.commons.pagination.Paginator;
import fr.univartois.ili.fsnet.commons.test.utils.MockHTTPServletRequest;
import fr.univartois.ili.fsnet.entities.SocialEntity;

public class PaginatorTest {

	private static EntityManager em;

	private static int numRecords;

	private static final String FIND_ALL = "SELECT se FROM SocialEntity se ORDER BY se.name";

	@BeforeClass
	public static void setUp() throws Exception {
		EntityManagerFactory fact = Persistence
				.createEntityManagerFactory("TestPU");
		em = fact.createEntityManager();
		numRecords = 200;
		em.getTransaction().begin();

		for (int i = 0; i < numRecords; i++) {
			SocialEntity se = new SocialEntity("name" + i, "firstName" + i,
					"email" + i);
			em.persist(se);
		}

		em.getTransaction().commit();
	}
	
	/*
	 * 
	 */
	@Test
	public void testGetNumResultsPerPage(){
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "1");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numRecords, "myId");
		assertEquals(200, paginator.getNumResultsPerPage());
	}
	
	@Test
	public void testGetRequestedPage(){
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "1");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numRecords, "myId");
		assertTrue( paginator.getRequestedPage()>0);
	}
	
	@Test
	public void testGetNumPages(){
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "1");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numRecords, "myId");
		assertTrue( paginator.getNumPages()>0);
	}

	/**
	 * Simply test the number of results using the
	 * Paginator.DEFAULT_NUM_RESULT_PER_PAGE behaviour
	 */
	@Test
	public void testPaginatorNumResults() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, "myId");
		assertEquals(Paginator.DEFAULT_NUM_RESULT_PER_PAGE, paginator
				.getResultList().size());
	}
	
	/**
	 * test rename RequestInput
	 */
	@Test
	public void testPaginatorRequestInputName() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, "myId","requestInputName");
		assertEquals("requestInputName", paginator.getRequestInputName());
	}

	

	
	
	/**
	 * Test the number of results when the last page has not the same number of
	 * results than previous
	 */
	@Test
	public void testPaginatorNumResultsOnLastPage() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "1");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numRecords - 10, "myId");
		assertEquals(10, paginator.getResultList().size());
	}

	/**
	 * Test the number of results when the number of results per page has been
	 * set programmatically by the developer
	 */
	@Test
	public void testPaginatorCustomNumResulstsFromDevelopers() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		int numResultPerPage = 17;
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numResultPerPage, "myId");
		assertEquals(numResultPerPage, paginator.getResultList().size());
	}

	/**
	 * test the number of results when the number of results per page has been
	 * set by a parameter in the request
	 */
	@Test
	public void testPaginatorCustomNumResulstsFromUser() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		int numResultPerPage = 17;
		request.addParameter(Paginator.NUM_RESULT_PER_PAGES, ""
				+ numResultPerPage);
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, "myId");
		assertEquals(numResultPerPage, paginator.getResultList().size());
	}

	/**
	 * Verify the behaviour when the requested page from the HTTPRequest is set
	 * over the number of pages
	 */
	@Test
	public void testPaginatorOnPageRequestOverNumOfResults() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "50");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, "myId");
		assertEquals(0, paginator.getResultList().size());
	}

	/**
	 * When the user pass a bad parameter value for the request page the
	 * paginator must throw an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testPaginatorBadPageRequestFromRequest() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "-1");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		new Paginator<SocialEntity>(query.getResultList(), request, "myId");
	}

	/**
	 * When an user set the number of results per page, the paginator must use
	 * the default policy
	 */
	@Test
	public void testPaginatorBadNumResultsPerPageFromRequest() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		request.addParameter(Paginator.NUM_RESULT_PER_PAGES, "-1");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, "myId");
		assertEquals(Paginator.DEFAULT_NUM_RESULT_PER_PAGE, paginator
				.getResultList().size());
	}

	/**
	 * If the developer set the number of results programmatically, the user
	 * can't change this setting by passing a parameter to the request
	 */
	@Test
	public void testNumResultPerPagePriority() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		int numResultPerPageFromUser = 17;
		int numResultPerPageFromDeveloper = 19;
		request.addParameter(Paginator.NUM_RESULT_PER_PAGES, ""
				+ numResultPerPageFromUser);
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numResultPerPageFromDeveloper, "myId");
		assertEquals(numResultPerPageFromDeveloper, paginator.getResultList()
				.size());
	}

	/**
	 * Test if the first result on the first page is the attempted result
	 */
	@Test
	public void testFirstResult() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		int numResultPerPageFromDeveloper = 16;
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numResultPerPageFromDeveloper, "myId");
		assertEquals("name0", paginator.getResultList().get(0).getName());
	}

	/**
	 * Test if the returned results admit a next page
	 */
	@Test
	public void testHasNextPage() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		request.addParameter(Paginator.PAGE_ID, "0");
		int numResultPerPageFromDeveloper = 16;
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numResultPerPageFromDeveloper, "myId");
		assertTrue(paginator.getHasNextPage());
	}

	/**
	 * Verify that the last page not admit a next page from the API
	 */
	@Test
	public void testHasNoNextPage() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		int numResultPerPage = 10;
		request.addParameter(Paginator.PAGE_ID, ""
				+ (numRecords / numResultPerPage));
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numResultPerPage, "myId");
		assertFalse(paginator.getHasNextPage());
	}

	/**
	 * Test if a N with N > 0 admit a previous page
	 */
	@Test
	public void testHasPreviousPage() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		int numResultPerPage = 8;
		request.addParameter(Paginator.PAGE_ID, "1");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numResultPerPage, "myId");
		assertTrue(paginator.getHasPreviousPage());
	}

	/**
	 * Test if a 0th page not admit a previous page
	 */
	@Test
	public void testHasNoPreviousPage() {
		MockHTTPServletRequest request = new MockHTTPServletRequest();
		int numResultPerPage = 8;
		request.addParameter(Paginator.PAGE_ID, "0");
		TypedQuery<SocialEntity> query = em.createQuery(FIND_ALL,
				SocialEntity.class);
		Paginator<SocialEntity> paginator = new Paginator<SocialEntity>(query
				.getResultList(), request, numResultPerPage, "myId");
		assertFalse(paginator.getHasPreviousPage());
	}
}
