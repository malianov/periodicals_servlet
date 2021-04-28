package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.SubscriberDao;
import hire.me.model.entity.Subscriber;

import java.util.List;

public class SubscriberService {

    private DaoFactory daoFactory;
    private static SubscriberService instance;

    private SubscriberService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static SubscriberService getInstance() {
        if (instance == null) {
            synchronized (SubscriberService.class) {
                if (instance == null) {
                    instance = new SubscriberService();
                }
            }
        }
        return instance;
    }

    public void registerSubscriberAccount(Subscriber subscriber) throws Exception {
        SubscriberDao subscriberDao = daoFactory.createSubscriberDao();

        if (subscriberDao.emailIsAlreadyUsed(subscriber.getPerson().getEmail())) {
            throw new Exception("Failed registering already existing subscriber email " +
                    subscriber.getPerson().getEmail());
        }

        subscriberDao.create(subscriber);
    }

    public Subscriber getSubscriberByEmail(String email) {

        List<Subscriber> subscribers = getAllSubscribers();

        return subscribers.stream()
                .filter(subscriber -> email.equals(subscriber.getPerson().getEmail()))
                .findAny()
                .orElse(null);
    }

    public boolean isExistingSubscriber(String login, String password) {
        SubscriberDao dao = daoFactory.createSubscriberDao();
        return dao.isSubscriberExist(login, password);
    }

    public Subscriber getSubscriberById(long id) {
        SubscriberDao dao = daoFactory.createSubscriberDao();
        return dao.findById(id);
    }

    public List<Subscriber> getAllSubscribers() {
        SubscriberDao dao = daoFactory.createSubscriberDao();
        return dao.findAll();
    }

    public boolean isLoginExist(String login) {
        SubscriberDao dao = daoFactory.createSubscriberDao();
        return dao.isLoginExist(login);
    }

    public boolean isPasswordCorrectForLogin(String login, String password) {
        SubscriberDao dao = daoFactory.createSubscriberDao();
        return dao.isPasswordCorrectForLogin(login, password);
    }
}
