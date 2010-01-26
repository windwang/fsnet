package fr.univartois.ili.fsnet.admin.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import fr.univartois.ili.fsnet.admin.utils.FSNetMailer;

public class IsConfigurationSet implements Filter {

	@Override
	public void destroy() {		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (FSNetMailer.getInstance().isReadyToSendMail()) {
			chain.doFilter(request, response);
		} else if (((HttpServletRequest)request).getRequestURI().endsWith("/ModifyOptions.do")) {
			chain.doFilter(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/DisplayOptions.do");
			rd.forward(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	

}
