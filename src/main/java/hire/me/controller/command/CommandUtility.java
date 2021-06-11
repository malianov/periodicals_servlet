package hire.me.controller.command;

import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;

public class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);

    private static UserService userService;

    static {
        userService = UserService.getInstance();
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String login) {
        logger.trace("Checking, is user is logged to system");

        HashSet<String> loggedUsers = (HashSet<String>) request.getSession()
                .getServletContext()
                .getAttribute("loggedUsers");

        if (loggedUsers.stream().anyMatch(login::equals)) {
            logger.trace("The user is already logged");
            return true;
        }

        loggedUsers.add(login);

        request.getSession()
                .getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static void loginUser(HttpServletRequest request, long user_id, String login, String password, UserRole userRole, BigDecimal subscriberBalance) {
        logger.trace("Placing the User data to session");
        request.getSession().setAttribute("password", password);
        request.getSession().setAttribute("user_id", user_id);
        request.getSession().setAttribute("login", login);
        request.getSession().setAttribute("role", userRole);
        request.getSession().setAttribute("subscriberBalance", subscriberBalance);
    }

    public static void logoutUser(HttpServletRequest request, String login) {
        logger.trace("Request for remove user from session");
        HashSet<String> loggedUsers = (HashSet<String>)
                request.getSession().getServletContext().getAttribute("loggedUsers");

        loggedUsers.remove(login);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);

        final HttpSession session = request.getSession();
        session.removeAttribute("login");
        session.removeAttribute("password");
        session.removeAttribute("role");
        session.removeAttribute("subscriberBalance");
    }

    public static User getCurrentSessionUser(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        String login = session.getAttribute("login").toString();

        UserService userService = UserService.getInstance();
        return userService.getUserByLogin(login);
    }

    public static void disallowBackToCached(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        final HttpSession session = request.getSession();
        final String path = request.getServletContext().getContextPath();

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        if (session.getAttribute("login") == null || session.getAttribute("password") == null) {
            response.sendRedirect(path + "/WEB-INF/common/error/invalidSession.jsp");
        }
    }
}


