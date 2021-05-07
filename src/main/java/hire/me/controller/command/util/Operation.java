package hire.me.controller.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum Operation {
    LOGIN("login"),
    REGISTRATION("registration");

    private static final Logger logger = LogManager.getLogger(Operation.class);

    private String path;

    Operation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
