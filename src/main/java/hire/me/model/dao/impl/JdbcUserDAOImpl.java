package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.UserDao;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.utility.Password;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcUserDAOImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(JdbcUserDAOImpl.class);

    final private Connection connection = getConnection();
    private static JdbcUserDAOImpl instance;

    private JdbcUserDAOImpl() {}

    public static JdbcUserDAOImpl getInstance() {
        logger.trace("");
        if (instance == null) {
            synchronized (JdbcUserDAOImpl.class) {
                if (instance == null) {
                    instance = new JdbcUserDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
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
}
