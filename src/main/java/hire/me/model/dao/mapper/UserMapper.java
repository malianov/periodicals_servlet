package hire.me.model.dao.mapper;

import hire.me.model.entity.account.Person;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {
    private static final Logger logger = LogManager.getLogger(UserMapper.class);

    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String FIRST_NAME = "first_name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String STATUS = "account_status";
    private static final String PERSONAL_BALANCE = "balance";

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return new User.Builder()
                .id(rs.getLong(ID))
                .login(rs.getString(LOGIN))
                .person(new Person(rs.getString(FIRST_NAME), rs.getString(SURNAME), rs.getString(EMAIL)))
                .userStatus(UserStatus.valueOf(rs.getString(STATUS).toUpperCase()))
                .balance(rs.getBigDecimal(PERSONAL_BALANCE))
                .build();
    }
}
