package hire.me.model.entity.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum UserStatus {
    ACTIVE, BLOCKED, UNKNOWN;

    private static final Logger logger = LogManager.getLogger(UserStatus.class);
}
