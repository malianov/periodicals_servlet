package hire.me.controller;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandFactory;
import hire.me.model.exception.NotFoundCommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            processRequest(req, resp);
        } catch (NotFoundCommandException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            processRequest(req, resp);
        } catch (NotFoundCommandException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, NotFoundCommandException, ServletException {

        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        String path = req.getRequestURI();
        path = path.replaceAll(".*/app/", "");

        Command command = CommandFactory.getCommand(path);
        command.execute(req, resp);
    }
}
