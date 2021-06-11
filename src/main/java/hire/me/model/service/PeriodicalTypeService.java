package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PeriodicalTypeDao;
import hire.me.model.entity.periodical.PeriodicalType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PeriodicalTypeService {
    private static final Logger logger = LogManager.getLogger(PeriodicalTypeService.class);

    private DaoFactory daoFactory;
    private static PeriodicalTypeService instance;

    private PeriodicalTypeService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static PeriodicalTypeService getInstance() {
        if (instance == null) {
            synchronized (PeriodicalTypeService.class) {
                if (instance == null) {
                    instance = new PeriodicalTypeService();
                }
            }
        }
        return instance;
    }

    public PeriodicalType getPeriodicalTypeById(long id) {
        PeriodicalTypeDao dao = daoFactory.createPeriodicalTypeDao();
        return dao.findById(id).get();
    }
}
