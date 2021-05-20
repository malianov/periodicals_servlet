package hire.me.model.dao.impl;

import hire.me.connection.ConnectionPool;
import hire.me.model.dao.daoFactory.PeriodicalDao;
import hire.me.model.dao.mapper.PeriodicalMapper;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            ps.setLong(3, periodical.getPricePerItem());
            ps.setString(4, periodical.getTheme());
            ps.setString(5, periodical.getPeriodicalStatus().name());
            ps.setString(6, periodical.getPeriodicalType().name());

            ps.execute();
        } catch (SQLException e) {
            logger.trace("Catched SQLException {}", e);
            e.printStackTrace();
        }
    }

//    public List<Periodical> findPeriodicalByPartOfName(String partOfName) {
//        logger.info("Lets find periodical by name - {}", partOfName);
//        Map<Integer, Periodical> periodicalItems = new HashMap<>();
//
//        String query = "SELECT * FROM periodical WHERE title LIKE ? ESCAPE '!';";
//
//        try (Statement st = connection.createStatement()) {
//            ResultSet rs = st.executeQuery(query);
//            PeriodicalMapper periodicalMapper = new PeriodicalMapper();
//
//            while (rs.next()) {
//                logger.info("Trying to get smth from rs");
//                Periodical periodical = periodicalMapper.extractFromResultSet(rs);
//            }
//
//            return new ArrayList<>(periodicalItems.values());
//        } catch (SQLException e) {
//            logger.trace("Catched SQLException {}", e);
//            e.printStackTrace();
//            return null;
//        }
//    }

    public PeriodicalService.PaginationResult searchPeriodicalsWithPagination(int lowerBound, int upperBound, String searchKey) {
        logger.info("Search periodical by pagination with lowerBound = {}, upperBound = {} and searchKey = {}", lowerBound, upperBound, searchKey);

        PeriodicalService.PaginationResult paginationResult = new PeriodicalService.PaginationResult();
        PeriodicalMapper periodicalMapper = new PeriodicalMapper();
        List<Periodical> periodicals = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement periodicalsPS = connection.prepareStatement("SELECT periodical.title, periodical.description, periodical.price_per_item, types.type, periodical.id_theme, periodic_status.status FROM periodical INNER JOIN periodic_status ON periodical.id_status = periodic_status.id INNER JOIN types ON periodical.id_type = types.id WHERE periodical.title LIKE ? LIMIT ?, ?;");
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
    public Periodical findById(long id) {
        return null;
    }

    @Override
    public List<Periodical> findAll() {
        return null;
    }

    @Override
    public void update(Periodical periodical) {

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
