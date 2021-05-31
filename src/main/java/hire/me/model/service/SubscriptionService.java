package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.SubscriptionDao;
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

    public boolean isSubscriptionSuccessful(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems, String subscriberAddress) {
        logger.trace("isSubscriptionSuccessful");

        SubscriptionDao dao = daoFactory.createSubscriptionDao();
        final Connection connection = getConnection();

        int quantityOfItems = selectedPeriodicItems.length;

        try {
            logger.trace("isSubscriptionSuccessful_inside try");
            connection.setAutoCommit(false);

            BigDecimal actualPeriodicPricePerItem = getPeriodicPricePerItem(connection, subscribedPeriodicId, dao);
            BigDecimal actualSubscriberBalance = getSubscriberBalance(connection, subscriberId, dao);

            if (isSubscriberBalanceIsEnoughForSubscription(actualSubscriberBalance, actualPeriodicPricePerItem, quantityOfItems)) {
                BigDecimal newSubscriberBalance = newSubscriberBalance(actualSubscriberBalance, actualPeriodicPricePerItem, quantityOfItems);
                setSubscriberBalanceToNew(connection, subscriberId, newSubscriberBalance, dao);

                addSubscriptionRecord(connection, subscriberId, subscribedPeriodicId, selectedPeriodicItems, subscriptionYear, actualPeriodicPricePerItem, subscriberAddress, dao);

                connection.commit();
                connection.setAutoCommit(true);
            } else {
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
        return true;
    }

    private void addSubscriptionRecord(Connection connection, Long subscriberId, Integer subscribedPeriodicId, String[] selectedPeriodicItems, String subscriptionYear, BigDecimal actualPeriodicPricePerItem, String subscriberAddress, SubscriptionDao dao) {
        for(String item: selectedPeriodicItems) {
            dao.addSubscriptionRecord(connection, subscriberId, subscribedPeriodicId, item, subscriptionYear, actualPeriodicPricePerItem, subscriberAddress);
        }
    }
    
    private BigDecimal getPeriodicPricePerItem(Connection connection, Integer subscribedPeriodicId, SubscriptionDao dao) {
        return dao.getPeriodicPricePerItem(connection, subscribedPeriodicId);
    }

    private BigDecimal getSubscriberBalance(Connection connection, Long subscriberId, SubscriptionDao dao) {
        return dao.getSubscriberBalance(connection, subscriberId);
    }

    private boolean isSubscriberBalanceIsEnoughForSubscription(BigDecimal actualSubscriberBalance, BigDecimal periodicPricePerItem, int quantityOfItems) {
        return actualSubscriberBalance.compareTo(periodicPricePerItem.multiply(new BigDecimal(quantityOfItems))) >= 0 ? true : false;
    }

    private BigDecimal newSubscriberBalance(BigDecimal actualSubscriberBalance, BigDecimal periodicPricePerItem, int quantityOfItems) {
        return actualSubscriberBalance.subtract(periodicPricePerItem.multiply(new BigDecimal(quantityOfItems)));
    }

    private void setSubscriberBalanceToNew(Connection connection, Long subscriberId, BigDecimal newSubscriberBalance, SubscriptionDao dao) {
        dao.setSubscriberBalanceToNew(connection, subscriberId, newSubscriberBalance);
    }
}
