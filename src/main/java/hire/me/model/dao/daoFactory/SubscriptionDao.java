package hire.me.model.dao.daoFactory;

import java.math.BigDecimal;
import java.sql.Connection;

public interface SubscriptionDao {

    BigDecimal getPeriodicPricePerItem(Connection connection, Integer subscribedPeriodicId);
    BigDecimal getSubscriberBalance(Connection connection, Long subscriberId);
    void isSubscriptionSuccessful(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems);
    void setSubscriberBalanceToNew(Connection connection, Long subscriberId, BigDecimal newSubscriberBalance);
    void addSubscriptionRecord(Connection connection, Long subscriberId, Integer subscribedPeriodicId, String item, String subscriptionYear, BigDecimal actualPeriodicPricePerItem, String subscriberAddress);
}
