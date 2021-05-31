package hire.me.controller.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(EncodingFilter.class);

	public void init(FilterConfig config) throws ServletException {
		logger.trace("Initialization for Encoding filter");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		chain.doFilter(request, response);
	}

	public void destroy() {
		logger.trace("Destroy Encoding filter");
	}
}
