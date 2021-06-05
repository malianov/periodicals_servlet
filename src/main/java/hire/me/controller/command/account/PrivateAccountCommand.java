package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.model.service.PrivateAccountService;
import hire.me.model.service.ServiceFactory;
import hire.me.model.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PrivateAccountCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PrivateAccountCommand.class);
    private PrivateAccountService privateAccountService;
    private ServiceFactory serviceFactory;

    public PrivateAccountCommand() {
        this.serviceFactory = ServiceFactory.getInstance();
        this.privateAccountService = serviceFactory.getPrivateAccountService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final BigDecimal additionToBalance = request.getParameter("addition_to_balance").isBlank() ? BigDecimal.ZERO : new BigDecimal(request.getParameter("addition_to_balance"));
        Map<String, String> collectedErrors = new HashMap<>();

        if (additionToBalance.signum() < 0) {
            collectedErrors.put("errorLoginNotValid", "The entered value is below 0, this is incorrect value.");
            request.setAttribute("errorMessages", collectedErrors);
            return "/WEB-INF/view/error_message.jsp";
        }

        final HttpSession session = request.getSession();
        final Long subscriberId = (Long) session.getAttribute("user_id");
        String pager = request.getParameter("pager");
        String path = request.getServletContext().getContextPath();

        Map<String, String> previous_page = Map.of(
                "home", "/app/to_home_page",
                "catalog", "/app/to_catalog_page",
                "my_subscriptions", "/app/to_my_subscriptions_page",
                "support", "/app/to_support_page");

        if (additionToBalance.compareTo(BigDecimal.ZERO) == 0) {
            return "redirect@" + path + previous_page.get(pager);
        }

        privateAccountService.increaseBalance(subscriberId, additionToBalance);
        request.getSession().setAttribute("subscriberBalance", serviceFactory.getPrivateAccountService().getSubscriberBalance(subscriberId));

        return "redirect@" + path + previous_page.get(pager);
    }
}
