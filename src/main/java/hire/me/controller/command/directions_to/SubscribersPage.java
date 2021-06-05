package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
import hire.me.model.entity.account.User;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SubscribersPage implements Command {
    private static final Logger logger = LogManager.getLogger(SubscribersPage.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    UserService userService = serviceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("to subscribers page");

        final int ROWS_PER_PAGE = 11;
        int currentPage = 1;
        String searchInput = "%";

        if(request.getParameter("current_page") != null) {
            currentPage = Integer.parseInt(request.getParameter("current_page"));
        }

        if(request.getParameter("search_input") != null) {
            searchInput = String.valueOf(request.getParameter("search_input"));
        }

        int lowerBound = (currentPage - 1) * ROWS_PER_PAGE;

        UserService.PaginationResult paginationResult =
                userService.getSearchSubscribersWithPagination(lowerBound, ROWS_PER_PAGE, searchInput);

        List<User> subscribers = paginationResult.getSubscribersList();

        int nuOfRows = paginationResult.getNuOfRows();
        int nuOfPages = (int) Math.ceil(nuOfRows * 1.0 / ROWS_PER_PAGE);

        request.getSession().setAttribute("subscribers", subscribers);
        request.getSession().setAttribute("nu_of_pages", nuOfPages);
        request.getSession().setAttribute("current_page", currentPage);
        request.getSession().setAttribute("search_input", searchInput);
        request.getSession().setAttribute("page", "subscribers");

        return "/WEB-INF/view/subscribers_page.jsp";
    }
}
