package hire.me.model.dao.impl.queries;

public enum PrivateAccountSQL {

    SET_BALANCE("UPDATE users SET balance=(?) WHERE (id=(?))"),
    READ_BALANCE("SELECT balance FROM users where id=(?);");

    String QUERY;

    PrivateAccountSQL(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQUERY() {
        return QUERY;
    }
}
