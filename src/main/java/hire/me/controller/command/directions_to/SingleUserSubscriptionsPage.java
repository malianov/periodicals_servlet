package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
import hire.me.model.entity.subscription.Subscription;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SingleUserSubscriptionsPage implements Command {
    private static final Logger logger = LogManager.getLogger(SingleUserSubscriptionsPage.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    SubscriptionService subscriptionService = serviceFactory.getSubscriptionService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("SERVICE to subscriptions page");

        final int ROWS_PER_PAGE = 14;
        int currentPage = 1;

        final HttpSession session = request.getSession();
        final String subscriberId = String.valueOf(session.getAttribute("user_id"));

        logger.trace("subscriberId ========================= {}", subscriberId);

        if(request.getParameter("current_page") != null) {
            currentPage = Integer.parseInt(request.getParameter("current_page"));
        }

        int lowerBound = (currentPage - 1) * ROWS_PER_PAGE;

        logger.trace("lowerBound = {}, ROWS_PER_PaGE = {}, searchInput = {}", lowerBound, ROWS_PER_PAGE, subscriberId);



        SubscriptionService.PaginationResult paginationResult =
                subscriptionService.getSearchSubscriptionWithPagination(lowerBound, ROWS_PER_PAGE, subscriberId);

        List<Subscription> subscriptions = paginationResult.getSubscriptionsList();

        int nuOfRows = paginationResult.getNuOfRows();
        int nuOfPages = (int) Math.ceil(nuOfRows * 1.0 / ROWS_PER_PAGE);

        request.getSession().setAttribute("subscriptions", subscriptions);
        request.getSession().setAttribute("nu_of_pages", nuOfPages);
        request.getSession().setAttribute("current_page", currentPage);
        request.getSession().setAttribute("page", "my_subscriptions");

        return "/WEB-INF/view/subscriptions_page.jsp";
    }
}

