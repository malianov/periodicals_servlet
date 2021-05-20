package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PeriodicalDao;
import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.entity.account.User;
import hire.me.model.entity.periodical.Periodical;

import java.sql.Connection;
import java.util.List;

public class PeriodicalService {

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

    public List<Periodical> getAllPeriodics() {
        PeriodicalDao dao = daoFactory.createPeriodicalDao();
        return dao.findAll();
    }

    public PaginationResult getSearchPeriodicalByPagination(int lowerBound, int upperBound, String searchKey) {
        return daoFactory.createPeriodicalDao().searchPeriodicalsByPagination(lowerBound, upperBound, searchKey);
    }

    public static class PaginationResult {
        private int noOfRows;
        private List<Periodical> periodicalList;

        public int getNoOfRows() {
            return noOfRows;
        }

        public void setNoOfRows(int noOfRows) {
            this.noOfRows = noOfRows;
        }

        public List<Periodical> getPeriodicalList() {
            return periodicalList;
        }

        public void setPeriodicalList(List<Periodical> resultList) {
            this.periodicalList = resultList;
        }
    }
}
