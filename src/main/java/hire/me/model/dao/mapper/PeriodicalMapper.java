package hire.me.model.dao.mapper;

import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.PeriodicalStatus;
import hire.me.model.entity.periodical.PeriodicalType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicalMapper implements ObjectMapper<Periodical> {
    private static final Logger logger = LogManager.getLogger(PeriodicalMapper.class);

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRICE_PER_ITEM = "price_per_item";
    private static final String THEME = "name";
    private static final String STATUS = "status";
    private static final String TYPE = "type";

    @Override
    public Periodical extractFromResultSet(ResultSet rs) throws SQLException {

        logger.trace("PeriodicalStatus.valueOf(rs.getString(STATUS).toUpperCase())) = {}", PeriodicalStatus.valueOf(rs.getString(STATUS).toUpperCase()));

        Periodical periodical = new Periodical.Builder()
                .id(rs.getInt(ID))
                .title(rs.getString(TITLE))
                .description(rs.getString(DESCRIPTION))
                .pricePerItem(rs.getBigDecimal(PRICE_PER_ITEM))
                .theme(rs.getString(THEME))
                .periodicalStatus(PeriodicalStatus.valueOf(rs.getString(STATUS).toUpperCase()))
                .periodicalType(PeriodicalType.valueOf(rs.getString(TYPE).toUpperCase()))
                .build();

        return periodical;
    }
}
