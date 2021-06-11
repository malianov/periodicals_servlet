package hire.me.controller.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(EncodingFilter.class);

    public void init(FilterConfig config) throws ServletException {
        logger.trace("Encoding filter initialization");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.trace("Encoding filter: do filter");

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
