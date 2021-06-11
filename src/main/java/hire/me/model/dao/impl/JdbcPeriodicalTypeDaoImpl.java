package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.PeriodicalTypeDao;
import hire.me.model.dao.impl.queries.PeriodicalTypeSQL;
import hire.me.model.dao.mapper.PeriodicalTypeMapper;
import hire.me.model.entity.periodical.PeriodicalType;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static hire.me.connection.ConnectionPool.getConnection;
import static org.apache.logging.log4j.LogManager.getLogger;

public class JdbcPeriodicalTypeDaoImpl implements PeriodicalTypeDao {
    private static final Logger logger = getLogger(JdbcPeriodicalTypeDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcPeriodicalTypeDaoImpl instance;

    private JdbcPeriodicalTypeDaoImpl() {
    }

    public static JdbcPeriodicalTypeDaoImpl getInstance() {
        if (instance == null) {
            synchronized (JdbcPeriodicalTypeDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcPeriodicalTypeDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(PeriodicalType entity) {

    }

    @Override
    public Optional<PeriodicalType> findById(long id) {
        PeriodicalTypeMapper periodicalTypeMapper = new PeriodicalTypeMapper();

        try {
            PreparedStatement ps = connection.prepareStatement(PeriodicalTypeSQL.READ_BY_ID.getQUERY());
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return Optional.of(periodicalTypeMapper.extractFromResultSet(rs));
            }
            rs.close();

        } catch (SQLException exception) {
            logger.error("Error with DAO: {}", exception);
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<PeriodicalType> findAll() {
        return null;
    }

    @Override
    public void update(PeriodicalType periodicalType) {
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public void close() {
    }
}
