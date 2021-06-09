package hire.me.model.dao.mapper;

import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.PeriodicalStatus;
import hire.me.model.entity.periodical.Theme;
import hire.me.model.service.PeriodicalStatusService;
import hire.me.model.service.PeriodicalTypeService;
import hire.me.model.service.ThemeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicalStatusMapper implements ObjectMapper<PeriodicalStatus> {

    private static final String ID = "id";
    private static final String STATUS = "status";

    @Override
    public PeriodicalStatus extractFromResultSet(ResultSet rs) throws SQLException {
        return new PeriodicalStatus(rs.getLong(ID), rs.getString(STATUS));
    }
}
