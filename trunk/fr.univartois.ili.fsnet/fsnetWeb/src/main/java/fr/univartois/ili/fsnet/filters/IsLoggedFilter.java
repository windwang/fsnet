package fr.univartois.ili.fsnet.filters;

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
public class IsLoggedFilter implements Filter {

	private transient ServletContext servC;

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		HttpSession session;
		EntiteSociale ent;
		RequestDispatcher dispatch;

		session = ((HttpServletRequest) request).getSession();
		ent = (EntiteSociale) session.getAttribute("entite");

		if (ent == null) {
			dispatch = servC.getRequestDispatcher("/login.jsp");
			dispatch.forward(request, response);
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(final FilterConfig fConfig) throws ServletException {
		servC = fConfig.getServletContext();
	}

}
