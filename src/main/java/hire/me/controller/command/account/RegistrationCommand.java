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

        Map<String, String> collectedErrors = new HashMap<>();

        final String password = request.getParameter("password");
        final String confirmedPassword = request.getParameter("confirmedPassword");

        logger.trace("password = {}, confirmedPassword = {}", password, confirmedPassword);

        createHashedPassword(password);

        logger.trace(collectedErrors.size());

        Map<String, String> registrationData = Map.of(
                "login", request.getParameter("login"),
                "name", request.getParameter("name"),
                "surname", request.getParameter("surname"),
                "email", request.getParameter("email"),
                "password", hashedPassword,
                "role", request.getParameter("role"),
                "personal_account", "0.0");

        logger.trace(collectedErrors.size());

        arePasswordsEqual(password, confirmedPassword, collectedErrors);
        logger.trace(collectedErrors.size());
        isLoginValid(registrationData, collectedErrors);
        logger.trace(collectedErrors.size());
        isNameValid(registrationData, collectedErrors);
        logger.trace(collectedErrors.size());
        isSurnameValid(registrationData, collectedErrors);
        logger.trace(collectedErrors.size());
        isEmailValid(registrationData, collectedErrors);
        logger.trace(collectedErrors.size());
        isPasswordValid(confirmedPassword, collectedErrors);

        logger.trace(collectedErrors.size());

        if(!collectedErrors.isEmpty()) {
            request.setAttribute("errorMessages", collectedErrors);
            logger.trace("inside collectedErrors are {} errors", collectedErrors.size());
            return "/WEB-INF/view/error_message.jsp";
        }

        createHashedPassword(password);
        User user = newUserRegistration(registrationData);

        try {
            userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            collectedErrors.put("errorDuringUserRegistration", "Oops. Smth was wrong. Please, try again.");
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
            e.printStackTrace();
        }
    }

    private void arePasswordsEqual(String password, String confirmedPassword, Map<String, String> collectedErrors) {
        if (!password.equals(confirmedPassword)) {
            collectedErrors.put("errorPasswordEquality", "The entered passwords are different");
        }
    }

    private void isLoginValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateLogin(registrationData.get("login"))) {
            collectedErrors.put("errorLoginNotValid", "The entered login is incorrect. Please, imagine another one.");
        }
    }

    private void isNameValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateName(registrationData.get("name"))) {
            collectedErrors.put("errorNameNotValid", "The entered name is incorrect. Please, imagine another one.");
        }
    }

    private void isSurnameValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateSurname(registrationData.get("surname"))) {
            collectedErrors.put("errorSurnameNotValid", "The entered surname is incorrect. Please, imagine another one.");
        }
    }

    private void isEmailValid(Map<String, String> registrationData, Map<String, String> collectedErrors) {
        if (!DataValidator.validateEmail(registrationData.get("email"))) {
            collectedErrors.put("errorEmailNotValid", "The entered email is incorrect");
        }
    }

    private void isPasswordValid(String password, Map<String, String> collectedErrors) {
        if (!DataValidator.validatePassword(password)) {
            collectedErrors.put("errorPasswordNotValid", "The entered password is invalid");
        }
    }

    private User newUserRegistration(Map<String, String> registrationData) {
        logger.trace("new user registration");

        final Person person = new Person();
        person.setName(registrationData.get("name"));
        person.setSurname(registrationData.get("surname"));
        person.setEmail(registrationData.get("email"));

        UserRole role = UserRole.valueOf(registrationData.get("role").toUpperCase());

        final User user = new User.Builder()
                .login(registrationData.get("login"))
                .password(registrationData.get("password"))
                .userStatus(UserStatus.ACTIVE)
                .person(person)
                .role(role)
                .balance(new BigDecimal(registrationData.get("personal_account")))
                .build();

        return user;
    }
}

