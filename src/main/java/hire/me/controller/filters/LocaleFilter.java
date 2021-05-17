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


/*    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req   = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        String language = (String) req.getSession().getAttribute("language");

        logger.info("URL is " + req.getRequestURI());
        logger.info("Session Language is " + req.getSession().getAttribute("language"));

        String path = req.getRequestURI()
                .replace(req.getContextPath(), "")
                .replace(req.getServletPath(), "")
                .replace("/", "");

        logger.info("URL is " + path);

        if(req.getParameter("language").isBlank()) {
            resp.sendRedirect(path);
        }

        logger.info("Language is " + language);

        boolean isEnglish   = language.equals("EN");
        boolean isUkrainian = language.equals("UA");
        boolean isRussian = language.equals("RU");

        if (isEnglish) {
            req.getSession().setAttribute("language", "en-EN");
        } else if (isUkrainian) {
            req.getSession().setAttribute("language", "uk-UA");
        } else if (isRussian) {
            req.getSession().setAttribute("language", "ru-RU");
        }

        logger.info("Language set to " + req.getSession().getAttribute("language"));
        req.getSession().getAttribute("language");
        resp.sendRedirect(path);
    }*/

    @Override
    public void destroy() {
        logger.trace("Destroying");
    }
}
