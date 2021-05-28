package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditPeriodicCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditPeriodicCommand.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    PeriodicalService periodicalService = serviceFactory.getPeriodicalService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.trace("Entered to execute");

        final String periodicId = req.getParameter("periodic_id");
        logger.trace("Periodic id = {}", periodicId);





return null;
    }
}