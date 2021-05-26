package hire.me.model.dao.mapper;

import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.PeriodicalStatus;
import hire.me.model.entity.periodical.PeriodicalType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PeriodicalMapper implements ObjectMapper<Periodical> {
    private static final Logger logger = LogManager.getLogger(PeriodicalMapper.class);

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRICE_PER_ITEM = "price_per_item";
    private static final String THEME = "id_theme";
    private static final String STATUS = "status";
    private static final String TYPE = "type";

    @Override
    public Periodical extractFromResultSet(ResultSet rs) throws SQLException {

        Periodical periodical = new Periodical();
        logger.trace("Create new periodical");
        periodical.setId(rs.getInt(ID));
        periodical.setTitle(rs.getString(TITLE));
        periodical.setDescription(rs.getString(DESCRIPTION));
        periodical.setPricePerItem(rs.getLong(PRICE_PER_ITEM));
        periodical.setTheme(rs.getString(THEME));
        logger.trace("PeriodicalStatus.valueOf(rs.getString(STATUS)) - {}", PeriodicalStatus.valueOf(rs.getString(STATUS).toUpperCase()));
        periodical.setPeriodicalStatus(PeriodicalStatus.valueOf(rs.getString(STATUS).toUpperCase()));
        logger.trace("type = {}", rs.getString(TYPE).toUpperCase());
        periodical.setPeriodicalType(PeriodicalType.valueOf(rs.getString(TYPE).toUpperCase()));

        return periodical;
    }
}
