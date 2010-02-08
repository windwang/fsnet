package fr.univartois.ili.fsnet.commons.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Paginator<T> {

	public static final String PAGE_ID = "pageId";

	public static final String NUM_RESULT_PER_PAGES = "numResults";

	public static final int DEFAULT_NUM_RESULT_PER_PAGE = 15;

	private int requestedPage = 0;

	private int numResultsPerPage;

	private int numPages;

	private boolean hasNextPage;

	private boolean hasPreviousPage;

	private List<T> allResults;

	private List<T> results;

	public Paginator(List<T> results, HttpServletRequest request,
			int numResultsPerPage) {
		this.allResults = results;
		parseRequest(request);
		this.numResultsPerPage = numResultsPerPage;
		init();
	}

	public Paginator(List<T> results, HttpServletRequest request)
			throws IllegalArgumentException {
		this.allResults = results;
		parseRequest(request);
		init();
	}

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
