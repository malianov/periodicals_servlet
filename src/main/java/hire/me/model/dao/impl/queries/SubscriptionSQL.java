package hire.me.model.dao.impl.queries;

public enum SubscriptionSQL {

    INSERT("INSERT INTO subscriptions (subscriber_id, periodic_id, periodic_item, periodic_year, item_price, subscriber_address, subscription_date, subscription_time) VALUES ((?), (?), (?), (?), (?), (?), now(), now());"),
    UPDATE_BALANCE("UPDATE users SET balance=(?) WHERE id=(?);"),
    READ_ACTUAL_BALANCE("SELECT balance FROM users where id=(?);"),
    READ_ACTUAL_BALANCE_WITH_BALANCE("SELECT balance FROM users where id=(?);"),
    READ_ACTUAL_PERIODIC_PRICE("SELECT price_per_item FROM periodical where id=(?);"),
    READ_SUBSCRIPTION("SELECT s.id, s.subscriber_id, s.periodic_id, p.id, s.periodic_item, s.subscription_date, s.subscriber_address, s.item_price, s.periodic_year " +
            "FROM subscriptions s " +
            "JOIN periodical p ON " +
            "s.periodic_id = p.id " +
            "WHERE s.subscriber_id LIKE ? ORDER BY s.id LIMIT ?, ?;"),
    COUNT_ROWS("SELECT COUNT(*) FROM subscriptions WHERE subscriptions.subscriber_id LIKE ?;");

    String QUERY;

    SubscriptionSQL(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQUERY() {
        return QUERY;
    }
}
