package hire.me.model.dao.impl.queries;

public enum PeriodicalTypeSQL {

    READ_BY_ID("SELECT id, type_ua FROM types where id=(?)");
    
    String QUERY;

    PeriodicalTypeSQL(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQUERY() {
        return QUERY;
    }
}
