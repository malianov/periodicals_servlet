package hire.me.model.entity;

import java.math.BigDecimal;

public class Subscriber extends Account {

    public Subscriber() {}

    public Subscriber(String login, String password, AccountStatus status, Person person) {
        super(login, password, status, person);
    }

    private boolean subscribeForPeriodical(Periodical periodical) {
        return false;
    }

    private boolean replenishMoneyAccount(BigDecimal quantity) {
        return false;
    }
}
