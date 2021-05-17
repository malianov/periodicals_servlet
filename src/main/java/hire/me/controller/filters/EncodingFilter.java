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

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		logger.trace("Encoding - charset set to " + req.getCharacterEncoding());
		chain.doFilter(req, resp);
	}

	public void destroy() {
		logger.trace("Destroy Encoding filter");
	}
}
