package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.SubscriptionDao;
import hire.me.model.dao.mapper.SubscriptionMapper;
import hire.me.model.entity.subscription.Subscription;
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

    public void isSubscriptionSuccessful(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems, String subscriberAddress) {
        logger.trace("start isSubscriptionSuccessful");
        BigDecimal actualPeriodicPricePerItem = null;
        BigDecimal actualSubscriberBalance = null;
        int quantityOfItems = selectedPeriodicItems.length;

        try {
            connection.setAutoCommit(false);

            actualPeriodicPricePerItem = getActualPeriodicPricePerItem(subscribedPeriodicId, actualPeriodicPricePerItem);
            actualSubscriberBalance = getActualSubscriberBalance(subscriberId, actualSubscriberBalance);

            if (isSubscriberHasEnoughMoney(actualPeriodicPricePerItem, actualSubscriberBalance, quantityOfItems)) {
                BigDecimal newSubscriberBalance = actualSubscriberBalance.subtract(actualPeriodicPricePerItem.multiply(new BigDecimal(quantityOfItems)));
                updateSubscribersBalance(subscriberId, newSubscriberBalance);
                insertToDatabaseRowsAsSubscriptions(subscriberId, subscribedPeriodicId, subscriptionYear, selectedPeriodicItems, subscriberAddress, actualPeriodicPricePerItem);
                connection.commit();
            }
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void insertToDatabaseRowsAsSubscriptions(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems, String subscriberAddress, BigDecimal actualPeriodicPricePerItem) throws SQLException {
        for (String item : selectedPeriodicItems) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO subscriptions (subscriber_id, periodic_id, periodic_item, periodic_year, item_price, subscriber_address, subscription_date, subscription_time) VALUES ((?), (?), (?), (?), (?), (?), now(), now());");
            ps.setLong(1, subscriberId);
            ps.setLong(2, subscribedPeriodicId);
            ps.setString(3, item);
            ps.setString(4, subscriptionYear);
            ps.setBigDecimal(5, actualPeriodicPricePerItem);
            ps.setString(6, subscriberAddress);
            ps.execute();
        }
    }

    private void updateSubscribersBalance(Long subscriberId, BigDecimal newSubscriberBalance) throws SQLException {
        PreparedStatement changeSubscriberBalanceStmt = connection.prepareStatement("UPDATE users SET balance=(?) WHERE id=(?);");
        changeSubscriberBalanceStmt.setBigDecimal(1, newSubscriberBalance);
        changeSubscriberBalanceStmt.setLong(2, subscriberId);
        changeSubscriberBalanceStmt.execute();
    }

    private boolean isSubscriberHasEnoughMoney(BigDecimal actualPeriodicPricePerItem, BigDecimal actualSubscriberBalance, int quantityOfItems) {
        return actualSubscriberBalance.compareTo(actualPeriodicPricePerItem.multiply(new BigDecimal(quantityOfItems))) >= 0 ? true : false;
    }

    private BigDecimal getActualSubscriberBalance(Long subscriberId, BigDecimal actualSubscriberBalance) throws SQLException {
        PreparedStatement actualSubscriberBalanceStatement = connection.prepareStatement("SELECT balance FROM users where id=(?);");
        actualSubscriberBalanceStatement.setLong(1, subscriberId);
        final ResultSet actualSubscriberBalanceRs = actualSubscriberBalanceStatement.executeQuery();
        if (actualSubscriberBalanceRs.next()) {
            actualSubscriberBalance = actualSubscriberBalanceRs.getBigDecimal("balance");
        }
        return actualSubscriberBalance;
    }

    private BigDecimal getActualPeriodicPricePerItem(Integer subscribedPeriodicId, BigDecimal actualPeriodicPricePerItem) throws SQLException {
        PreparedStatement actualPriceStmt = connection.prepareStatement("SELECT price_per_item FROM periodical where id=(?);");
        actualPriceStmt.setInt(1, subscribedPeriodicId);
        final ResultSet actualPriceResultSet = actualPriceStmt.executeQuery();
        if (actualPriceResultSet.next()) {
            actualPeriodicPricePerItem = actualPriceResultSet.getBigDecimal("price_per_item");
        }
        return actualPeriodicPricePerItem;
    }

    public SubscriptionService.PaginationResult searchSubscriptionsWithPagination(int lowerBound,
                                                                                  int upperBound, String searchKey) {
        logger.info("Search subscription by pagination with lowerBound = {}, upperBound = {} and searchKey = {}", lowerBound, upperBound, searchKey);

        SubscriptionService.PaginationResult paginationResult = new SubscriptionService.PaginationResult();
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        List<Subscription> subscriptions = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement subscriptionsPS = connection.prepareStatement(
                     "SELECT s.id, s.subscriber_id, s.periodic_id, p.id_periodic, s.periodic_item, s.subscription_date, s.subscriber_address, s.item_price, s.periodic_year FROM subscriptions s " +
                     "JOIN periodical p ON " +
                     "s.periodic_id = p.id_periodic " +
                     "WHERE s.subscriber_id LIKE ? ORDER BY s.id LIMIT ?, ?;");

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
