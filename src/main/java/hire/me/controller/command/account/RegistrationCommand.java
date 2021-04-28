package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.model.entity.AccountStatus;
import hire.me.model.entity.Person;
import hire.me.model.entity.Subscriber;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.SubscriberService;
import hire.me.model.validator.DataValidator;
import hire.me.utility.Password;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class RegistrationCommand implements Command, Password {
    private SubscriberService subscriberService;
    private ServiceFactory serviceFactory;

    private String hashedPassword;

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public RegistrationCommand() {
        this.serviceFactory = ServiceFactory.getInstance();
        this.subscriberService = serviceFactory.getSubscriberService();
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
                "password",  hashedPassword);

        if (!DataValidator.validateEmail(registrationData.get("email"))) {
            return "/WEB-INF/common/registration.jsp?invalidEmail=true";
        }

        if (!DataValidator.validatePassword(password)) {
            return "/WEB-INF/common/registration.jsp?invalidPassword=true";
        }

        Subscriber subscriber = newSubscriberRegistration(registrationData);

        try {
            subscriberService.registerSubscriberAccount(subscriber);
        } catch (Exception e) {
            e.printStackTrace();
            return "/WEB-INF/common/registration.jsp?alreadyExist=true";
        }
        return "/WEB-INF/common/registration.jsp?success=true";
    }

    private Subscriber newSubscriberRegistration(Map<String, String> registrationData) {

        final Person person = new Person();
        person.setName(registrationData.get("name"));
        person.setSurname(registrationData.get("surname"));
        person.setEmail(registrationData.get("email"));

        final Subscriber subscriber = new Subscriber();
        subscriber.setLogin(registrationData.get("login"));
        subscriber.setPassword(registrationData.get("password"));
        subscriber.setAccountStatus(AccountStatus.ACTIVE);
        subscriber.setPerson(person);

        return subscriber;
    }
}
