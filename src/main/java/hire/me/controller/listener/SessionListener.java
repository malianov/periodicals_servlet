package hire.me.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.trace("SessionListener: sessionDestroyed");

        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession()
                .getServletContext()
                .getAttribute("loggedUsers");

        String login = (String) httpSessionEvent.getSession().getAttribute("login");
        loggedUsers.remove(login);
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
    }
}
