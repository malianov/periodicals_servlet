package hire.me.model.dao.daoFactory;

import java.math.BigDecimal;
import java.sql.Connection;

public interface SubscriptionDao {

    BigDecimal getPeriodicPricePerItem(Connection connection, Integer subscribedPeriodicId);
    BigDecimal getSubscriberBalance(Connection connection, String subscriberLogin);
    void isSubscriptionSuccessful(String subscriberLogin, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems);
    void setSubscriberBalanceToNew(Connection connection, String subscriberLogin, BigDecimal newSubscriberBalance);
    void addSubscriptionRecord(Connection connection, String subscriberLogin, Integer subscribedPeriodicId, String item, String subscriptionYear, BigDecimal actualPeriodicPricePerItem);
}
