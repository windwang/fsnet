package fr.univartois.ili.fsnet.commons.pagination;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FSNet
 * 
 * @param <T>
 */
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
	 * The name of the parameter in the request to save the request input
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public static final String DEFAULT_REQUEST_INPUT_NAME = "requestInput";

	/**
	 * The identifier of the current tile
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public static final String TILE_ID = "tileId";

	/**
	 * The default number of results if The user or developer has not set this
	 * parameter
	 */
	public static final int DEFAULT_NUM_RESULT_PER_PAGE = 15;

	/**
	 * The default id for tile identifier
	 */
	public static final String DEFAULT_TILE_ID = "default";

	/**
	 * The requested page id name
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	private String requestInputName;

	/**
	 * The requested page id
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	private String requestInput;

	/**
	 * The tile identifier
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	private String identifier = "default";

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
	 * Default constructor that allow user to set the number of result by
	 * including the proper parameter in his request
	 * 
	 * @param results
	 * @param request
	 * @param identifier
	 *            the tile identifier
	 * @throws IllegalArgumentException
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public Paginator(List<T> results, HttpServletRequest request,
			String identifier) throws IllegalArgumentException {
		this.allResults = results;
		this.identifier = identifier;
		this.requestInputName = DEFAULT_REQUEST_INPUT_NAME;
		parseRequest(request);
		init();
	}

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
	 * @param identifier
	 *            the tile identifier
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public Paginator(List<T> results, HttpServletRequest request,
			int numResultsPerPage, String identifier) {
		this.allResults = results;
		this.identifier = identifier;
		this.requestInputName = DEFAULT_REQUEST_INPUT_NAME;
		parseRequest(request);
		this.numResultsPerPage = numResultsPerPage;
		init();
	}

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
	 * @param identifier
	 *            the tile identifier
	 * @param requestInputName
	 *            the requestInput name
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public Paginator(List<T> results, HttpServletRequest request,
			int numResultsPerPage, String identifier, String requestInputName) {
		this.allResults = results;
		this.identifier = identifier;
		this.requestInputName = requestInputName;
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
	 * @param identifier
	 *            the tile identifier
	 * @param requestInputName
	 *            the requestInput name
	 * @throws IllegalArgumentException
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public Paginator(List<T> results, HttpServletRequest request,
			String identifier, String requestInputName)
			throws IllegalArgumentException {
		this.allResults = results;
		this.identifier = identifier;
		this.requestInputName = requestInputName;
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
		hasNextPage = (requestedPage < numPages - 1);
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

	/**
	 * Parse the parameters from the request
	 * 
	 * @param request
	 * @throws IllegalArgumentException
	 */
	private void parseRequest(HttpServletRequest request)
			throws IllegalArgumentException {
		if (!request.getParameterMap().containsKey(TILE_ID)
				|| ((String) request.getParameter(TILE_ID)).equals(identifier)
				|| ((String) request.getParameter(TILE_ID))
						.equals(DEFAULT_TILE_ID)) {
			if (request.getParameterMap().containsKey(PAGE_ID)) {
				requestedPage = parseParameter(PAGE_ID, request);
				if (requestedPage < 0) {
					throw new IllegalArgumentException();
				}
			} else {
				requestedPage = 0;
			}
			if (request.getParameterMap().containsKey(NUM_RESULT_PER_PAGES)) {
				numResultsPerPage = parseParameter(NUM_RESULT_PER_PAGES,
						request);
				if (numResultsPerPage < 0) {
					this.numResultsPerPage = DEFAULT_NUM_RESULT_PER_PAGE;
				}
			} else {
				this.numResultsPerPage = DEFAULT_NUM_RESULT_PER_PAGE;
			}
			if (request.getParameterMap().containsKey(requestInputName)) {
				requestInput = (String) request.getParameter(requestInputName);
			} else {
				requestInput = "";
			}
		} else {
			requestedPage = 0;
			this.numResultsPerPage = DEFAULT_NUM_RESULT_PER_PAGE;
			requestInput = "";
		}
	}

	private int parseParameter(String attributeName, HttpServletRequest request)
			throws IllegalArgumentException {
		int result;
		try {
			result = Integer.parseInt((String) request
					.getParameter(attributeName));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return result;
	}

	/**
	 * Return the splited result list
	 * 
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
	public boolean getHasNextPage() {
		return hasNextPage;
	}

	/**
	 * 
	 * @return the next page if possible or the current one
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public int getNextPage() {
		int nextPage = getRequestedPage();
		if (getHasNextPage()) {
			nextPage++;
		}
		return nextPage;
	}

	/**
	 * @return true if there is a previous page following input datas
	 */
	public boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	/**
	 * 
	 * @return the previous page if possible or the current one
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public int getPreviousPage() {
		int previousPage = getRequestedPage();
		if (getHasPreviousPage()) {
			previousPage--;
		}
		return previousPage;
	}

	/**
	 * 
	 * @return the request input
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public String getRequestInput() {
		return requestInput;
	}

	/**
	 * 
	 * @return the request input name
	 * 
	 * @author Alexandre Lohez <alexandre.lohez at gmail.com>
	 */
	public String getRequestInputName() {
		return requestInputName;
	}
}
