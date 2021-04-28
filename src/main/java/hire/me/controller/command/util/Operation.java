package hire.me.controller.command.util;

public enum Operation {
    LOGIN("login"),
    REGISTRATION("registration");

    private String path;

    Operation(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
