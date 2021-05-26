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
import java.util.HashSet;

public class CommandUtility {
    private static final Logger logger = LogManager.getLogger(CommandUtility.class);

    private static UserService userService;

    static {
        userService = UserService.getInstance();
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String login) {
        logger.trace("Check is user with login = {} is logged", login);
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession()
                .getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(login::equals)) {
            logger.trace("User with login = {} is logged", login);
            return true;
        }

        loggedUsers.add(login);
        logger.trace("User with login = {} is added to the logged users list", login);

        request.getSession()
                .getServletContext()
                .setAttribute("loggedUsers", loggedUsers);

        System.out.println("The quantity of loggedUsers are = " + loggedUsers);
        return false;
    }

    public static void loginUser(HttpServletRequest request, String login, String password, UserRole userRole){
        logger.trace("Set attributes with login = {}, password = {} and userRole = {}", login, password, userRole);
        request.getSession().setAttribute("password", password);
        request.getSession().setAttribute("login", login);
        request.getSession().setAttribute("role", userRole);
    }

    public static void logoutUser(HttpServletRequest request, String login) {
        logger.trace("Inside logoutUser for login = {}", login);

        HashSet<String> loggedUsers = (HashSet<String>)
                request.getSession().getServletContext().getAttribute("loggedUsers");

        loggedUsers.remove(login);
        logger.trace("User with login = {} is being removed from list of logged users", login);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);

        final HttpSession session = request.getSession();
        session.removeAttribute("login");
        session.removeAttribute("password");
        session.removeAttribute("role");
    }

    public static User getCurrentSessionUser(HttpServletRequest request){
        logger.trace("Inside getCurrentSessionUser");

        final HttpSession session = request.getSession();
        String login = session.getAttribute("login").toString();

        UserService userService = UserService.getInstance();
        return userService.getUserByLogin(login);
    }

    public static void disallowBackToCached(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final HttpSession session = request.getSession();
        final String path = request.getServletContext().getContextPath();

        //to prevent user coming back to cached pages after logout
        response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);
        if (session.getAttribute("login") == null || session.getAttribute("password") == null) {
            response.sendRedirect(path + "/WEB-INF/common/error/invalidSession.jsp");
        }
    }
}

