package hire.me.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;

public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.trace("ContextListener: initialization");

        final ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("loggedUsers", new HashSet<String>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.trace("ContextListener: destroying");

        final ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute("loggedUsers");
    }
}
