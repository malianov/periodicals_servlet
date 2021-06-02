package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SupportPage implements Command {
    private static final Logger logger = LogManager.getLogger(SupportPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("to support page");

        request.getSession().setAttribute("page", "support");
        return "/WEB-INF/view/support_page.jsp";
    }
}