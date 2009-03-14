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

	private ServletContext servC;

	/**
	 * Default constructor.
	 */
	public IsLoggedFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpSession session = ((HttpServletRequest) request).getSession();
		EntiteSociale en = (EntiteSociale) session.getAttribute("entite");
		RequestDispatcher dispatch;

		if (en == null) {
			dispatch = servC.getRequestDispatcher("/login.jsp");
			dispatch.forward(request, response);
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		servC = fConfig.getServletContext();
	}

}
