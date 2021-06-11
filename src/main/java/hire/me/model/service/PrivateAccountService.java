package hire.me.model.service;

import hire.me.model.dao.daoFactory.DaoFactory;
import hire.me.model.dao.daoFactory.PrivateAccountDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class PrivateAccountService {
    private static final Logger logger = LogManager.getLogger(PrivateAccountService.class);

    private DaoFactory daoFactory;
    private static PrivateAccountService instance;

    private PrivateAccountService() {
        daoFactory = DaoFactory.getInstance();
    }

    public static PrivateAccountService getInstance() {
        if (instance == null) {
            synchronized (PrivateAccountService.class) {
                if (instance == null) {
                    instance = new PrivateAccountService();
                }
            }
        }
        return instance;
    }

    public void increaseBalance(Long subscriberId, BigDecimal additionToBalance) {
        PrivateAccountDao dao = daoFactory.createPrivateAccountDao();

        dao.increaseBalance(subscriberId, additionToBalance);
    }

    public BigDecimal getSubscriberBalance(Long subscriberId) {
        return daoFactory.createPrivateAccountDao()
                .getSubscriberBalance(subscriberId);
    }
}
