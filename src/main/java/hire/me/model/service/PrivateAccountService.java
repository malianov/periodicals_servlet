package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PrivateAccountDao;
import hire.me.model.dao.daoFactory.SubscriptionDao;
import hire.me.model.dao.daoFactory.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static hire.me.connection.ConnectionPool.getConnection;

public class PrivateAccountService {
    private static final Logger logger = LogManager.getLogger(PrivateAccountService.class);

    private DaoFactory daoFactory;
    private static PrivateAccountService instance;

    private PrivateAccountService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static PrivateAccountService getInstance() {
        if (instance == null) {
            synchronized (PrivateAccountService.class) {
                if (instance == null) {
                    instance = new PrivateAccountService();
                }
            }
        }
        return instance;
    }

    public void increaseBalance(Long subscriberId, BigDecimal additionToBalance) {
        logger.trace("inside increaseBalance");
        PrivateAccountDao dao = daoFactory.createPrivateAccountDao();

        dao.increaseBalance(subscriberId, additionToBalance);


    }

/*    public void increaseBalance(Long subscriberId, BigDecimal additionToBalance) {
        logger.trace("inside increaseBalance");
        PrivateAccountDao dao = daoFactory.createPrivateAccountDao();
        final Connection connection = getConnection();

        try {
            logger.trace("inside increaseBalance - try");
            connection.setAutoCommit(false);

            BigDecimal actualSubscriberBalance = getSubscriberBalance(connection, subscriberId, dao);
            BigDecimal newActualSubscriberBalance = actualSubscriberBalance.add(additionToBalance);
            increaseBalance(connection, subscriberId, newActualSubscriberBalance, dao);

            connection.commit();
            connection.setAutoCommit(true);
            logger.trace("after commit");

        } catch (SQLException e) {
            logger.trace("inside Exception");
            try {
                logger.trace("connection rollback");
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
    }*/

    public BigDecimal getSubscriberBalance(Long subscriberId) {


        BigDecimal bg = daoFactory.createPrivateAccountDao()
                .getSubscriberBalance(subscriberId);

        logger.trace("inside getSubscriberBalance ================================== {}", bg);
        return bg;
    }

//    public BigDecimal getSubscriberBalance(Connection connection, Long subscriberId, PrivateAccountDao dao) {
//        logger.trace("inside getSubscriberBalance");
//        return dao.getSubscriberBalance(connection, subscriberId);
//    }

//    private void increaseBalance(Connection connection, Long subscriberId, BigDecimal newActualSubscriberBalance, PrivateAccountDao dao) {
//        logger.trace("inside increaseBalance");
//        dao.increaseBalance(connection, subscriberId, newActualSubscriberBalance);
//    }
}
