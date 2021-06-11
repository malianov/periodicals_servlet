package hire.me.model.dao.impl;

import hire.me.model.dao.daoFactory.ThemeDao;
import hire.me.model.dao.impl.queries.ThemeSQL;
import hire.me.model.dao.mapper.ThemeMapper;
import hire.me.model.entity.periodical.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static hire.me.connection.ConnectionPool.getConnection;

public class JdbcThemeDaoImpl implements ThemeDao {

    private static final Logger logger = LogManager.getLogger(JdbcUserDaoImpl.class);

    final private Connection connection = getConnection();
    private static JdbcThemeDaoImpl instance;

    private JdbcThemeDaoImpl() {
    }

    public static JdbcThemeDaoImpl getInstance() {
        if (instance == null) {
            synchronized (JdbcThemeDaoImpl.class) {
                if (instance == null) {
                    instance = new JdbcThemeDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void create(Theme entity) {
    }

    @Override
    public Optional<Theme> findById(long id) {
        ThemeMapper themeMapper = new ThemeMapper();

        try {
            PreparedStatement ps = connection.prepareStatement(ThemeSQL.READ_THEME_BY_ID.getQUERY());
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return Optional.of(themeMapper.extractFromResultSet(rs));
            }
            rs.close();

        } catch (SQLException exception) {
            logger.error("Error with DAO: {}", exception);
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Theme> findAll() {
        ThemeMapper themeMapper = new ThemeMapper();
        List<Theme> themes = new LinkedList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT t.id, t.theme_ua FROM themes t");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Theme theme = themeMapper.extractFromResultSet(rs);
                themes.add(theme);
            }
            rs.close();
        } catch (SQLException exception) {
            logger.error("Error with DAO: {}", exception);
            exception.printStackTrace();
        }
        return themes;
    }

    @Override
    public void update(Theme theme) {
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public void close() {
    }
}
