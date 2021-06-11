package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PeriodicalDao;
import hire.me.model.entity.periodical.Periodical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PeriodicalService {
    private static final Logger logger = LogManager.getLogger(PeriodicalService.class);

    private DaoFactory daoFactory;
    private static PeriodicalService instance;

    private PeriodicalService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static PeriodicalService getInstance() {
        if (instance == null) {
            synchronized (PeriodicalService.class) {
                if (instance == null) {
                    instance = new PeriodicalService();
                }
            }
        }
        return instance;
    }

    public void updatePeriodic(Periodical periodical) {
        PeriodicalDao periodicalDao = daoFactory.createPeriodicalDao();
        periodicalDao.update(periodical);
    }

    public Optional<Periodical> getPeriodicById(Long id) {
        PeriodicalDao dao = daoFactory.createPeriodicalDao();
        return dao.findById(id);
    }

    public List<Periodical> getAllPeriodics() {
        PeriodicalDao dao = daoFactory.createPeriodicalDao();
        return dao.findAll();
    }

    public void changePeriodicalStatus(String periodical_id, String newPeriodicStatus) {
        PeriodicalDao dao = daoFactory.createPeriodicalDao();
        dao.changePeriodicalStatus(periodical_id, newPeriodicStatus);
    }

    public PaginationResult getSearchPeriodicalWithPagination(int lowerBound, int upperBound, String searchKey) {
        return daoFactory.createPeriodicalDao().searchPeriodicalsWithPagination(lowerBound, upperBound, searchKey);
    }

    public static class PaginationResult {
        private int nuOfRows;
        private List<Periodical> periodicalList;

        public int getNuOfRows() {
            return nuOfRows;
        }

        public void setNuOfRows(int nuOfRows) {
            this.nuOfRows = nuOfRows;
        }

        public List<Periodical> getPeriodicalList() {
            return periodicalList;
        }

        public void setPeriodicalList(List<Periodical> resultList) {
            this.periodicalList = resultList;
        }
    }
}
