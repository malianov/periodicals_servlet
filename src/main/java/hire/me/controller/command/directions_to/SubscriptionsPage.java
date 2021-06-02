package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.subscription.Subscription;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SubscriptionsPage implements Command {
    private static final Logger logger = LogManager.getLogger(SubscriptionsPage.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    SubscriptionService subscriptionService = serviceFactory.getSubscriptionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("SERVICE to subscriptions page");

        final int ROWS_PER_PAGE = 14;
        int currentPage = 1;
        String searchInput = "%";

        if(request.getParameter("current_page") != null) {
            currentPage = Integer.parseInt(request.getParameter("current_page"));
        }

        if(request.getParameter("search_input") != null) {
            searchInput = String.valueOf(request.getParameter("search_input"));
        }

        int lowerBound = (currentPage - 1) * ROWS_PER_PAGE;

        logger.trace("lowerBound = {}, ROWS_PER_PaGE = {}, searchInput = {}", lowerBound, ROWS_PER_PAGE, searchInput);
        SubscriptionService.PaginationResult paginationResult =
                subscriptionService.getSearchSubscriptionWithPagination(lowerBound, ROWS_PER_PAGE, searchInput);

        List<Subscription> subscriptions = paginationResult.getSubscriptionsList();

        logger.trace("==========================================================================" +
                "==================================================================" +
                "subscriptions = {}", subscriptions.size());

        int nuOfRows = paginationResult.getNuOfRows();
        int nuOfPages = (int) Math.ceil(nuOfRows * 1.0 / ROWS_PER_PAGE);

        request.getSession().setAttribute("subscriptions", subscriptions);
        request.getSession().setAttribute("nuOfPages", nuOfPages);
        request.getSession().setAttribute("currentPage", currentPage);
        request.getSession().setAttribute("searchInput", searchInput);
        request.getSession().setAttribute("page", "subscriptions");

        return "/WEB-INF/view/subscriptions_page.jsp";
    }
}

