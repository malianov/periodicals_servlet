package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.PeriodicalDao;
import hire.me.model.dao.impl.queries.PeriodicalSQL;
import hire.me.model.dao.mapper.PeriodicalMapper;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalService;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import static hire.me.connection.ConnectionPool.getConnection;
import static org.apache.logging.log4j.LogManager.getLogger;

public class JdbcPeriodicalDaoImpl implements PeriodicalDao {
    private static final Logger logger = getLogger(JdbcPeriodicalDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcPeriodicalDaoImpl instance;

    private JdbcPeriodicalDaoImpl() {
    }

    public static JdbcPeriodicalDaoImpl getInstance() {
        if (instance == null) {
            synchronized (JdbcPeriodicalDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcPeriodicalDaoImpl();
                }
            }
        }
        return instance;
    }

    public PeriodicalService.PaginationResult searchPeriodicalsWithPagination(int lowerBound, int upperBound, String searchKey) {

        PeriodicalService.PaginationResult paginationResult = new PeriodicalService.PaginationResult();
        PeriodicalMapper periodicalMapper = new PeriodicalMapper();
        List<Periodical> periodicals = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement periodicalsPS = connection.prepareStatement(PeriodicalSQL.READ_PERIODICAL_WITH_SEARCH.getQUERY());
             PreparedStatement countRowsPS = connection.prepareStatement(PeriodicalSQL.COUNT_PERIODICAL_BY_TITLE.getQUERY())) {

            periodicalsPS.setString(1, "%" + searchKey + "%");
            periodicalsPS.setInt(2, lowerBound);
            periodicalsPS.setInt(3, upperBound);

            ResultSet rs = periodicalsPS.executeQuery();

            while (rs.next()) {
                Periodical periodical = periodicalMapper.extractFromResultSet(rs);
                periodicals.add(periodical);
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

        paginationResult.setPeriodicalList(new ArrayList<>(periodicals));
        return paginationResult;
    }

    @Override
    public void changePeriodicalStatus(String periodical_id, String newPeriodicStatus) {
        try (PreparedStatement ps = connection.prepareStatement(PeriodicalSQL.SET_STATUS.getQUERY())) {
            ps.setString(2, periodical_id);
            ps.setInt(1, newPeriodicStatus.equals("make_orderable") ? 1 : 2);
            ps.execute();
        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
    }

    @Override
    public void create(Periodical entity) {
    }

    @Override
    public Optional<Periodical> findById(long id) {
        PeriodicalMapper periodicalMapper = new PeriodicalMapper();
        Periodical periodical;

        try (PreparedStatement ps = connection.prepareStatement(PeriodicalSQL.READ_BY_ID.getQUERY())) {

            ps.setLong(1, id);
            final ResultSet rs = ps.executeQuery();

            while (!rs.next()) {
                return Optional.empty();
            }
            periodical = periodicalMapper.extractFromResultSet(rs);

            rs.close();
            return Optional.of(periodical);

        } catch (SQLException e) {
            logger.error("Error with DAO: {}", e);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Periodical> findAll() {
        return null;
    }

    @Override
    public void update(Periodical periodical) {

        try (PreparedStatement ps = connection.prepareStatement(PeriodicalSQL.UPDATE.getQUERY())) {
            ps.setString(1, periodical.getTitle());
            ps.setString(2, periodical.getDescription());
            ps.setString(3, periodical.getPricePerItem().toString());
            ps.setLong(4, periodical.getId());
            ps.execute();

        } catch (SQLException exception) {
            logger.error("Error with DAO: {}", exception);
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public void close() {
    }

    @Override
    public boolean isPeriodicalExist(String name) {
        return false;
    }

    @Override
    public boolean nameIsAlreadyUsed(String name) {
        return false;
    }
}
