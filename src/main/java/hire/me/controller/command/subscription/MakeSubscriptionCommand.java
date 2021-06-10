package hire.me.controller.command.subscription;

import hire.me.controller.command.Command;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MakeSubscriptionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MakeSubscriptionCommand.class);

    private SubscriptionService subscriptionService;
    private ServiceFactory serviceFactory;

    public MakeSubscriptionCommand() {
        logger.trace("Entered to MakeSubscriptionCommand constructor");

        this.serviceFactory = ServiceFactory.getInstance();
        this.subscriptionService = serviceFactory.getSubscriptionService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("Execute {}", request.getParameter("periodic_id"));

        Map<String, String> collectedErrors = new HashMap<>();

        final Integer subscribedPeriodicId = Integer.valueOf((request.getParameter("periodic_id")));
        final String[] selectedPeriodicItems = request.getParameterValues("selected");
        final String subscriptionYear = request.getParameter("subscription_year");
        final String subscriberAddress = request.getParameter("address");

        logger.trace("Execute {}", request.getParameter("selected"));

        final HttpSession session = request.getSession();
        final Long subscriberId = (Long) session.getAttribute("user_id");

        if(selectedPeriodicItems == null) {
            collectedErrors.put("errorUserDidntChooseAnyItem", "error_message.there-are-no-chosen-months-in-your-confirmed-subscription");
            request.setAttribute("errorMessages", collectedErrors);
            return "/WEB-INF/view/error_message.jsp";
        }

        if(subscriptionService.isSubscriptionSuccessful(subscriberId, subscribedPeriodicId, subscriptionYear, selectedPeriodicItems, subscriberAddress)) {
            System.out.println("==========================    =========     ============ " + serviceFactory.getSubscriptionService().getActualSubscriberBalance(subscriberId));

            request.getSession().setAttribute("subscriberBalance", serviceFactory.getSubscriptionService().getActualSubscriberBalance(subscriberId));
            logger.trace("New balance is - {}", session.getAttribute("subscriberBalance"));
            logger.trace("money from service - {}", serviceFactory.getPrivateAccountService().getSubscriberBalance(subscriberId));
        } else {
            collectedErrors.put("errorUserHasNoEnoughMoney", "error_message.change-your-subscription-list");
        }

        logger.trace("serviceFactory.getPrivateAccountService().getSubscriberBalance(subscriberId) = {}", serviceFactory.getPrivateAccountService().getSubscriberBalance(subscriberId));

        if(!collectedErrors.isEmpty()) {
            request.setAttribute("errorMessages", collectedErrors);
            logger.trace("inside collectedErrors are {} errors", collectedErrors.size());
            return "/WEB-INF/view/error_message.jsp";
        }

        String path = request.getServletContext().getContextPath();
//        logger.trace("BALANCE BEFOR REDIRECT = {}", session.getAttribute("subscriberBalance"));
        return "redirect@" + path + "/app/to_catalog_page";

    }
}
