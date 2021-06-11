package hire.me.controller.command.directions_to;

import hire.me.controller.command.Command;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.Theme;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.ThemeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CatalogPage implements Command {
    private static final Logger logger = LogManager.getLogger(CatalogPage.class);

    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    PeriodicalService periodicalService = serviceFactory.getPeriodicalService();
    ThemeService themeService = serviceFactory.getThemeService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("CatalogPageCommand executing");

        final int ROWS_PER_PAGE = 11;
        int currentPage = 1;
        String searchInput = "%";

        if (request.getParameter("current_page") != null) {
            currentPage = Integer.parseInt(request.getParameter("current_page"));
        }

        if (request.getParameter("search_input") != null) {
            searchInput = String.valueOf(request.getParameter("search_input"));
        }

        int lowerBound = (currentPage - 1) * ROWS_PER_PAGE;

        PeriodicalService.PaginationResult paginationResult =
                periodicalService.getSearchPeriodicalWithPagination(lowerBound, ROWS_PER_PAGE, searchInput);

        List<Theme> themes = themeService.getThemes();
        List<Periodical> allPeriodicals = paginationResult.getPeriodicalList();

        int nuOfRows = paginationResult.getNuOfRows();
        int nuOfPages = (int) Math.ceil(nuOfRows * 1.0 / ROWS_PER_PAGE);

        request.getSession().setAttribute("periodicals", allPeriodicals);
        request.getSession().setAttribute("nu_of_pages", nuOfPages);
        request.getSession().setAttribute("current_page", currentPage);
        request.getSession().setAttribute("search_input", searchInput);
        request.getSession().setAttribute("page", "catalog");
        request.getSession().setAttribute("list_of_themes", themes);

        return "/WEB-INF/view/catalog_page.jsp";
    }
}
