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
import java.util.Map;

public class SubscriptionsPage implements Command {
    private static final Logger logger = LogManager.getLogger(SubscriptionsPage.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    SubscriptionService subscriptionService = serviceFactory.getSubscriptionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("SERVICE to subscriptions page");

        final int ROWS_PER_PAGE = 15;
        int currentPage = 1;
        String searchInput = "%";



        if(request.getParameter("current_page") != null) {
            currentPage = Integer.parseInt(request.getParameter("current_page"));
            logger.trace("currentPage = {}", currentPage);
        }

        if(request.getParameter("search_input") != null) {
            searchInput = String.valueOf(request.getParameter("search_input"));
        }

        int lowerBound = (currentPage - 1) * ROWS_PER_PAGE;

        SubscriptionService.PaginationResult paginationResult =
                subscriptionService.getSearchSubscriptionWithPagination(lowerBound, ROWS_PER_PAGE, searchInput);

        List<Subscription> subscriptions = paginationResult.getSubscriptionsList();


        int nuOfRows = paginationResult.getNuOfRows();
        int nuOfPages = (int) Math.ceil(nuOfRows * 1.0 / ROWS_PER_PAGE);

        request.getSession().setAttribute("subscriptions", subscriptions);
        request.getSession().setAttribute("nu_of_pages", nuOfPages);
        request.getSession().setAttribute("current_page", currentPage);
        request.getSession().setAttribute("search_input", searchInput);
        request.getSession().setAttribute("page", "subscriptions");




//        return previous_page.get(request.getServletContext().getContextPath());
//
////                "/WEB-INF/view/support.jsp";
////                "/WEB-INF/view/catalog.jsp";
////                "/WEB-INF/view/home_page.jsp";
        return  "/WEB-INF/view/subscriptions_page.jsp";
    }
}

