package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.entity.periodical.PeriodicalStatus;
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
        logger.trace("Inside constructor");
        this.serviceFactory = ServiceFactory.getInstance();
        this.periodicalService = serviceFactory.getPeriodicalService();
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("execute");
        CommandUtility.disallowBackToCached(request, response);

        final String periodical_id = request.getParameter("periodic_id");
        String newPeriodicStatus = request.getParameter("periodic_status");

        /*

        08.06.2021 - 13:43

        if(newPeriodicStatus.equals("make_orderable")) {
            newPeriodicStatus = PeriodicalStatus.ORDERABLE.name();
        } else if(newPeriodicStatus.equals("make_nonorderable")) {
            newPeriodicStatus = PeriodicalStatus.NONORDERABLE.name();
        } else {
            // Exception - no suitable value
        }*/

        try {
            periodicalService.changePeriodicalStatus(periodical_id, newPeriodicStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return "/WEB-INF/view/catalog_page.jsp";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_catalog_page";
    }
}
