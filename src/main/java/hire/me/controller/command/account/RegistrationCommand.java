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
        logger.trace("execute");

        final String password = request.getParameter("password");
        final String confirmedPassword = request.getParameter("confirmedPassword");

        if (!password.equals(confirmedPassword)) {
            return "/WEB-INF/common/registration.jsp?passwordsAreDifferent=true";
        }

        try {
            String hashedPassword = Password.generateStrongPasswordHash(password);
            setHashedPassword(hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        Map<String, String> registrationData = Map.of(
                "login", request.getParameter("login"),
                "name", request.getParameter("name"),
                "surname", request.getParameter("surname"),
                "email", request.getParameter("email"),
                "password", hashedPassword,
                "role", request.getParameter("role"),
                "personal_account", "0.0");


        if (!DataValidator.validateLogin(registrationData.get("login"))) {
            logger.trace("validate login");
            return "/WEB-INF/common/registration.jsp?invalidLogin=true";
        }

        if (!DataValidator.validateEmail(registrationData.get("email"))) {
            logger.trace("validate email");
            return "/WEB-INF/common/registration.jsp?invalidEmail=true";
        }

        if (!DataValidator.validatePassword(password)) {
            logger.trace("validate password");
            return "/WEB-INF/common/registration.jsp?invalidPassword=true";
        }

        User user = newUserRegistration(registrationData);

        try {
            userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "/WEB-INF/common/registration.jsp?alreadyExist=true";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_home_page";
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

