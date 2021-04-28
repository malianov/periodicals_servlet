package hire.me.model.validator;

import java.util.regex.Pattern;

public class DataValidator {

    public static boolean validateEmail(String email) {
        return email != null && Pattern.matches("(^[\\w\\.-_]+)@([\\w\\.-]+)\\.([\\w\\.-]+)$", email);
    }

    public static boolean validatePassword(String password) {
        return password != null && Pattern.matches("^([a-zA-Z0-9_.]{1,30})$", password);
    }

    //TODO
    // Add user name, surname and login validation
}