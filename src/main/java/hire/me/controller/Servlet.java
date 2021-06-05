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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            logger.trace("Inside doGet");
            processRequest(request, response);
        } catch (NotFoundCommandException e) {
            logger.trace("Inside doGet: Exception {}", e);
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            logger.trace("Inside doPost");
            processRequest(request, response);
        } catch (NotFoundCommandException e) {
            logger.trace("Inside doPost: Exception {}", e);
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, NotFoundCommandException, ServletException {
        logger.trace("Inside processRequest: path = {}", () -> request.getRequestURI());

        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/", "");

        Command command = CommandFactory.getCommand(path);
        logger.trace("command = {}", command);

        String page = command.execute(request, response);
        logger.trace("page = {}", page);


        if (page.contains("redirect@")) {
            response.sendRedirect(page.replace("redirect@", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
