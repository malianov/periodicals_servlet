package hire.me.model.dao.mapper;

import hire.me.model.entity.language.Language;
import hire.me.model.entity.periodical.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemeMapper implements ObjectMapper<Theme> {
    private static final Logger logger = LogManager.getLogger(ThemeMapper.class);

    private static final String ID = "id";
    private static final String THEME = "theme_en";

    @Override
    public Theme extractFromResultSet(ResultSet rs) throws SQLException {
        return new Theme(rs.getLong(ID), rs.getString(THEME));
    }
}