package hire.me.model.dao.mapper;

import hire.me.model.entity.language.Language;
import hire.me.model.entity.periodical.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper implements ObjectMapper<Theme> {
    private static final Logger logger = LogManager.getLogger(ThemeMapper.class);

//    private static final String ID = "id";
    private static final String THEME = "theme";
    private static final String THEME_ID = "theme_id";
    private static final String LANGUAGE = "language";

    @Override
    public Theme extractFromResultSet(ResultSet rs) throws SQLException {
        logger.trace("Inside Them mapper");

        logger.trace(" {} = ", rs.getInt(THEME_ID));
        logger.trace(" {} = ", rs.getString(THEME));
        logger.trace(" {} = ", Language.valueOf(rs.getString(LANGUAGE).toUpperCase()));

        return new Theme(rs.getInt(THEME_ID), rs.getString(THEME), Language.valueOf(rs.getString(LANGUAGE).toUpperCase()));
    }
}