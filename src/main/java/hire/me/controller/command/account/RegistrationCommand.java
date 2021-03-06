package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.entity.account.Person;
import hire.me.model.service.UserService;
import hire.me.model.service.ServiceFactory;
import hire.me.model.validator.DataValidator;
import hire.me.utility.Password;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationCommand implements Command, Password {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    private UserService userService;
    private ServiceFactory serviceFactory;
    private String hashedPassword;

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public RegistrationCommand() {
        this.serviceFactory = ServiceFactory.getInstance();
        this.userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("RegistrationCommand executing");

        Map<String, String> collectedErrors = new HashMap<>();

        final String password = request.getParameter("password");
        final String confirmedPassword = request.getParameter("confirmedPassword");

        createHashedPassword(password);

        Map<String, String> registrationData = Map.of(
                "login", request.getParameter("login"),
                "name", request.getParameter("name"),
                "surname", request.getParameter("surname"),
                "email", request.getParameter("email"),
                "password", hashedPassword,
                "role", request.getParameter("role"),
                "personal_account", "0.0");

        arePasswordsEqual(password, confirmedPassword, collectedErrors);
        isLoginValid(registrationData, collectedErrors);
        isNameValid(registrationData, collectedErrors);
        isSurnameValid(registrationData, collectedErrors);
        isEmailValid(registrationData, collectedErrors);
        isPasswordValid(confirmedPassword, collectedErrors);

        if (!collectedErrors.isEmpty()) {
            request.setAttribute("errorMessages", collectedErrors);
            return "/WEB-INF/view/error_message.jsp";
        }

        createHashedPassword(password);
        User user = newUserRegistration(registrationData);

        try {
            userService.registerUser(user);
        } catch (Exception e) {
            logger.error("User cannot be registered to {}", user.toString());
            e.printStackTrace();
            collectedErrors.put("errorDuringUserRegistration", "error_message.oops-smth-was-wrong");
            request.setAttribute("errorMessages", collectedErrors);
            return "/WEB-INF/view/error_message.jsp";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_home_page";
    }

    private void createHashedPassword(String password) {
        try {
            String hashedPassword = Password.generateStrongPasswordHash(password);
            setHashedPassword(hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("The hashed password cannot be created");
            e.printStackTrace();
        }
    }

    private void arePasswordsEqual(String password, String confirmedPassword, Map<String, String> collectedErrors) {
        if (!password.equals(confirmedPassword)) {
            collectedErrors.put("errorPasswordEquality", "error_message.entered-passwords-are-different");
        }
    }

    private void isLoginValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateLogin(registrationData.get("login"))) {
            collectedErrors.put("errorLoginNotValid", "error_message.entered-login-incorrect");
        }
    }

    private void isNameValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateName(registrationData.get("name"))) {
            collectedErrors.put("errorNameNotValid", "error_message.entered-name-incorrect.");
        }
    }

    private void isSurnameValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateSurname(registrationData.get("surname"))) {
            collectedErrors.put("errorSurnameNotValid", "error_message.entered-surname-is-incorrect");
        }
    }

    private void isEmailValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateEmail(registrationData.get("email"))) {
            collectedErrors.put("errorEmailNotValid", "error_message.entered-email-is-incorrect");
        }
    }

    private void isPasswordValid(String password, Map<String, String> collectedErrors) {
        if (!DataValidator.validatePassword(password)) {
            collectedErrors.put("errorPasswordNotValid", "error_message.entered-password-is-invalid");
        }
    }

    private User newUserRegistration(Map<String, String> registrationData) {

        final Person person = new Person();
        person.setName(registrationData.get("name"));
        person.setSurname(registrationData.get("surname"));
        person.setEmail(registrationData.get("email"));

        UserRole role = UserRole.valueOf(registrationData.get("role").toUpperCase());

        return new User.Builder()
                .login(registrationData.get("login"))
                .password(registrationData.get("password"))
                .userStatus(UserStatus.ACTIVE)
                .person(person)
                .role(role)
                .balance(new BigDecimal(registrationData.get("personal_account")))
                .build();
    }
}

