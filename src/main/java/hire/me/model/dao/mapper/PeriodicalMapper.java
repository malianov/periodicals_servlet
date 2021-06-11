package hire.me.model.dao.mapper;

import hire.me.model.entity.periodical.Periodical;
import hire.me.model.service.PeriodicalStatusService;
import hire.me.model.service.PeriodicalTypeService;
import hire.me.model.service.ThemeService;
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
    private static final String TYPE_ID = "id_type";
    private static final String STATUS_ID = "id_status";
    private static final String THEME_ID = "id_theme";

    @Override
    public Periodical extractFromResultSet(ResultSet rs) throws SQLException {
        ThemeService themeService = ThemeService.getInstance();
        PeriodicalTypeService periodicalTypeService = PeriodicalTypeService.getInstance();
        PeriodicalStatusService periodicalStatusService = PeriodicalStatusService.getInstance();

        return new Periodical.Builder()
                .id(rs.getInt(ID))
                .title(rs.getString(TITLE))
                .description(rs.getString(DESCRIPTION))
                .pricePerItem(rs.getBigDecimal(PRICE_PER_ITEM))
                .theme(themeService.getThemeById(rs.getLong(THEME_ID)))
                .periodicalType(periodicalTypeService.getPeriodicalTypeById(rs.getLong(TYPE_ID)))
                .periodicalStatus(periodicalStatusService.getPeriodicalStatusServiceById(rs.getLong(STATUS_ID)))
                .build();
    }
}
