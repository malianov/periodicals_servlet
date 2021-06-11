package hire.me.model.dao.mapper;

import hire.me.model.entity.periodical.PeriodicalType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicalTypeMapper implements ObjectMapper<PeriodicalType> {
    private static final Logger logger = LogManager.getLogger(ThemeMapper.class);

    private static final String ID = "id";
    private static final String TYPE = "type_ua";

    @Override
    public PeriodicalType extractFromResultSet(ResultSet rs) throws SQLException {
        return new PeriodicalType(rs.getLong(ID), rs.getString(TYPE));
    }
}