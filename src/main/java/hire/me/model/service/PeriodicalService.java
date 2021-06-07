package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PeriodicalDao;
import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.entity.account.User;
import hire.me.model.entity.language.Language;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public Periodical getPeriodicById(Long id) {
        logger.trace("getPeriodicById = {}", id);

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

//    public List<Themes> getListOfThemes() {
//        return daoFactory.createThemeDao().findAll();
//    }

    public static class PaginationResult {
        private int nuOfRows;
        private List<Periodical> periodicalList;

        public int getNuOfRows() {
            return nuOfRows;
        }

        public void setNuOfRows(int nuOfRows) {
            logger.trace("setNuOfRows request, nuOfRows = {}", nuOfRows);
            this.nuOfRows = nuOfRows;
        }

        public List<Periodical> getPeriodicalList() {
            return periodicalList;
        }

        public Map<String, List<Periodical>> getPeriodicalMap() {
            logger.trace("getPeriodicalMap(), periodicalList = {}", periodicalList.size());
            List<Periodical> periodicals_en = periodicalList.stream()
                    .filter(t -> t.getLanguage().equals(Language.ENGLISH))
                    .toList();

            List<Periodical> periodicals_ru = periodicalList.stream()
                    .filter(t -> t.getLanguage().equals(Language.RUSSIAN))
                    .toList();

            List<Periodical> periodicals_ua = periodicalList.stream()
                    .filter(t -> t.getLanguage().equals(Language.UKRAINIAN))
                    .toList();

            logger.trace("periodicals_ua  =====  {}", periodicals_ua.size());
            logger.trace("periodicals_ru  =====  {}", periodicals_ru.size());
            logger.trace("periodicals_en  =====  {}", periodicals_en.size());

            Map<String, List<Periodical>> allPeriodicalsByLanguage = new HashMap<>();
            allPeriodicalsByLanguage.put("en", periodicals_en);
            allPeriodicalsByLanguage.put("ua", periodicals_ua);
            allPeriodicalsByLanguage.put("ru", periodicals_ru);

            return allPeriodicalsByLanguage;
        }


        public void setPeriodicalList(List<Periodical> resultList) {
            this.periodicalList = resultList;
        }
    }
}
