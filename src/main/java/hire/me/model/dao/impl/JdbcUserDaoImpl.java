package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.dao.mapper.PeriodicalMapper;
import hire.me.model.dao.mapper.UserMapper;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.UserService;
import hire.me.utility.Password;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcUserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(JdbcUserDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcUserDaoImpl instance;

    private JdbcUserDaoImpl() {
    }

    public static JdbcUserDaoImpl getInstance() {
        logger.trace("");
        if (instance == null) {
            synchronized (JdbcUserDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcUserDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(User user) {
        logger.trace("user => {}", user);
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO users " +
                "(login, first_name, surname, email, password, account_status, user_role, balance) " +
                "VALUES ((?),(?),(?),(?),(?),(?),(?),(?))")) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPerson().getName());
            ps.setString(3, user.getPerson().getSurname());
            ps.setString(4, user.getPerson().getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getUserStatus().name());
            ps.setString(7, UserRole.SUBSCRIBER.name());
            ps.setString(8, String.valueOf(user.getSubscriberBalance()));

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User user) {
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public void close() {
    }


    @Override
    public boolean isUserExist(String login, String password) {
        logger.trace("login => {}, password => {}", login, password);
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where login=(?) and password=(?);")) {

            ps.setString(1, login);
            ps.setString(2, password);

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean emailIsAlreadyUsed(String email) {
        logger.trace("email => {}", email);
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where email=(?);")) {
            ps.setString(1, email);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Caught SQLException exception" + e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean loginIsAlreadyUsed(String login) {
        logger.trace("login => {}", login);
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where login=(?);")) {
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Caught SQLException exception" + e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isLoginExist(String login) {
        logger.trace("login => {}", login);
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where login=(?);")) {
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.trace("login {} is exists", login);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.trace("login {} does not exists", login);
        return false;
    }

    @Override
    public boolean isPasswordCorrectForLogin(String login, String passwordFromWeb) {
        logger.trace("login => {}, passwordFromWeb => {}", login, passwordFromWeb);
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where login=(?);")) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String passwordFromDB = rs.getString("password");
                return Password.validatePassword(passwordFromWeb, passwordFromDB);
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserRole getRoleByLogin(String login) {
        logger.trace("login => {}", login);
        UserRole role = UserRole.GUEST;

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where login=(?);")) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role = UserRole.valueOf(rs.getString("user_role"));
                logger.trace("Inside getRoleByLogin, role exists and equal to {}", role);
                return role;
            }
        } catch (SQLException e) {
            logger.trace("Inside getRoleByLogin: Exception {}", e);
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public Long getIdByLogin(String login) {
        logger.trace("login => {}", login);

        try (PreparedStatement ps = connection.prepareStatement("SELECT id FROM users where login=(?);")) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("id");
                logger.trace("Inside getIdByLogin, role exists and equal to {}", id);
                return id;
            }
        } catch (SQLException e) {
            logger.trace("Inside getIdByLogin: Exception {}", e);
            e.printStackTrace();
        }
        return null;
    }

    public UserService.PaginationResult searchSubscribersWithPagination(int lowerBound, int upperBound, String searchKey) {
        logger.info("Search users by pagination with lowerBound = {}, upperBound = {} and searchKey = {}", lowerBound, upperBound, searchKey);

        UserService.PaginationResult paginationResult = new UserService.PaginationResult();
        UserMapper userMapper = new UserMapper();
        List<User> subscribers = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement subscribersPS = connection.prepareStatement("SELECT users.id, users.login, users.first_name, users.surname, users.email," +
                     "users.account_status, users.balance FROM users WHERE users.user_role='SUBSCRIBER' AND (users.login LIKE ? OR users.first_name LIKE ? OR users.surname LIKE ? OR users.email LIKE ?) LIMIT ?, ?;");
             PreparedStatement countRowsPS = connection.prepareStatement("SELECT COUNT(*) " +
                     "FROM users WHERE users.user_role='SUBSCRIBER' AND (users.login LIKE ? OR users.first_name LIKE ? OR users.surname LIKE ? OR users.email LIKE ?);");) {

            logger.trace("try to get queryList");

            subscribersPS.setString(1, "%" + searchKey + "%");
            subscribersPS.setString(2, "%" + searchKey + "%");
            subscribersPS.setString(3, "%" + searchKey + "%");
            subscribersPS.setString(4, "%" + searchKey + "%");
            subscribersPS.setInt(5, lowerBound);
            subscribersPS.setInt(6, upperBound);

            ResultSet rs = subscribersPS.executeQuery();
            while (rs.next()) {
                logger.info("We have smth inside rs_1");
                User subscriber = userMapper.extractFromResultSet(rs);
                subscribers.add(subscriber);
            }
            rs.close();

            countRowsPS.setString(1, "%" + searchKey + "%");
            countRowsPS.setString(2, "%" + searchKey + "%");
            countRowsPS.setString(3, "%" + searchKey + "%");
            countRowsPS.setString(4, "%" + searchKey + "%");
            rs = countRowsPS.executeQuery();

            if (rs.next()) {
                logger.info("We have smth inside rs_2");
                paginationResult.setNuOfRows(rs.getInt(1));
            }
            rs.close();

        } catch (SQLException e) {
            logger.trace("Catched SQLException {}", e);
            e.printStackTrace();
        }

        paginationResult.setSubscribersList(new ArrayList<>(subscribers));
        return paginationResult;
    }

    @Override
    public void changeUserStatus(String login, String status) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE users SET account_status = (?) WHERE `login` = (?);")) {
            ps.setString(2, login);
            ps.setString(1, status.equals("ACTIVE") ? "ACTIVE" : "BLOCKED");

            ps.execute();

        } catch (SQLException e) {
            logger.trace("Caught SQLException exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public BigDecimal getSubscriberBalanceByLogin(String login) {
        logger.trace("login => {}", login);
        BigDecimal subscriberBalance = new BigDecimal(0.0);

        try (PreparedStatement ps = connection.prepareStatement("SELECT users.balance FROM users where login=(?);")) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                subscriberBalance = new BigDecimal(rs.getString("balance"));
                logger.trace("Inside getPrivateAccountByLogin, personalAccountis equal to {}", subscriberBalance);
                return subscriberBalance;
            }
        } catch (SQLException e) {
            logger.trace("Inside getSubscriberBalanceByLogin: Exception {}", e);
            e.printStackTrace();
        }
        return subscriberBalance;
    }
}

    /*Override
    public void create(User user) {
        logger.trace("user => {}", user);
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO users " +
                "(login, first_name, surname, email, password, account_status, user_role) " +
                "VALUES ((?),(?),(?),(?),(?),(?),(?))")) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPerson().getName());
            ps.setString(3, user.getPerson().getSurname());
            ps.setString(4, user.getPerson().getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getUserStatus().name());
            ps.setString(7, UserRole.SUBSCRIBER.name());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/