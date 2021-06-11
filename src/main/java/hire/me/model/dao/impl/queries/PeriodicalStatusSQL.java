package hire.me.model.dao.impl.queries;

public enum PeriodicalStatusSQL {

    READ_BY_ID("SELECT id, status FROM periodic_status where id=(?)");
    
    String QUERY;

    PeriodicalStatusSQL(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQUERY() {
        return QUERY;
    }
}
