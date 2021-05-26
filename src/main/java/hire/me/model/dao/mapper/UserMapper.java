package hire.me.model.dao.mapper;

import hire.me.model.entity.account.Person;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.entity.periodical.Periodical;
import hire.me.model.entity.periodical.PeriodicalStatus;
import hire.me.model.entity.periodical.PeriodicalType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{
private static final Logger logger = LogManager.getLogger(UserMapper.class);

    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String FIRST_NAME = "first_name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String STATUS = "account_status";

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User subscriber = new User();
        logger.trace("Create new subscriber");
        subscriber.setId(rs.getInt(ID));
        subscriber.setLogin(rs.getString(LOGIN));
        subscriber.setPerson(new Person(rs.getString(FIRST_NAME), rs.getString(SURNAME), rs.getString(EMAIL)));
        subscriber.setUserStatus(UserStatus.valueOf(rs.getString(STATUS).toUpperCase()));

        return subscriber;
    }
}
