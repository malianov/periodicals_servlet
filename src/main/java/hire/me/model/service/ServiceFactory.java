package hire.me.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServiceFactory {
    private static final Logger logger = LogManager.getLogger(ServiceFactory.class);

    private static ServiceFactory serviceFactory;

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactory();
                }
            }
        }
        return serviceFactory;
    }

    public UserService getUserService() {
        return UserService.getInstance();
    }

    public PeriodicalService getPeriodicalService() {
        return PeriodicalService.getInstance();
    }

    public UserService getUsersService() {
        return UserService.getInstance();
    }

    public SubscriptionService getSubscriptionService() {
        return SubscriptionService.getInstance();
    }
}
