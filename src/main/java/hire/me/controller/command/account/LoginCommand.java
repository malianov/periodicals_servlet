package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.entity.account.UserRole;
import hire.me.model.service.UserService;
import hire.me.model.service.ServiceFactory;
import hire.me.model.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService userService;
    private ServiceFactory serviceFactory;

    public LoginCommand() {
        this.serviceFactory = ServiceFactory.getInstance();
        this.userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Map<String, String> collectedErrors = new HashMap<>();

        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        Map<String, String> loginData = Map.of(
                "login", login,
                "password", password);

        isLoginValid(login, collectedErrors);
        isPasswordValid(password, collectedErrors);
        isLoginExists(login, collectedErrors);

        if(!collectedErrors.isEmpty()) {
            request.setAttribute("errorMessages", collectedErrors);
            logger.trace("inside collectedErrors are {} errors", collectedErrors.size());
            return "/WEB-INF/view/error_message.jsp";
        }

        final UserRole role = userService.getRoleByLogin(login);
        final BigDecimal privateAccount = userService.getPrivateAccountByLogin(login);
        final Long userId = userService.getIdByLogin(login);

        if(userService.isPasswordCorrectForLogin(login, password, role)) {
            if (CommandUtility.checkUserIsLogged(request, login)) {
                CommandUtility.logoutUser(request, login);
                collectedErrors.put("errorPasswordNotValid", "This login is already in system");
                request.setAttribute("errorMessages", collectedErrors);
                return "/WEB-INF/view/error_message.jsp";
            }
            CommandUtility.loginUser(request, userId, login, password, role, privateAccount);
        } else {
            collectedErrors.put("errorPasswordNotValid", "The entered password is incorrect");
        }

        if(!collectedErrors.isEmpty()) {
            request.setAttribute("errorMessages", collectedErrors);
            logger.trace("inside collectedErrors are {} errors", collectedErrors.size());
            return "/WEB-INF/view/error_message.jsp";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_home_page";
    }

    private void isLoginValid(String login, Map<String, String> collectedErrors) {
        if (!DataValidator.validateLogin(login)) {
            collectedErrors.put("errorLoginNotValid", "The entered login is incorrect. Please, imagine another one.");
        }
    }

    private void isPasswordValid(String password, Map<String, String> collectedErrors) {
        if (!DataValidator.validatePassword(password)) {
            collectedErrors.put("errorPasswordNotValid", "The entered password is incorrect");
        }
    }

    private void isLoginExists(String login, Map<String, String> collectedErrors) {
        if(!userService.isLoginExist(login)) {
            collectedErrors.put("errorLoginNotValid", "The entered login does not exists");
        }
    }
}
