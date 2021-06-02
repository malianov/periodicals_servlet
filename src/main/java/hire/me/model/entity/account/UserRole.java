package hire.me.model.entity.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum UserRole {
    SUBSCRIBER, ADMIN, GUEST;

    private static final Logger logger = LogManager.getLogger(UserRole.class);
}
