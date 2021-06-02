package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.SubscriptionDao;
import hire.me.model.dao.mapper.PeriodicalMapper;
import hire.me.model.dao.mapper.SubscriptionMapper;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.subscription.Subscription;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.SubscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void isSubscriptionSuccessful(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems) {
        try (PreparedStatement actualPrice = connection.prepareStatement("SELECT price_per_item FROM periodical where id=(?);");
             PreparedStatement actualSubscriberBalance = connection.prepareStatement("SELECT balance FROM users where id=(?);")) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0.0);     // Put here Exception (the price per item have to be!)
    }

    public BigDecimal getSubscriberBalance(Connection serviceConnection, Long subscriberId) {
        logger.trace("getSubscriberBalance");

        try (PreparedStatement ps = serviceConnection.prepareStatement("SELECT balance FROM users where id=(?);")) {
            ps.setLong(1, subscriberId);
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

    public void setSubscriberBalanceToNew(Connection serviceConnection, Long subscriberId, BigDecimal newSubscriberBalance) {
        logger.trace("setSubscriberBalanceToNew");

        try (PreparedStatement ps = serviceConnection.prepareStatement("UPDATE users SET balance=(?) WHERE id=(?);")) {
            ps.setBigDecimal(1, newSubscriberBalance);
            ps.setLong(2, subscriberId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubscriptionRecord(Connection serviceConnection, Long subscriberId, Integer subscribedPeriodicId, String item, String subscriptionYear, BigDecimal actualPeriodicPricePerItem, String subscriberAddress) {
        logger.trace("addSubscriptionRecord");

        try (PreparedStatement ps = serviceConnection.prepareStatement("INSERT INTO subscriptions (subscriber_id, periodic_id, periodic_item, periodic_year, item_price, subscriber_address, subscription_date, subscription_time) VALUES ((?), (?), (?), (?), (?), (?), now(), now());")) {
            ps.setLong(1, subscriberId);
            ps.setLong(2, subscribedPeriodicId);
            ps.setString(3, item);
            ps.setString(4, subscriptionYear);
            ps.setBigDecimal(5, actualPeriodicPricePerItem);
            ps.setString(6, subscriberAddress);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SubscriptionService.PaginationResult searchSubscriptionsWithPagination(int lowerBound, int upperBound, String searchKey) {
        logger.info("Search subscription by pagination with lowerBound = {}, upperBound = {} and searchKey = {}", lowerBound, upperBound, searchKey);

        SubscriptionService.PaginationResult paginationResult = new SubscriptionService.PaginationResult();
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        List<Subscription> subscriptions = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement subscriptionsPS = connection.prepareStatement("" +
                     "SELECT subscriptions.id, subscriptions.subscriber_id, subscriptions.periodic_id, subscriptions.periodic_item, subscriptions.subscription_date, subscriptions.subscriber_address, subscriptions.item_price, subscriptions.periodic_year FROM subscriptions " +
                     "WHERE subscriptions.subscriber_id LIKE ? ORDER BY subscriptions.id LIMIT ?, ?");
             PreparedStatement countRowsPS = connection.prepareStatement("SELECT COUNT(*) FROM subscriptions WHERE subscriptions.subscriber_id LIKE ?;")) {

            countRowsPS.setString(1, "%" + searchKey + "%");
            subscriptionsPS.setString(1, "%" + searchKey + "%");
            subscriptionsPS.setInt(2, lowerBound);
            subscriptionsPS.setInt(3, upperBound);

            ResultSet rs = subscriptionsPS.executeQuery();

            while (rs.next()) {
                Subscription subscription = subscriptionMapper.extractFromResultSet(rs);
                subscriptions.add(subscription);
            }
            rs.close();

            countRowsPS.setString(1, "%" + searchKey + "%");
            rs = countRowsPS.executeQuery();

            if (rs.next()) {
                paginationResult.setNuOfRows(rs.getInt(1));
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        paginationResult.setSubscriptionsList(new ArrayList<>(subscriptions));
        return paginationResult;
    }

    @Override
    public void create(Subscription entity) {

    }

    @Override
    public Subscription findById(long id) {
        return null;
    }

    @Override
    public List<Subscription> findAll() {
        return null;
    }

    @Override
    public void update(Subscription subscription) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }
}
