package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcDaoFactory extends DaoFactory {
    private static final Logger logger = LogManager.getLogger(JdbcDaoFactory.class);

    @Override
    public UserDao createUserDao() {
        return JdbcUserDaoImpl.getInstance();
    }

    @Override
    public PeriodicalDao createPeriodicalDao() {
        return JdbcPeriodicalDaoImpl.getInstance();
    }

    @Override
    public SubscriptionDao createSubscriptionDao() {
        return JdbcSubscriptionDaoImpl.getInstance();
    }

    @Override
    public PrivateAccountDao createPrivateAccountDao() {
        return JdbcPrivateAccountDaoImpl.getInstance();
    }

    @Override
    public ThemeDao createThemeDao() {
        return JdbcThemeDaoImpl.getInstance();
    }

    @Override
    public PeriodicalTypeDao createPeriodicalTypeDao() {
        return JdbcPeriodicalTypeDaoImpl.getInstance();
    }

    @Override
    public PeriodicalStatusDao createPeriodicalStatusDao() {
        return JdbcPeriodicalStatusDaoImpl.getInstance();
    }
}
