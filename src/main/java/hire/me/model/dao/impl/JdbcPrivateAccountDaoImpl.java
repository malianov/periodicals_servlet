package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.PrivateAccountDao;
import hire.me.model.dao.impl.queries.PrivateAccountSQL;
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
    public void increaseBalance(Long subscriberId, BigDecimal additionToBalance) {
        try {
            connection.setAutoCommit(false);
            BigDecimal actualSubscriberBalance = getSubscriberBalance(subscriberId);
            BigDecimal newActualSubscriberBalance = actualSubscriberBalance.add(additionToBalance);
            updateBalance(subscriberId, newActualSubscriberBalance);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.error("Error with DAO: {}", exception);
                exception.printStackTrace();
            }
        }
    }

    private void updateBalance(Long subscriberId, BigDecimal newActualSubscriberBalance) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(PrivateAccountSQL.SET_BALANCE.getQUERY());
        ps.setBigDecimal(1, newActualSubscriberBalance);
        ps.setLong(2, subscriberId);
        ps.execute();
    }

    @Override
    public BigDecimal getSubscriberBalance(Long subscriberId) {
        try (PreparedStatement ps = connection.prepareStatement(PrivateAccountSQL.READ_BALANCE.getQUERY())) {
            ps.setLong(1, subscriberId);
            final ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return new BigDecimal(0.0);
    }
}
