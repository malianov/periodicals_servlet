package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.SubscriptionDao;
import hire.me.model.dao.impl.queries.SubscriptionSQL;
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
import java.util.Optional;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcSubscriptionDaoImpl implements SubscriptionDao {
    private static final Logger logger = LogManager.getLogger(JdbcSubscriptionDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcSubscriptionDaoImpl instance;

    private JdbcSubscriptionDaoImpl() {
    }

    public static JdbcSubscriptionDaoImpl getInstance() {
        if (instance == null) {
            synchronized (JdbcSubscriptionDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcSubscriptionDaoImpl();
                }
            }
        }
        return instance;
    }

    public boolean isSubscriptionSuccessful(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems, String subscriberAddress) {
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
                return true;
            }
        } catch (Exception e) {
            logger.error("Error with DAO: {}", e);
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.error("Error with DAO: {}", exception);
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }

    private void insertToDatabaseRowsAsSubscriptions(Long subscriberId, Integer subscribedPeriodicId, String subscriptionYear, String[] selectedPeriodicItems, String subscriberAddress, BigDecimal actualPeriodicPricePerItem) throws SQLException {
        for (String item : selectedPeriodicItems) {
            PreparedStatement ps = connection.prepareStatement(SubscriptionSQL.INSERT.getQUERY());
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
        PreparedStatement changeSubscriberBalanceStmt = connection.prepareStatement(SubscriptionSQL.UPDATE_BALANCE.getQUERY());
        changeSubscriberBalanceStmt.setBigDecimal(1, newSubscriberBalance);
        changeSubscriberBalanceStmt.setLong(2, subscriberId);
        changeSubscriberBalanceStmt.execute();
    }

    private boolean isSubscriberHasEnoughMoney(BigDecimal actualPeriodicPricePerItem, BigDecimal actualSubscriberBalance, int quantityOfItems) {
        return actualSubscriberBalance.compareTo(actualPeriodicPricePerItem.multiply(new BigDecimal(quantityOfItems))) >= 0 ? true : false;
    }

    private BigDecimal getActualSubscriberBalance(Long subscriberId, BigDecimal actualSubscriberBalance) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(SubscriptionSQL.READ_ACTUAL_BALANCE.getQUERY());
        ps.setLong(1, subscriberId);
        final ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            actualSubscriberBalance = rs.getBigDecimal("balance");
        }
        return actualSubscriberBalance;
    }

    public BigDecimal getActualSubscriberBalance(Long subscriberId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SubscriptionSQL.READ_ACTUAL_BALANCE_WITH_BALANCE.getQUERY());
            ps.setLong(1, subscriberId);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new BigDecimal(0.0);
    }

    private BigDecimal getActualPeriodicPricePerItem(Integer subscribedPeriodicId, BigDecimal actualPeriodicPricePerItem) throws SQLException {
        PreparedStatement actualPriceStmt = connection.prepareStatement(SubscriptionSQL.READ_ACTUAL_PERIODIC_PRICE.getQUERY());
        actualPriceStmt.setInt(1, subscribedPeriodicId);
        final ResultSet actualPriceResultSet = actualPriceStmt.executeQuery();
        if (actualPriceResultSet.next()) {
            actualPeriodicPricePerItem = actualPriceResultSet.getBigDecimal("price_per_item");
        }
        return actualPeriodicPricePerItem;
    }

    public SubscriptionService.PaginationResult searchSubscriptionsWithPagination(int lowerBound,
                                                                                  int upperBound, String searchKey) {

        SubscriptionService.PaginationResult paginationResult = new SubscriptionService.PaginationResult();
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        List<Subscription> subscriptions = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement subscriptionsPS = connection.prepareStatement(
                     SubscriptionSQL.READ_SUBSCRIPTION.getQUERY());

            PreparedStatement countRowsPS = connection.prepareStatement(SubscriptionSQL.COUNT_ROWS.getQUERY())) {

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
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }

        paginationResult.setSubscriptionsList(new ArrayList<>(subscriptions));
        return paginationResult;
    }

    @Override
    public void create(Subscription entity) {
    }

    @Override
    public Optional<Subscription> findById(long id) {
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
