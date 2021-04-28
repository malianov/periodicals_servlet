package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.SubscriberDao;

public class JdbcDaoFactory extends DaoFactory {
    @Override
    public SubscriberDao createSubscriberDao() {
        return JdbcSubscriberDAOImpl.getInstance();
    }
}
