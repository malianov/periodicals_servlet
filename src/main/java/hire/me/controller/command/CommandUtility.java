package hire.me.controller.command;

import hire.me.model.entity.Subscriber;
import hire.me.model.service.SubscriberService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

public class CommandUtility {

    private static SubscriberService subscriberService;

    static {
        subscriberService = SubscriberService.getInstance();
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, String email) {

        HashSet<String> loggedUsers = (HashSet<String>) request.getSession()
                .getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(email::equals)) {
            return true;
        }

        loggedUsers.add(email);
        request.getSession()
                .getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }


    public static void logUser(HttpServletRequest request, String login, String password){
        request.getSession().setAttribute("password", password);
        request.getSession().setAttribute("login", login);
    }

    public static void logoutUser(HttpServletRequest request, String login) {

        HashSet<String> loggedUsers = (HashSet<String>)
                request.getSession().getServletContext().getAttribute("loggedUsers");

        loggedUsers.remove(login);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);

        final HttpSession session = request.getSession();
        session.removeAttribute("login");
        session.removeAttribute("password");
    }

    public static Subscriber getCurrentSessionUser(HttpServletRequest request){

        final HttpSession session = request.getSession();
        String email = session.getAttribute("email").toString();

        SubscriberService subscriberService = SubscriberService.getInstance();
        return subscriberService.getSubscriberByEmail(email);
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
        if (session.getAttribute("email") == null || session.getAttribute("password") == null) {
            response.sendRedirect(path + "/WEB-INF/common/error/invalidSession.jsp");
        }
    }
}


