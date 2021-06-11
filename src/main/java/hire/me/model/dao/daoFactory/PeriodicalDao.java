package hire.me.model.dao.daoFactory;

import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalService;

public interface PeriodicalDao extends AbstractDao<Periodical> {
    boolean isPeriodicalExist(final String name);

    boolean nameIsAlreadyUsed(final String name);

    void changePeriodicalStatus(String periodical_id, String newPeriodicStatus);

    PeriodicalService.PaginationResult searchPeriodicalsWithPagination(int lowerBound, int upperBound, String
            searchKey);
}
