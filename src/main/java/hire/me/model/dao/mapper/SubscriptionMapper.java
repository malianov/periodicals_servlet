package hire.me.model.dao.mapper;

import hire.me.model.entity.subscription.Subscription;
import hire.me.model.service.PeriodicalService;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionMapper implements ObjectMapper<Subscription> {
    private static final Logger logger = LogManager.getLogger(SubscriptionMapper.class);

    private static final String ID = "id";
    private static final String SUBSCRIBER_ID = "subscriber_id";
    private static final String PERIODIC_ID = "periodic_id";
    private static final String PERIODIC_ITEM = "periodic_item";
    private static final String DATE = "subscription_date";
    private static final String ADDRESS = "subscriber_address";
    private static final String PERIODIC_YEAR = "periodic_year";
    private static final String ITEM_PRICE = "item_price";

    UserService userService = UserService.getInstance();
    PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public Subscription extractFromResultSet(ResultSet rs) throws SQLException {

        return new Subscription.Builder()
                .id(rs.getLong(ID))
                .user(userService.getUserById(rs.getLong(SUBSCRIBER_ID)).get())
                .periodical(periodicalService.getPeriodicById(rs.getLong(PERIODIC_ID)).get())
                .periodicItem(rs.getString(PERIODIC_ITEM))
                .date(rs.getDate(DATE).toLocalDate())
                .address(rs.getString(ADDRESS))
                .periodic_year(rs.getString(PERIODIC_YEAR))
                .item_price(rs.getBigDecimal(ITEM_PRICE))
                .build();
    }
}