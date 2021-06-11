package hire.me.model.dao.impl.queries;

public enum ThemeSQL {

    READ_THEME_BY_ID("SELECT id, theme_ua FROM themes where id=(?)");

    String QUERY;

    ThemeSQL(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQUERY() {
        return QUERY;
    }
}
