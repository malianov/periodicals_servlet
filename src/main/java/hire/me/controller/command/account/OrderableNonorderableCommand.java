package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderableNonorderableCommand implements Command {
    private static final Logger logger = LogManager.getLogger(OrderableNonorderableCommand.class);

    private PeriodicalService periodicalService;
    private ServiceFactory serviceFactory;

    public OrderableNonorderableCommand() {
        this.serviceFactory = ServiceFactory.getInstance();
        this.periodicalService = serviceFactory.getPeriodicalService();
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("OrderableNonorderableCommand executing");

        CommandUtility.disallowBackToCached(request, response);

        final String periodical_id = request.getParameter("periodic_id");
        String newPeriodicStatus = request.getParameter("periodic_status");

        try {
            periodicalService.changePeriodicalStatus(periodical_id, newPeriodicStatus);
        } catch (Exception e) {
            logger.error("User cannot change periodic orderability status for {} to {}", periodical_id, newPeriodicStatus);
            e.printStackTrace();
            return "/WEB-INF/view/catalog_page.jsp";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_catalog_page";
    }
}
