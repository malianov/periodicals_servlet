package hire.me.model.dao.mapper;

import hire.me.model.entity.language.Language;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.PeriodicalStatus;
import hire.me.model.entity.periodical.PeriodicalType;
import hire.me.model.entity.periodical.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicalMapper implements ObjectMapper<Periodical> {
    private static final Logger logger = LogManager.getLogger(PeriodicalMapper.class);

    private static final String ID = "id";
    private static final String PERIODICAL_ID = "id_periodic";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String THEME_ID = "theme_id";
    private static final String THEME = "theme";
    private static final String LANGUAGE = "language";
    private static final String TYPE = "type";
    private static final String TYPE_ID = "type_id";
    private static final String PRICE_PER_ITEM = "price_per_item";
    private static final String STATUS = "status";

    @Override
    public Periodical extractFromResultSet(ResultSet rs) throws SQLException {
logger.trace("inside extractFromResultSet");

logger.trace("{} = ", rs.getInt(ID));
        logger.trace("{} = ", rs.getString(TITLE));
        logger.trace("{} = ", rs.getString(DESCRIPTION));
        logger.trace("{} = ", rs.getString(LANGUAGE).toUpperCase());
        logger.trace("{} = ", rs.getString(TYPE));
        logger.trace("{} = ", rs.getInt(TYPE_ID));
        logger.trace("{} = ", rs.getBigDecimal(PRICE_PER_ITEM));
        logger.trace("{} = ", rs.getString(STATUS));
        logger.trace("////////////////////////////////////////////////////");
        logger.trace(" {} = ", rs.getInt(THEME_ID));
        logger.trace(" {} = ", rs.getString(THEME));
        logger.trace(" {} = ", Language.valueOf(rs.getString(LANGUAGE).toUpperCase()));

        Periodical periodical = new Periodical.Builder()
                .id(rs.getInt(ID))
                .periodicalId(rs.getInt(PERIODICAL_ID))
                .title(rs.getString(TITLE))
                .description(rs.getString(DESCRIPTION))
                .theme(new Theme(rs.getInt(THEME_ID), rs.getString(THEME), Language.valueOf(rs.getString(LANGUAGE).toUpperCase())))
                .language(Language.valueOf(rs.getString(LANGUAGE).toUpperCase()))
                .periodicalType(new PeriodicalType(rs.getInt(TYPE_ID),rs.getString(TYPE), Language.valueOf(rs.getString(LANGUAGE).toUpperCase())))
                .pricePerItem(rs.getBigDecimal(PRICE_PER_ITEM))
                .periodicalStatus(PeriodicalStatus.valueOf(rs.getString(STATUS).toUpperCase()))
                .build();
        logger.trace("inside extractFromResultSet, periodical - {}", periodical);

        return periodical;
    }
}
