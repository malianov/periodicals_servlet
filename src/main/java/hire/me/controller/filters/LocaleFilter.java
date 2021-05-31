package hire.me.controller.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocaleFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LocaleFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
        logger.trace("Initialization for Locale filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest requestHttp = (HttpServletRequest) request;

        if(request.getParameter("language") != null) {
            requestHttp.getSession().setAttribute("language", request.getParameter("language"));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.trace("Destroying");
    }
}
