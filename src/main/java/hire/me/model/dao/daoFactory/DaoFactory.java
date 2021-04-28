package hire.me.model.dao.daoFactory;

import hire.me.model.dao.impl.JdbcDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract SubscriberDao createSubscriberDao();

    public static DaoFactory getInstance() {
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
