package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
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

        final int ROWS_PER_PAGE = 19;
        int currentPage = 1;

        if(request.getParameter("current_page") != null) {
            currentPage = Integer.parseInt(request.getParameter("current_page"));
        }

        performPagination(request, currentPage, ROWS_PER_PAGE);

        return "/WEB-INF/view/catalog_page.jsp";

//        String path = request.getServletContext().getContextPath();
//        logger.trace("Path is {}", path);
//        return "redirect@" + path + "/app/to_catalog_page";
    }

    private void performPagination(HttpServletRequest request, int currentPage, int rowsPerPage) {

//        String searchInput = String.valueOf(request.getSession().getAttribute("search_input"));
        String searchInput = String.valueOf(request.getParameter("search_input"));
        logger.info("==================================Search input - {}", searchInput);

        int lowerBound = calcLowerBound(currentPage, rowsPerPage);
        logger.info("lowerBound = {}", lowerBound);



        PeriodicalService.PaginationResult paginationResult =
                periodicalService.getSearchPeriodicalByPagination(lowerBound, rowsPerPage, searchInput);

        List<Periodical> periodicals = paginationResult.getPeriodicalList();
        logger.trace("periodicals = {}", periodicals);

        int nuOfRows = paginationResult.getNoOfRows();
        logger.trace("nuOfRows = {}", nuOfRows);

        int noOfPages = calcNoOfPages(nuOfRows, rowsPerPage);
        logger.trace("noOfPages = {}", noOfPages);

        request.getSession().setAttribute("periodicals", periodicals);
        request.getSession().setAttribute("noOfPages", noOfPages);
        request.getSession().setAttribute("currentPage", currentPage);
    }

    private int calcLowerBound(int currentPage, int rowsPerPage) {
        return (currentPage - 1) * rowsPerPage;
    }

    private int calcNoOfPages(int noOfRows, int rowsPerPage) {
        return (int) Math.ceil(noOfRows * 1.0 / rowsPerPage);
    }
}
