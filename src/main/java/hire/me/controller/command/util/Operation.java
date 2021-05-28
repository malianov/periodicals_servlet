package hire.me.controller.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum Operation {
    TO_REGISTRATION_PAGE("to_registration_page"),
    LOGIN("login"),
    REGISTRATION("registration"),
    TO_LOGIN_PAGE("to_login_page"),
    TO_HOME_PAGE("to_home_page"),
    TO_SUPPORT_PAGE("to_support_page"),
    TO_CATALOG("to_catalog_page"),
    LOGOUT("logout"),
    TO_SUBSCRIBERS_PAGE("to_subscribers_page"),
    BLOCK_UNBLOCK("to_block_unblock_user"),
    EDIT_PERIODIC("to_edit_periodic"),
    MAKE_ORDER_NONORDER_PERIODIC("to_make_order_nonorder_periodic");

    private static final Logger logger = LogManager.getLogger(Operation.class);

    private String path;

    Operation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
