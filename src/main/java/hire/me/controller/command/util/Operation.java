package hire.me.controller.command.util;

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
    MAKE_ORDER_NONORDER_PERIODIC("to_make_order_nonorder_periodic"),
    MAKE_SUBSCRIPTION("to_order_periodic"),
    ADDITION_TO_BALANCE("to_increase_balance"),
    SHOW_ALL_SUBSCRIPTIONS("to_subscriptions_page"),
    SHOW_ALL_SINGLE_USER("to_my_subscriptions_page"),
    EDIT_PERIODIC("to_edit_periodic");

    private String path;

    Operation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
