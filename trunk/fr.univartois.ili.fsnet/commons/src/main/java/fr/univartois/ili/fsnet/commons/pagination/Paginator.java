package fr.univartois.ili.fsnet.commons.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Paginator<T> {

	/**
	 * The name of the parameter in the request to give the requested page id
	 */
	public static final String PAGE_ID = "pageId";

	/**
	 * The name of the parameter in the request to set the number of results
	 */
	public static final String NUM_RESULT_PER_PAGES = "numResults";

	/**
	 * The default number of results if The user or developer has not set this
	 * parameter
	 */
	public static final int DEFAULT_NUM_RESULT_PER_PAGE = 15;

	/**
	 * The requested page id
	 */
	private int requestedPage = 0;

	/**
	 * The number of results per page (set to NUM_RESULT_PER_PAGES by default)
	 */
	private int numResultsPerPage;

	/**
	 * The number of pages in this query
	 */
	private int numPages;

	/**
	 * true if this query admit a next page
	 */
	private boolean hasNextPage;

	/**
	 * true if this query admit a previous page
	 */
	private boolean hasPreviousPage;

	/**
	 * All results from query
	 */
	private List<T> allResults;

	/**
	 * The splited results
	 */
	private List<T> results;

	/**
	 * This constructor allow developper to set the number of results, AND this
	 * parameter is higher priority to HTTP parameter
	 * 
	 * @param results
	 *            All results from a query from example
	 * @param requestThe
	 *            HTTPRequest that contains the proper parameters
	 * @param numResultsPerPage
	 *            the number of results per page
	 */
	public Paginator(List<T> results, HttpServletRequest request,
			int numResultsPerPage) {
		this.allResults = results;
		parseRequest(request);
		this.numResultsPerPage = numResultsPerPage;
		init();
	}

	/**
	 * Default constructor that allow user to set the number of result by
	 * including the proper parameter in his request
	 * 
	 * @param results
	 * @param request
	 * @throws IllegalArgumentException
	 */
	public Paginator(List<T> results, HttpServletRequest request)
			throws IllegalArgumentException {
		this.allResults = results;
		parseRequest(request);
		init();
	}

	/**
	 * Init the properties about this paginator
	 */
	private void init() {
		numPages = (int) Math.ceil(allResults.size()
				/ ((double) numResultsPerPage));
		hasPreviousPage = (requestedPage != 0);
		hasNextPage = (requestedPage < numPages);
		if (requestedPage < numPages) {
			int toIndex = requestedPage * numResultsPerPage + numResultsPerPage;
			if (toIndex > allResults.size()) {
				toIndex = allResults.size();
			}
			results = allResults.subList(requestedPage * numResultsPerPage,
					toIndex);
		} else {
			results = new ArrayList<T>();
		}
	}

	/*
	 * Parse the parameters from the request
	 * @param request
	 * @throws IllegalArgumentException
	 */
	private void parseRequest(HttpServletRequest request)
			throws IllegalArgumentException {
		if (request.getParameterMap().containsKey(PAGE_ID)) {
			requestedPage = parseParameter(PAGE_ID, request);
			if (requestedPage < 0) {
				throw new IllegalArgumentException();
			}
		} else {
			requestedPage = 0;
		}
		if (request.getParameterMap().containsKey(NUM_RESULT_PER_PAGES)) {
			numResultsPerPage = parseParameter(NUM_RESULT_PER_PAGES, request);
			if (numResultsPerPage < 0) {
				this.numResultsPerPage = DEFAULT_NUM_RESULT_PER_PAGE;
			}
		} else {
			this.numResultsPerPage = DEFAULT_NUM_RESULT_PER_PAGE;
		}
	}

	private int parseParameter(String attributeName, HttpServletRequest request)
			throws IllegalArgumentException {
		int result;
		try {
			result = Integer.parseInt((String) request
					.getParameter(attributeName));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		return result;
	}

	/**
	 * Return the splited result list
	 * @return
	 */
	public List<T> getResultList() {
		return results;
	}

	/**
	 * @return the requested page id
	 */
	public int getRequestedPage() {
		return requestedPage;
	}

	/**
	 * @return the number of results per page
	 */
	public int getNumResultsPerPage() {
		return numResultsPerPage;
	}

	/**
	 * @return the number of pages
	 */
	public int getNumPages() {
		return numPages;
	}

	/**
	 * 
	 * @return true if there is a next page following input datas
	 */
	public boolean hasNextPage() {
		return hasNextPage;
	}

	/**
	 *@return true if there is a previous page following input datas
	 */
	public boolean hasPreviousPage() {
		return hasPreviousPage;
	}
}
