package hire.me.model.dao.daoFactory;

import hire.me.model.entity.subscription.Subscription;
import hire.me.model.service.SubscriptionService;

import java.math.BigDecimal;

public interface SubscriptionDao extends AbstractDao<Subscription> {
    boolean isSubscriptionSuccessful(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems, String subscriberAddress);

    SubscriptionService.PaginationResult searchSubscriptionsWithPagination(int lowerBound, int upperBound, String searchKey);

    BigDecimal getActualSubscriberBalance(Long subscriberId);
}
