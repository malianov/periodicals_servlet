package hire.me.model.dao.daoFactory;

import hire.me.model.entity.periodical.Periodical;

import java.math.BigDecimal;
import java.sql.Connection;

public interface PrivateAccountDao {
    void increaseBalance(Connection serviceConnection, Long subscriber_id, BigDecimal additionToBalance);

    BigDecimal getSubscriberBalance(Connection serviceConnection, Long subscriberId);
    BigDecimal getSubscriberBalance(Long subscriberId);
}
