package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.entity.periodical.Periodical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

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
        logger.trace("register user");
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
        UserDao dao = daoFactory.createUserDao();
        return dao.isUserExist(login, password);
    }

    public User getUserById(long id) {
        UserDao dao = daoFactory.createUserDao();
        return dao.findById(id);
    }

    public List<User> getAllUsers() {
        UserDao dao = daoFactory.createUserDao();
        return dao.findAll();
    }

    public boolean isLoginExist(String login) {
        UserDao dao = daoFactory.createUserDao();
        return dao.isLoginExist(login);
    }

    public boolean isPasswordCorrectForLogin(String login, String password, UserRole role) {
        UserDao dao = daoFactory.createUserDao();
        return dao.isPasswordCorrectForLogin(login, password);
    }

    public UserRole getRoleByLogin(String login) {
        logger.info("Check the role by login");
        UserDao dao = daoFactory.createUserDao();
        return dao.getRoleByLogin(login);
    }

    public void changeUserStatus(String login, String status) {
        UserDao dao = daoFactory.createUserDao();
        dao.changeUserStatus(login, status);
    }

    public UserService.PaginationResult getSearchSubscribersWithPagination(int lowerBound, int upperBound, String searchKey) {
        return daoFactory.createUserDao().searchSubscribersWithPagination(lowerBound, upperBound, searchKey);
    }

    public BigDecimal getPrivateAccountByLogin(String login) {
        UserDao dao = daoFactory.createUserDao();
        return dao.getSubscriberBalanceByLogin(login);
    }

    public static class PaginationResult {
        private int nuOfRows;
        private List<User> subscriberList;

        public int getNuOfRows() {
            return nuOfRows;
        }

        public void setNuOfRows(int nuOfRows) {
            logger.trace("setNuOfRows request, nuOfRows = {}", nuOfRows);
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
