package fr.univartois.ili.fsnet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * Filtre sur les requetes pour les encoder en UTF-8
 * 
 **/
public class CharsetFilter implements Filter {
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	public void destroy() {}

	public void init(final FilterConfig arg0) throws ServletException {}

}
