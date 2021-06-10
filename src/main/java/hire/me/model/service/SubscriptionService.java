package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.SubscriptionDao;
import hire.me.model.entity.subscription.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

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

        return daoFactory.createSubscriptionDao()
                .isSubscriptionSuccessful(subscriberId, subscribedPeriodicId, subscriptionYear, selectedPeriodicItems, subscriberAddress);
    }

    public BigDecimal getActualSubscriberBalance(Long subscriberId) {
        return daoFactory.createSubscriptionDao().getActualSubscriberBalance(subscriberId);

    }

    public SubscriptionService.PaginationResult getSearchSubscriptionWithPagination(int lowerBound, int upperBound, String searchKey) {
        logger.trace("Inside getSearchSubscriptionWithPagination, lowerBound = {}, upperBound = {}, searchKey = {}", lowerBound, upperBound, searchKey);

        return daoFactory.createSubscriptionDao()
                .searchSubscriptionsWithPagination(lowerBound, upperBound, searchKey);
    }

    public static class PaginationResult {
        private int nuOfRows;
        private List<Subscription> subscriptionsList;

        public int getNuOfRows() {
            return nuOfRows;
        }

        public void setNuOfRows(int nuOfRows) {
            logger.trace("setNuOfRows request, nuOfRows = {}", nuOfRows);
            this.nuOfRows = nuOfRows;
        }

        public List<Subscription> getSubscriptionsList() {
            logger.trace("getSubscriptionsList");
            return subscriptionsList;
        }

        public void setSubscriptionsList(List<Subscription> resultList) {
            logger.trace("setSubscriptionsList");
            this.subscriptionsList = resultList;
        }
    }
}
