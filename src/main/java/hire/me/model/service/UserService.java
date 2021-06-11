package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private DaoFactory daoFactory;
    private static UserService instance;

    private UserService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    public void registerUser(User user) throws Exception {
        UserDao userDao = daoFactory.createUserDao();

        if (userDao.emailIsAlreadyUsed(user.getPerson().getEmail())) {
            throw new Exception("Failed registering already existing user email " +
                    user.getPerson().getEmail());
        }

        if (userDao.loginIsAlreadyUsed(user.getLogin())) {
            throw new Exception("Failed registering already existing user login " +
                    user.getLogin());
        }

        userDao.create(user);
    }

    public User getUserByEmail(String email) {
        List<User> users = getAllUsers();

        return users.stream()
                .filter(user -> email.equals(user.getPerson().getEmail()))
                .findAny()
                .orElse(null);
    }

    public User getUserByLogin(String login) {
        List<User> users = getAllUsers();

        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findAny()
                .orElse(null);
    }

    public boolean isExistingUser(String login, String password) {
        return daoFactory.createUserDao().isUserExist(login, password);
    }

    public Optional<User> getUserById(long id) {
        return daoFactory.createUserDao().findById(id);
    }

    public List<User> getAllUsers() {
        return daoFactory.createUserDao().findAll();
    }

    public boolean isLoginExist(String login) {
        return daoFactory.createUserDao().isLoginExist(login);
    }

    public boolean isPasswordCorrectForLogin(String login, String password, UserRole role) {
        return daoFactory.createUserDao().isPasswordCorrectForLogin(login, password);
    }

    public UserRole getRoleByLogin(String login) {
        return daoFactory.createUserDao().getRoleByLogin(login);
    }

    public Long getIdByLogin(String login) {
        return daoFactory.createUserDao().getIdByLogin(login);
    }

    public void changeUserStatus(String login, String status) {
        daoFactory.createUserDao().changeUserStatus(login, status);
    }

    public UserService.PaginationResult getSearchSubscribersWithPagination(int lowerBound, int upperBound, String searchKey) {
        return daoFactory.createUserDao().searchSubscribersWithPagination(lowerBound, upperBound, searchKey);
    }

    public BigDecimal getSubscriberBalanceByLogin(String login) {
        return daoFactory.createUserDao().getSubscriberBalanceByLogin(login);
    }

    public boolean isLoginBlocked(String login) {
        if (daoFactory.createUserDao().checkSubscriberStatusByLogin(login).equals(UserStatus.BLOCKED)) {
            return true;
        }
        return false;
    }

    public static class PaginationResult {
        private int nuOfRows;
        private List<User> subscriberList;

        public int getNuOfRows() {
            return nuOfRows;
        }

        public void setNuOfRows(int nuOfRows) {
            this.nuOfRows = nuOfRows;
        }

        public List<User> getSubscribersList() {
            return subscriberList;
        }

        public void setSubscribersList(List<User> resultList) {
            this.subscriberList = resultList;
        }
    }
}
