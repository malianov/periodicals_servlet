package hire.me.model.dao.impl.queries;

public enum UserSQL {

    CREATE("INSERT INTO users " +
            "(login, first_name, surname, email, password, account_status, user_role, balance) " +
            "VALUES ((?),(?),(?),(?),(?),(?),(?),(?))"),
    CHECK_EXISTENCE("SELECT * FROM users where login=(?) and password=(?);"),
    CHECK_EMAIL_USING("SELECT * FROM users where email=(?);"),
    CHECK_LOGIN_USED("SELECT * FROM users where login=(?);"),
    CHECK_LOGIN_EXISTENCE("SELECT * FROM users where login=(?);"),
    READ_PASSWORD_FOR_LOGIN("SELECT password FROM users where login=(?);"),
    READ_ROLE_FOR_LOGIN("SELECT user_role FROM users WHERE login=(?);"),
    READ_ID_BY_LOGIN("SELECT id FROM users where login=(?);"),
    FILTER_SUBSCRIBERS("SELECT users.id, users.login, users.first_name, users.surname, users.email," +
            "users.account_status, users.balance FROM users WHERE users.user_role='SUBSCRIBER' AND (users.login LIKE ? OR users.first_name LIKE ? OR users.surname LIKE ? OR users.email LIKE ?) LIMIT ?, ?;"),
    COUNT_FILTERED_SUBSCRIBERS("SELECT COUNT(*) " +
                                       "FROM users WHERE users.user_role='SUBSCRIBER' AND (users.login LIKE ? OR users.first_name LIKE ? OR users.surname LIKE ? OR users.email LIKE ?);"),
    UPDATE_USER_STATUS("UPDATE users SET account_status = (?) WHERE `login` = (?);"),
    GET_SUBSCRIBER_BALANCE_BY_ID("SELECT users.balance FROM users where login=(?);"),
    READ_ACCOUNT_STATUS_BY_LOGIN("SELECT account_status FROM users WHERE login=(?);"),
    READ_USER_BY_ID("SELECT * FROM users where id=(?);");

    String QUERY;

    UserSQL(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQUERY() {
        return QUERY;
    }
}
