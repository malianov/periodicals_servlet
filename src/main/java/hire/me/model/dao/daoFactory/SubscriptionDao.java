package hire.me.model.dao.daoFactory;

import hire.me.model.entity.subscription.Subscription;
import hire.me.model.service.SubscriptionService;

public interface SubscriptionDao extends AbstractDao<Subscription> {
    void isSubscriptionSuccessful(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems, String subscriberAddress);
    SubscriptionService.PaginationResult searchSubscriptionsWithPagination(int lowerBound, int upperBound, String searchKey);
}
