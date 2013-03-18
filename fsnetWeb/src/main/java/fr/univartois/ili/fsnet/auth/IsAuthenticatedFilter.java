package fr.univartois.ili.fsnet.auth;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This filter is used to make sure that the current request is made by an
 * authenticated user
 * 
 * @author Mathieu Boniface < mat.boniface {At} gmail.com >
 */

@WebFilter("*.do")
public class IsAuthenticatedFilter implements Filter {

	private ServletContext servletContext;

	/**
	 * Verify if the current user is authenticated
	 */
	public void doFilter(final ServletRequest req,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		HttpSession session;

		RequestDispatcher dispatch;
		HttpServletRequest request = (HttpServletRequest) req;
		session = request.getSession(); // NOSONAR
		Integer userId = (Integer) session
				.getAttribute(Authenticate.AUTHENTICATED_USER);
		
		if (userId == null) {			
			String pathURI = ((HttpServletRequest) request).getRequestURI();			
			if (pathURI != null && pathURI.matches(".*\\/Talk[^/]+\\.do")) {
				Logger.getAnonymousLogger().info("Avoid to load TalkMembersReceive");
				session.setAttribute("requestedURL", null);
			} else {
				// Reconstruct the requested url and store it in the session
				StringBuffer requestedURL = ((HttpServletRequest) request)
						.getRequestURL();
				if (request.getQueryString() != null) {
					requestedURL.append('?');
					requestedURL.append(request.getQueryString());
				}
				session.setAttribute("requestedURL", requestedURL.toString());
			}

			dispatch = servletContext
					.getRequestDispatcher("/Authenticate");
			dispatch.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * Init method used to retrieve Servlet Context
	 */
	public void init(final FilterConfig filterConfig) throws ServletException {
		servletContext = filterConfig.getServletContext();
	}

	@Override
	public void destroy() {
	}
}
