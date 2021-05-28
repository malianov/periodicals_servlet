package hire.me.model.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class DataValidator {
    private static final Logger logger = LogManager.getLogger(DataValidator.class);

    public static boolean validateEmail(String email) {
        return email != null && Pattern.matches("(^[\\w\\.-_]+)@([\\w\\.-]+)\\.([\\w\\.-]+)$", email);
    }

    public static boolean validatePassword(String password) {
        return password != null && Pattern.matches("^([a-zA-Z0-9_.]{1,30})$", password);
    }

    public static boolean validateLogin(String login) {
        return login != null && Pattern.matches("(^[\\w\\.-_]+)$", login);
    }

    //TODO
    // Add account name, surname and login validation
}