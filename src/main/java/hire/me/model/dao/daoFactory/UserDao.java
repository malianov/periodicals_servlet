package hire.me.model.dao.daoFactory;

import hire.me.model.entity.account.Account;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface UserDao extends AbstractDao<User> {
    static final Logger logger = LogManager.getLogger(UserDao.class);

    boolean isUserExist(final String email, final String password);
    boolean emailIsAlreadyUsed(final String email);
    boolean loginIsAlreadyUsed(final String login);
    boolean isLoginExist(String login);
    boolean isPasswordCorrectForLogin(String login, String password);

    UserRole getRoleByLogin(String login);
}


