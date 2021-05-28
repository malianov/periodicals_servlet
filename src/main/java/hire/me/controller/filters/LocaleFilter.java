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
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        logger.trace("do Locale Filter");
        HttpServletRequest request = (HttpServletRequest) req;

        logger.trace("language = {}", request.getParameter("language"));
        if(request.getParameter("language") != null) {
            request.getSession().setAttribute("language", request.getParameter("language"));
            logger.trace("Because language != null, lang = {}", request.getParameter("language"));
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        logger.trace("Destroying");
    }
}
