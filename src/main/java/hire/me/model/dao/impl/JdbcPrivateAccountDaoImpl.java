package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.PrivateAccountDao;
import hire.me.model.entity.account.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcPrivateAccountDaoImpl implements PrivateAccountDao {
    private static final Logger logger = LogManager.getLogger(JdbcPrivateAccountDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcPrivateAccountDaoImpl instance;

    private JdbcPrivateAccountDaoImpl() {
    }

    public static JdbcPrivateAccountDaoImpl getInstance() {

        if (instance == null) {
            synchronized (JdbcPrivateAccountDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcPrivateAccountDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void increaseBalance(Connection serviceConnection, Long subscriberId, BigDecimal additionToBalance) {
        // UPDATE users SET balance = (?) WHERE (id = (?));
        logger.info("increaseBalance for subscriberID = {}, additionToBalance = {}", subscriberId, additionToBalance);
        try (PreparedStatement ps = serviceConnection.prepareStatement("UPDATE users SET balance = (?) WHERE (id = (?))")) {
            ps.setBigDecimal(1, additionToBalance);
            ps.setLong(2, subscriberId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BigDecimal getSubscriberBalance(Connection serviceConnection, Long subscriberId) {
        logger.info("getSubscriberBalance");
        try (PreparedStatement ps = serviceConnection.prepareStatement("SELECT balance FROM users where id=(?);")) {

            ps.setLong(1, subscriberId);

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0.0);     // Put here Exception
    }

    @Override
    public BigDecimal getSubscriberBalance(Long subscriberId) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT balance FROM users where id=(?);")) {

            ps.setLong(1, subscriberId);

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0.0);
    }
}
