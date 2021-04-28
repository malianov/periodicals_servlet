package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.SubscriberDao;
import hire.me.model.entity.Subscriber;
import hire.me.utility.Password;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcSubscriberDAOImpl implements SubscriberDao {

    private Connection connection = getConnection();
    private static JdbcSubscriberDAOImpl instance;

    private JdbcSubscriberDAOImpl() {}

    public static JdbcSubscriberDAOImpl getInstance() {
        if (instance == null) {
            synchronized (JdbcSubscriberDAOImpl.class) {
                if (instance == null) {
                    instance = new JdbcSubscriberDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Subscriber subscriber) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO subscribers " +
                "(login, first_name, surname, email, password, account_status) " +
                "VALUES ((?),(?),(?),(?),(?),(?))")) {

            ps.setString(1, subscriber.getLogin());
            ps.setString(2, subscriber.getPerson().getName());
            ps.setString(3, subscriber.getPerson().getSurname());
            ps.setString(4, subscriber.getPerson().getEmail());
            ps.setString(5, subscriber.getPassword());
            ps.setString(6, subscriber.getAccountStatus().name());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Subscriber findById(long id) {
        return null;
    }

    @Override
    public List<Subscriber> findAll() {
        return null;
    }

    @Override
    public void update(Subscriber subscriber) {
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public void close() {
    }


    @Override
    public boolean isSubscriberExist(String login, String password) {

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM subscribers where login=(?) and password=(?);")) {

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
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM subscribers where email=(?);")) {
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
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM subscribers where login=(?);")) {
            ps.setString(1, login);
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
    public boolean isPasswordCorrectForLogin(String login, String passwordFromWeb) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM subscribers where login=(?);")) {
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
}
