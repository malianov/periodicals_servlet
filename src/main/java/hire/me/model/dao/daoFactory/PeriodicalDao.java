package hire.me.model.dao.daoFactory;

import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalService;

public interface PeriodicalDao extends AbstractDao<Periodical> {
    boolean isPeriodicalExist(final String name);
    boolean nameIsAlreadyUsed(final String name);
    PeriodicalService.PaginationResult searchPeriodicalsByPagination(int lowerBound, int upperBound, String
            searchKey);
}
