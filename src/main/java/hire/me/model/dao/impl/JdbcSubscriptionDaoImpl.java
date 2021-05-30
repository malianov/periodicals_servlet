package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.SubscriptionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcSubscriptionDaoImpl implements SubscriptionDao {
    private static final Logger logger = LogManager.getLogger(JdbcSubscriptionDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcSubscriptionDaoImpl instance;

    private JdbcSubscriptionDaoImpl() {
    }

    public static JdbcSubscriptionDaoImpl getInstance() {
        logger.trace("Get the instance of JdbcSubscriptionDaoImpl");
        if (instance == null) {
            synchronized (JdbcSubscriptionDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcSubscriptionDaoImpl();
                }
            }
        }
        return instance;
    }

    public void isSubscriptionSuccessful(String subscriberLogin, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems) {
        try (PreparedStatement actualPrice = connection.prepareStatement("SELECT price_per_item FROM periodical where id=(?);");
             PreparedStatement actualSubscriberBalance = connection.prepareStatement("SELECT balance FROM users where login=(?);")
        ) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public BigDecimal getPeriodicPricePerItem(Connection serviceConnection, Integer subscribedPeriodicId) {
        try (PreparedStatement ps = serviceConnection.prepareStatement("SELECT price_per_item FROM periodical where id=(?);")) {

            ps.setInt(1, subscribedPeriodicId);

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.trace("We are inside rs.next 'getPeriodicPricePerItem'");
                return rs.getBigDecimal("price_per_item");
//                return new BigDecimal("0.0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0.0);     // Put here Exception (the price per item have to be!)
    }

    public BigDecimal getSubscriberBalance(Connection serviceConnection, String subscriberLogin) {
        logger.trace("getSubscriberBalance");
        try (PreparedStatement ps = serviceConnection.prepareStatement("SELECT balance FROM users where login=(?);")) {

            ps.setString(1, subscriberLogin);

            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.trace("We are inside rs.next 'getSubscriberBalance'");
                return rs.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0.0);     // Put here Exception
    }

    public void setSubscriberBalanceToNew(Connection serviceConnection, String subscriberLogin, BigDecimal newSubscriberBalance) {
        logger.trace("setSubscriberBalanceToNew");
        try (PreparedStatement ps = serviceConnection.prepareStatement("UPDATE users SET balance=(?) WHERE login=(?);")) {

            ps.setBigDecimal(1, newSubscriberBalance);
            ps.setString(2, subscriberLogin);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubscriptionRecord(Connection serviceConnection, String subscriberLogin, Integer subscribedPeriodicId, String item, String subscriptionYear, BigDecimal actualPeriodicPricePerItem) {
        logger.trace("addSubscriptionRecord");

        try (PreparedStatement ps = serviceConnection.prepareStatement("INSERT INTO subscriptions (subscriber_id, periodic_id, periodic_item, periodic_year, item_price, subscription_date, subscription_time) VALUES ((?), (?), (?), (?), (?), now(), now());")) {

            ps.setString(1, subscriberLogin);
            ps.setLong(2, subscribedPeriodicId);
            ps.setString(3, item);
            ps.setString(4, subscriptionYear);
            ps.setBigDecimal(5, actualPeriodicPricePerItem);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
