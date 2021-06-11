package hire.me.model.dao.mapper;

import hire.me.model.entity.periodical.PeriodicalStatus;

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
