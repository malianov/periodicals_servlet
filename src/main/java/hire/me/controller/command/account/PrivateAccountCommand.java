package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.model.service.PrivateAccountService;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

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
        logger.trace("Inside execute");
        final BigDecimal additionToBalance = new BigDecimal(request.getParameter("addition_to_balance"));

        final HttpSession session = request.getSession();

        final Long subscriberId = (Long) session.getAttribute("user_id");

        privateAccountService.increaseBalance(subscriberId, additionToBalance);
        request.getSession().setAttribute("subscriberBalance", serviceFactory.getPrivateAccountService().getSubscriberBalance(subscriberId));

        return "/WEB-INF/view/home_page.jsp";
    }
}
