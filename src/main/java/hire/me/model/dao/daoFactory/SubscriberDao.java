package hire.me.model.dao.daoFactory;

import hire.me.model.entity.Subscriber;

import java.util.List;

public interface SubscriberDao extends AbstractDao<Subscriber> {
    boolean isSubscriberExist(final String email, final String password);
    boolean emailIsAlreadyUsed(final String email);

    boolean isLoginExist(String login);
    boolean isPasswordCorrectForLogin(String login, String password);
}


