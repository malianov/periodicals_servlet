package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.PeriodicalDao;
import hire.me.model.dao.mapper.PeriodicalMapper;
import hire.me.model.dao.mapper.UserMapper;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalService;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hire.me.connection.ConnectionPool.getConnection;
import static org.apache.logging.log4j.LogManager.getLogger;

public class JdbcPeriodicalDaoImpl implements PeriodicalDao {
    private static final Logger logger = getLogger(JdbcPeriodicalDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcPeriodicalDaoImpl instance;

    private JdbcPeriodicalDaoImpl() {
    }

    public static JdbcPeriodicalDaoImpl getInstance() {
        logger.trace("Get the instance of JdbcPeriodicalDaoImpl");
        if (instance == null) {
            synchronized (JdbcPeriodicalDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcPeriodicalDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Periodical periodical) {
        logger.trace("Trying to create ps");
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO periodical " +
                "(title, description, price_per_item, id_theme, id_status, id_type) " +
                "VALUES (?, ?, ?, ?, ?, ?);")) {

            ps.setString(1, periodical.getTitle());
            ps.setString(2, periodical.getDescription());
            ps.setBigDecimal(3, periodical.getPricePerItem());
            ps.setString(4, periodical.getTheme());
            ps.setString(5, periodical.getPeriodicalStatus().name());
            ps.setString(6, periodical.getPeriodicalType().name());

            ps.execute();
        } catch (SQLException e) {
            logger.trace("Catched SQLException {}", e);
            e.printStackTrace();
        }
    }

    public PeriodicalService.PaginationResult searchPeriodicalsWithPagination(int lowerBound, int upperBound, String searchKey) {
        logger.info("Search periodical by pagination with lowerBound = {}, upperBound = {} and searchKey = {}", lowerBound, upperBound, searchKey);

        PeriodicalService.PaginationResult paginationResult = new PeriodicalService.PaginationResult();
        PeriodicalMapper periodicalMapper = new PeriodicalMapper();
        List<Periodical> periodicals = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement periodicalsPS = connection.prepareStatement("SELECT p.id, p.title, p.description, t.name, ps.status, \n" +
                     "tp.type, p.price_per_item FROM myPeriodics.periodical p \n" +
                     "INNER JOIN periodic_status ps ON p.id_status = ps.id \n" +
                     "INNER JOIN themes t ON p.id_theme = t.id \n" +
                     "INNER JOIN types tp ON p.id_type = tp.id\n" +
                     "WHERE p.id LIKE ? ORDER BY p.id LIMIT ?, ?;");
             PreparedStatement countRowsPS = connection.prepareStatement("SELECT COUNT(*) FROM periodical WHERE title LIKE ?;")) {

            logger.trace("try to get queryList");

            periodicalsPS.setString(1, "%" + searchKey + "%");
            periodicalsPS.setInt(2, lowerBound);
            periodicalsPS.setInt(3, upperBound);

            ResultSet rs = periodicalsPS.executeQuery();

            while (rs.next()) {
                logger.info("We have smth inside rs_1");
                Periodical periodical = periodicalMapper.extractFromResultSet(rs);
                periodicals.add(periodical);
            }
            rs.close();

            countRowsPS.setString(1, "%" + searchKey + "%");
            rs = countRowsPS.executeQuery();

            if (rs.next()) {
                logger.info("We have smth inside rs_2");
                paginationResult.setNuOfRows(rs.getInt(1));
            }
            rs.close();

        } catch (SQLException e) {
            logger.trace("Catched SQLException {}", e);
            e.printStackTrace();
        }

        paginationResult.setPeriodicalList(new ArrayList<>(periodicals));
        return paginationResult;
    }

    @Override
    public void changePeriodicalStatus(String periodical_id, String newPeriodicStatus) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE periodical SET id_status = (?) WHERE `id` = (?);")) {
            ps.setString(2, periodical_id);
            ps.setInt(1, newPeriodicStatus.equals("ORDERABLE") ? 1 : 2);
            ps.execute();

        } catch (SQLException e) {
            logger.trace("Caught SQLException exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public Periodical findById(long id) {
        PeriodicalMapper periodicalMapper = new PeriodicalMapper();
        Periodical periodical;

        try (PreparedStatement ps = connection.prepareStatement("SELECT p.id, p.title, p.description, t.name, ps.status, \n" +
                "tp.type, p.price_per_item FROM myPeriodics.periodical p \n" +
                "INNER JOIN periodic_status ps ON p.id_status = ps.id \n" +
                "INNER JOIN themes t ON p.id_theme = t.id \n" +
                "INNER JOIN types tp ON p.id_type = tp.id\n" +
                "WHERE p.id LIKE ?;")) {


            // "SELECT * FROM periodical where id=(?);")) {
            ps.setLong(1, id);
            final ResultSet rs = ps.executeQuery();

            while (!rs.next()) {
                return null;
            }
            logger.info("We have smth inside rs_1");
            periodical = periodicalMapper.extractFromResultSet(rs);

            rs.close();
            return periodical;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Periodical> findAll() {
        return null;
    }

    @Override
    public void update(Periodical periodical) {

        try (PreparedStatement ps = connection.prepareStatement("UPDATE periodical SET title = (?), description = (?), price_per_item = (?) WHERE (id = (?))")) {
            ps.setString(1, periodical.getTitle());
            ps.setString(2, periodical.getDescription());
            ps.setString(3, periodical.getPricePerItem().toString());
            ps.setLong(4, periodical.getId());
            ps.execute();

        } catch (SQLException exception) {
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
