package hire.me.controller.command.subscription;

import hire.me.controller.command.Command;
import hire.me.model.entity.subscription.Subscription;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.SubscriptionService;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

public class MakeSubscriptionCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MakeSubscriptionCommand.class);

    private SubscriptionService subscriptionService;
    private ServiceFactory serviceFactory;

    public MakeSubscriptionCommand() {
        logger.trace("Entered to MakeSubscriptionCommand constructor");

        this.serviceFactory = ServiceFactory.getInstance();
        this.subscriptionService = serviceFactory.getSubscriptionService();
    }
    // periodic_id=8&
    // selected=aug&selected=dec&new_description=%D0%B3.+%D0%9A%D0%B8%D0%B5%D0%B2%2C+%D0%BD%D0%B0+%D0%B4%D0%B5%D1%80%D0%B5%D0%B2%D0%BD%D1%8E+%D0%BA+%D0%B1%D0%B0%D0%B1%D1%83%D1%88%D0%BA%D0%B5

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
logger.trace("Execute");
        final Integer subscribedPeriodicId = Integer.valueOf((request.getParameter("periodic_id")));
        final String[] selectedPeriodicItems = request.getParameterValues("selected");
        final String subscriptionYear = request.getParameter("subscription_year");
        final String subscriberAddress = request.getParameter("address");

        final HttpSession session = request.getSession();
//        final String subscriberLogin = session.getAttribute("login").toString();
        final Long subscriberId = (Long) session.getAttribute("user_id");
//        final int monthsQuantityty = selectedPeriodicMonths.length;

        logger.trace("SubscriberId = {}", subscriberId);
        logger.trace("Subscriber Periodic ID = {}", subscribedPeriodicId);

        subscriptionService.isSubscriptionSuccessful(subscriberId, subscribedPeriodicId, subscriptionYear, selectedPeriodicItems, subscriberAddress);

        request.getSession().setAttribute("subscriberBalance", serviceFactory.getPrivateAccountService().getSubscriberBalance(subscriberId));


        logger.trace("Execute - going to return null");
        return "/WEB-INF/view/home_page.jsp";
    }
}
