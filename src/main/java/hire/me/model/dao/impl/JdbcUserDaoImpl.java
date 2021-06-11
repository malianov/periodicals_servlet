package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.dao.impl.queries.UserSQL;
import hire.me.model.dao.mapper.UserMapper;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
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
import java.util.Optional;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcUserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(JdbcUserDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcUserDaoImpl instance;

    private JdbcUserDaoImpl() {
    }

    public static JdbcUserDaoImpl getInstance() {
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
        try (PreparedStatement ps = connection.prepareStatement(UserSQL.CREATE.getQUERY())) {

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
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
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
        try (PreparedStatement ps = connection.prepareStatement(UserSQL.CHECK_EXISTENCE.getQUERY())) {
            ps.setString(1, login);
            ps.setString(2, password);

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean emailIsAlreadyUsed(String email) {
        try (PreparedStatement ps = connection.prepareStatement(UserSQL.CHECK_EMAIL_USING.getQUERY())) {
            ps.setString(1, email);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean loginIsAlreadyUsed(String login) {
        try (PreparedStatement ps = connection.prepareStatement(UserSQL.CHECK_LOGIN_USED.getQUERY())) {
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isLoginExist(String login) {
        try (PreparedStatement ps = connection.prepareStatement(UserSQL.CHECK_LOGIN_EXISTENCE.getQUERY())) {
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isPasswordCorrectForLogin(String login, String passwordFromWeb) {
        try (PreparedStatement ps = connection.prepareStatement(UserSQL.READ_PASSWORD_FOR_LOGIN.getQUERY())) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String passwordFromDB = rs.getString("password");
                return Password.validatePassword(passwordFromWeb, passwordFromDB);
            }
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserRole getRoleByLogin(String login) {
        UserRole role = UserRole.GUEST;

        try (PreparedStatement ps = connection.prepareStatement(UserSQL.READ_ROLE_FOR_LOGIN.getQUERY())) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                role = UserRole.valueOf(rs.getString("user_role").toUpperCase());
                return role;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public Long getIdByLogin(String login) {

        try (PreparedStatement ps = connection.prepareStatement(UserSQL.READ_ID_BY_LOGIN.getQUERY())) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("id");
                return id;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return null;
    }

    public UserService.PaginationResult searchSubscribersWithPagination(int lowerBound, int upperBound, String searchKey) {

        UserService.PaginationResult paginationResult = new UserService.PaginationResult();
        UserMapper userMapper = new UserMapper();
        List<User> subscribers = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement subscribersPS = connection.prepareStatement(UserSQL.FILTER_SUBSCRIBERS.getQUERY());
             PreparedStatement countRowsPS = connection.prepareStatement(UserSQL.COUNT_FILTERED_SUBSCRIBERS.getQUERY());) {

            subscribersPS.setString(1, "%" + searchKey + "%");
            subscribersPS.setString(2, "%" + searchKey + "%");
            subscribersPS.setString(3, "%" + searchKey + "%");
            subscribersPS.setString(4, "%" + searchKey + "%");
            subscribersPS.setInt(5, lowerBound);
            subscribersPS.setInt(6, upperBound);

            ResultSet rs = subscribersPS.executeQuery();
            while (rs.next()) {
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
                paginationResult.setNuOfRows(rs.getInt(1));
            }
            rs.close();

        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }

        paginationResult.setSubscribersList(new ArrayList<>(subscribers));
        return paginationResult;
    }

    @Override
    public void changeUserStatus(String login, String status) {
        try (PreparedStatement ps = connection.prepareStatement(UserSQL.UPDATE_USER_STATUS.getQUERY())) {
            ps.setString(2, login);
            ps.setString(1, status.equals("ACTIVE") ? "ACTIVE" : "BLOCKED");

            ps.execute();

        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
    }

    @Override
    public BigDecimal getSubscriberBalanceByLogin(String login) {
        BigDecimal subscriberBalance = new BigDecimal(0.0);

        try (PreparedStatement ps = connection.prepareStatement(UserSQL.GET_SUBSCRIBER_BALANCE_BY_ID.getQUERY())) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                subscriberBalance = new BigDecimal(rs.getString("balance"));
                return subscriberBalance;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return subscriberBalance;
    }

    @Override
    public UserStatus checkSubscriberStatusByLogin(String login) {

        UserStatus userStatus = UserStatus.UNKNOWN;

        try (PreparedStatement ps = connection.prepareStatement(UserSQL.READ_ACCOUNT_STATUS_BY_LOGIN.getQUERY())) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userStatus = UserStatus.valueOf(rs.getString("account_status").toUpperCase());
                return userStatus;
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return userStatus;
    }

    @Override
    public Optional<User> findById(long id) {

        UserMapper userMapper = new UserMapper();
        User user;

        try (PreparedStatement ps = connection.prepareStatement(UserSQL.READ_USER_BY_ID.getQUERY())) {
            ps.setLong(1, id);
            final ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = userMapper.extractFromResultSet(rs);
                return Optional.of(user);
            }
            rs.close();

        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return Optional.empty();
    }
}