package hire.me.controller.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum Operation {
    LOGIN("login"),
    REGISTRATION("registration"),
    TO_LOGIN_PAGE("to_login_page"),
    TO_REGISTRATION_PAGE("to_registration_page"),
    TO_HOME_PAGE("to_home_page");

    private static final Logger logger = LogManager.getLogger(Operation.class);

    private String path;

    Operation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
