package fr.univartois.ili.fsnet.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.univartois.ili.fsnet.entities.EntiteSociale;

/**
 * Servlet Filter implementation class IsLoggedFilter
 */
public class IsAuthenticatedFilter implements Filter {

	private transient ServletContext servC;

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		HttpSession session;
		EntiteSociale es;
		RequestDispatcher dispatch;

		session = ((HttpServletRequest) request).getSession();
		es = (EntiteSociale) session
				.getAttribute(Authenticate.AUTHENTICATED_USER);

		if (es == null) {
			dispatch = servC
					.getRequestDispatcher(Authenticate.WELCOME_NON_AUTHENTICATED_PAGE);
			dispatch.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(final FilterConfig fConfig) throws ServletException {
		servC = fConfig.getServletContext();
	}

	@Override
	public void destroy() {
	}
}
