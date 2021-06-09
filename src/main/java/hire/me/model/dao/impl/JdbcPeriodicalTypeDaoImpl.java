package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.PeriodicalTypeDao;
import hire.me.model.dao.mapper.PeriodicalTypeMapper;
import hire.me.model.dao.mapper.ThemeMapper;
import hire.me.model.entity.periodical.PeriodicalType;
import hire.me.model.entity.periodical.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcPeriodicalTypeDaoImpl implements PeriodicalTypeDao {

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
            PreparedStatement ps = connection.prepareStatement("SELECT id, type_en FROM types where id=(?)");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return Optional.of(periodicalTypeMapper.extractFromResultSet(rs));
            }
            rs.close();

        } catch (SQLException exception) {
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
