package hire.me.model.dao.daoFactory;

import hire.me.model.dao.impl.JdbcDaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DaoFactory {
    private static final Logger logger = LogManager.getLogger(DaoFactory.class);

    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract PeriodicalDao createPeriodicalDao();

    public static DaoFactory getInstance() {
        logger.trace("isDatafactoryExist = {}", daoFactory);
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JdbcDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
