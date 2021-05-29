package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CatalogPage implements Command {
    private static final Logger logger = LogManager.getLogger(CatalogPage.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    PeriodicalService periodicalService = serviceFactory.getPeriodicalService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("to catalog page");

//        CommandUtility.disallowBackToCached(request, response);

        final int ROWS_PER_PAGE = 14;
        int currentPage = 1;

        if(request.getParameter("current_page") != null) {
            currentPage = Integer.parseInt(request.getParameter("current_page"));
        }

        String searchInput = "%";

        if(request.getParameter("search_input") != null) {
            searchInput = String.valueOf(request.getParameter("search_input"));

        }

        int lowerBound = (currentPage - 1) * ROWS_PER_PAGE;

        PeriodicalService.PaginationResult paginationResult =
                periodicalService.getSearchPeriodicalWithPagination(lowerBound, ROWS_PER_PAGE, searchInput);

        List<Periodical> periodicals = paginationResult.getPeriodicalList();

        int nuOfRows = paginationResult.getNuOfRows();
        logger.trace("======================= nuOfRows - {}", nuOfRows);

        int nuOfPages = (int) Math.ceil(nuOfRows * 1.0 / ROWS_PER_PAGE);

        logger.trace("======================= nuOfPages - {}", nuOfPages);

        request.getSession().setAttribute("periodicals", periodicals);
        request.getSession().setAttribute("nuOfPages", nuOfPages);
        request.getSession().setAttribute("currentPage", currentPage);
        request.getSession().setAttribute("searchInput", searchInput);
        request.getSession().setAttribute("page", "catalog");

        return "/WEB-INF/view/catalog_page.jsp";
    }
}
