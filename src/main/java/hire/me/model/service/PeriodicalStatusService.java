package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PeriodicalStatusDao;
import hire.me.model.entity.periodical.PeriodicalStatus;

public class PeriodicalStatusService {

    private DaoFactory daoFactory;
    private static PeriodicalStatusService instance;

    private PeriodicalStatusService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static PeriodicalStatusService getInstance() {
        if (instance == null) {
            synchronized (PeriodicalStatusService.class) {
                if (instance == null) {
                    instance = new PeriodicalStatusService();
                }
            }
        }
        return instance;
    }

    public PeriodicalStatus getPeriodicalStatusServiceById(long id) {
        PeriodicalStatusDao dao = daoFactory.createPeriodicalStatusDao();
        return dao.findById(id).get();
    }
}
