package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.SubscriptionDao;
import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.entity.account.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import static hire.me.connection.ConnectionPool.getConnection;

public class SubscriptionService {
    private static final Logger logger = LogManager.getLogger(SubscriptionService.class);

    private DaoFactory daoFactory;
    private static SubscriptionService instance;

    private SubscriptionService() {
        logger.trace("constructor");
        daoFactory = DaoFactory.getInstance();
    }

    public static SubscriptionService getInstance() {
        if (instance == null) {
            synchronized (SubscriptionService.class) {
                if (instance == null) {
                    instance = new SubscriptionService();
                }
            }
        }
        return instance;
    }

    public boolean isSubscriptionSuccessful(String subscriberLogin, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems) {
        logger.trace("isSubscriptionSuccessful");

        SubscriptionDao dao = daoFactory.createSubscriptionDao();
        final Connection connection = getConnection();

        int quantityOfItems = selectedPeriodicItems.length;

        try {
            logger.trace("isSubscriptionSuccessful_inside try");
            connection.setAutoCommit(false);

            BigDecimal actualPeriodicPricePerItem = getPeriodicPricePerItem(connection, subscribedPeriodicId, dao);

            BigDecimal actualSubscriberBalance = getSubscriberBalance(connection, subscriberLogin, dao);

            logger.trace("actualPeriodicPricePerItem - {}", actualPeriodicPricePerItem);
            logger.trace("actualSubscriberBalance - {}", actualSubscriberBalance);
            if (isSubscriberBalanceIsEnoughForSubscription(actualSubscriberBalance, actualPeriodicPricePerItem, quantityOfItems)) {
                logger.trace("isSubscriptionSuccessful_inside try _ if");

                BigDecimal newSubscriberBalance = newSubscriberBalance(actualSubscriberBalance, actualPeriodicPricePerItem, quantityOfItems);
                logger.trace("newSubscriberBalance = {}", newSubscriberBalance);
                setSubscriberBalanceToNew(connection, subscriberLogin, newSubscriberBalance, dao);

                logger.trace("going to add record");

                addSubscriptionRecord(connection, subscriberLogin, subscribedPeriodicId, selectedPeriodicItems, subscriptionYear, actualPeriodicPricePerItem, dao);

                connection.commit();
                connection.setAutoCommit(true);
            } else {
                logger.trace("inside ELSE");
                return false;
            }

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


//        if(isSubscriberBalanceIsEnoughForSubscription(currentSubscriberBalance, periodicPricePerItem, quantityOfItems)))

//        dao.isSubscriptionSuccessful(subscriberLogin, subscribedPeriodicId, subscriptionYear, selectedPeriodicItems);

        return true;
    }

    private void addSubscriptionRecord(Connection connection, String subscriberLogin, Integer subscribedPeriodicId, String[] selectedPeriodicItems, String subscriptionYear, BigDecimal actualPeriodicPricePerItem, SubscriptionDao dao) {
        for(String item: selectedPeriodicItems) {
            dao.addSubscriptionRecord(connection, subscriberLogin, subscribedPeriodicId, item, subscriptionYear, actualPeriodicPricePerItem);
        }
    }


    private BigDecimal getPeriodicPricePerItem(Connection connection, Integer subscribedPeriodicId, SubscriptionDao dao) {
        logger.trace("getPeriodicPricePerItem INTERNAL INSODE SERVICE");
        return dao.getPeriodicPricePerItem(connection, subscribedPeriodicId);
    }

    private BigDecimal getSubscriberBalance(Connection connection, String subscriberLogin, SubscriptionDao dao) {
        return dao.getSubscriberBalance(connection, subscriberLogin);
    }

    private boolean isSubscriberBalanceIsEnoughForSubscription(BigDecimal actualSubscriberBalance, BigDecimal periodicPricePerItem, int quantityOfItems) {
        return actualSubscriberBalance.compareTo(periodicPricePerItem.multiply(new BigDecimal(quantityOfItems))) >= 0 ? true : false;
    }

    private BigDecimal newSubscriberBalance(BigDecimal actualSubscriberBalance, BigDecimal periodicPricePerItem, int quantityOfItems) {
        return actualSubscriberBalance.subtract(periodicPricePerItem.multiply(new BigDecimal(quantityOfItems)));
    }

    private void setSubscriberBalanceToNew(Connection connection, String subscriberLogin, BigDecimal newSubscriberBalance, SubscriptionDao dao) {
        dao.setSubscriberBalanceToNew(connection, subscriberLogin, newSubscriberBalance);
    }
}
