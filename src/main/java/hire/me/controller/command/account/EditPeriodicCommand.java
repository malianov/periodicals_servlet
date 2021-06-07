package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.entity.account.Person;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.PeriodicalStatus;
import hire.me.model.entity.periodical.PeriodicalType;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static java.lang.String.valueOf;

public class EditPeriodicCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditPeriodicCommand.class);

    ServiceFactory serviceFactory;
    PeriodicalService periodicalService;

    public EditPeriodicCommand() {
        this.serviceFactory = ServiceFactory.getInstance();
        this.periodicalService = serviceFactory.getPeriodicalService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        CommandUtility.disallowBackToCached(request, response);

        Periodical originalPeriodical = periodicalService.getPeriodicById(Long.valueOf(request.getParameter("id")));

        Map<String, String> editionData = Map.of(
                "id", request.getParameter("id"),
                "new_title", request.getParameter("new_title").isBlank() ? originalPeriodical.getTitle() : request.getParameter("new_title"),
                "new_description", request.getParameter("new_description").isBlank() ? originalPeriodical.getDescription() : request.getParameter("new_description"),
                "new_price", request.getParameter("new_price").isBlank() ? originalPeriodical.getPricePerItem().toString() : valueOf(request.getParameter("new_price")));

        Periodical periodical = updatePeriodical(editionData);

        try {
            periodicalService.updatePeriodic(periodical);
        } catch (Exception e) {
            e.printStackTrace();
            return "/WEB-INF/view/error_message.jsp";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_catalog_page";
    }

    private Periodical updatePeriodical(Map<String, String> editionData) {
        logger.trace("periodic information update");

        final Periodical periodical = new Periodical.Builder()
                .id(Integer.parseInt(editionData.get("id")))
                .title(editionData.get("new_title"))
                .description(editionData.get("new_description"))
//                .pricePerItem(new BigDecimal(editionData.get("new_price")))
                .build();

        return periodical;
    }
}
