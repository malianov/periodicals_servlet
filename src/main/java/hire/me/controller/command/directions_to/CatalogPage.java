package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatalogPage implements Command {
    private static final Logger logger = LogManager.getLogger(CatalogPage.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("to catalog page");
        return "/WEB-INF/view/catalog_page.jsp";
    }
}
