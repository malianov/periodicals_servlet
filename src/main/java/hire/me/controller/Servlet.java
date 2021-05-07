package hire.me.controller;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandFactory;
import hire.me.model.exception.NotFoundCommandException;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Servlet extends HttpServlet {
    private static final Logger logger = getLogger(Servlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            logger.trace("Inside doGet");
            processRequest(req, resp);
        } catch (NotFoundCommandException e) {
            logger.trace("Inside doGet: Exception {}", e);
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            logger.trace("Inside doPost");
            processRequest(req, resp);
        } catch (NotFoundCommandException e) {
            logger.trace("Inside doPost: Exception {}", e);
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, NotFoundCommandException, ServletException {
        logger.trace("Inside processRequest: path = {}", () -> req.getRequestURI());

//        resp.setContentType("text/html; charset=UTF-8");
//        req.setCharacterEncoding("UTF-8");
        String path = req.getRequestURI();
        path = path.replaceAll(".*/app/", "");

        Command command = CommandFactory.getCommand(path);
        logger.trace("Inside processRequest: command = {}", () -> command);
        String page = command.execute(req, resp);
        logger.trace("Page is equal to {}", page);

        if (page.contains("redirect@")) {
            resp.sendRedirect(page.replace("redirect@", ""));
        } else {
            req.getRequestDispatcher(page).forward(req, resp);
        }
    }
}
