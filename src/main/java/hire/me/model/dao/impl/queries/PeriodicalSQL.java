package hire.me.model.dao.impl.queries;

public enum PeriodicalSQL {

    READ_PERIODICAL_WITH_SEARCH("(SELECT p.id, p.title, p.description, p.price_per_item, p.id_type, p.id_status, p.id_theme " +
            "FROM periodical p " +
            "WHERE p.title LIKE ? ORDER BY p.id LIMIT ?, ?);"),
    COUNT_PERIODICAL_BY_TITLE("SELECT COUNT(*) FROM periodical WHERE title LIKE ?;"),
    SET_STATUS("UPDATE periodical SET id_status = (?) " +
            "WHERE `id` = (?);"),
    READ_BY_ID("SELECT p.id, p.title, p.description, p.id_theme, p.id_status, p.id_type, p.price_per_item " +
            "FROM myPeriodics.periodical p WHERE p.id LIKE ?;"),
    UPDATE("UPDATE periodical SET title = (?), description = (?), price_per_item = (?) WHERE (id = (?))");

    String QUERY;

    PeriodicalSQL(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQUERY() {
        return QUERY;
    }
}
