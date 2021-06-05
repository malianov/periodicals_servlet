package hire.me.model.dao.daoFactory;

import hire.me.model.entity.periodical.Periodical;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public interface PrivateAccountDao {
    void increaseBalance(Long subscriberId, BigDecimal additionToBalance);
    BigDecimal getSubscriberBalance(Long subscriberId);
}
