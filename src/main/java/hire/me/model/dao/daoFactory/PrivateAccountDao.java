package hire.me.model.dao.daoFactory;

import java.math.BigDecimal;

public interface PrivateAccountDao {
    void increaseBalance(Long subscriberId, BigDecimal additionToBalance);

    BigDecimal getSubscriberBalance(Long subscriberId);
}
