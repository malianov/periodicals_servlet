package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PeriodicalDao;
import hire.me.model.dao.daoFactory.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcDaoFactory extends DaoFactory {
    private static final Logger logger = LogManager.getLogger(JdbcDaoFactory.class);

    @Override
    public UserDao createUserDao() {
        logger.trace("");
        return JdbcUserDaoImpl.getInstance();
    }

    @Override
    public PeriodicalDao createPeriodicalDao() {
        logger.trace("");
        return JdbcPeriodicalDaoImpl.getInstance();
    }
}
