package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.PeriodicalStatusDao;
import hire.me.model.dao.mapper.PeriodicalStatusMapper;
import hire.me.model.entity.periodical.PeriodicalStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcPeriodicalStatusDaoImpl implements PeriodicalStatusDao {

    final private Connection connection = getConnection();
    private static JdbcPeriodicalStatusDaoImpl instance;

    private JdbcPeriodicalStatusDaoImpl() {
    }

    public static JdbcPeriodicalStatusDaoImpl getInstance() {
        if (instance == null) {
            synchronized (JdbcPeriodicalStatusDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcPeriodicalStatusDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(PeriodicalStatus entity) {

    }

    @Override
    public Optional<PeriodicalStatus> findById(long id) {
        PeriodicalStatusMapper periodicalStatusMapper = new PeriodicalStatusMapper();

        Optional<PeriodicalStatus> periodicalStatus = Optional.empty();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id, status FROM periodic_status where id=(?)");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                periodicalStatus = Optional.of(periodicalStatusMapper.extractFromResultSet(rs));
            }
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return periodicalStatus;
    }

    @Override
    public List<PeriodicalStatus> findAll() {
        return null;
    }

    @Override
    public void update(PeriodicalStatus periodicalStatus) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {

    }
}
