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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;
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
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String password = req.getParameter("password");
        final String confirmedPassword = req.getParameter("confirmedPassword");

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
                "login", req.getParameter("login"),
                "name", req.getParameter("name"),
                "surname", req.getParameter("surname"),
                "email", req.getParameter("email"),
                "password",  hashedPassword,
                "role", req.getParameter("role"));

        if (!DataValidator.validateEmail(registrationData.get("email"))) {
            return "/WEB-INF/common/registration.jsp?invalidEmail=true";
        }

        if (!DataValidator.validatePassword(password)) {
            return "/WEB-INF/common/registration.jsp?invalidPassword=true";
        }

        User user = newUserRegistration(registrationData);

        try {
            userService.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "/WEB-INF/common/registration.jsp?alreadyExist=true";
        }
        return "/WEB-INF/common/registration.jsp?success=true";
    }

    private User newUserRegistration(Map<String, String> registrationData) {

        final Person person = new Person();
        person.setName(registrationData.get("name"));
        person.setSurname(registrationData.get("surname"));
        person.setEmail(registrationData.get("email"));

        UserRole role = UserRole.valueOf(registrationData.get("role").toUpperCase());

        final User user = new User();
        user.setLogin(registrationData.get("login"));
        user.setPassword(registrationData.get("password"));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setPerson(person);
        user.setUserRole(role);
        return user;
    }
}
