package hire.me.model.dao.daoFactory;

import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public interface UserDao extends AbstractDao<User> {
    static final Logger logger = LogManager.getLogger(UserDao.class);

    boolean isUserExist(final String email, final String password);
    boolean emailIsAlreadyUsed(final String email);
    boolean loginIsAlreadyUsed(final String login);
    boolean isLoginExist(String login);
    boolean isPasswordCorrectForLogin(String login, String password);

    UserRole getRoleByLogin(String login);
    UserService.PaginationResult searchSubscribersWithPagination(int lowerBound, int upperBound, String searchKey);

    void changeUserStatus(String login, String status);

    BigDecimal getSubscriberBalanceByLogin(String login);

//    void changeStatusToBlocked(String login);
}


