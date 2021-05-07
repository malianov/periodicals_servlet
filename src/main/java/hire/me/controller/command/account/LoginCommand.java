package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.entity.account.UserRole;
import hire.me.model.service.UserService;
import hire.me.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService userService;
    private ServiceFactory serviceFactory;

    public LoginCommand() {
        logger.trace("Entered to LoginCommand constructor");
        this.serviceFactory = ServiceFactory.getInstance();
        this.userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.trace("Entered to execute");

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        if(!userService.isLoginExist(login)) {
            logger.trace("Login {} doesn't exist", login);
            return "/WEB-INF/common/login.jsp?loginExist=false";
        }

        final UserRole role = userService.getRoleByLogin(login);

        logger.trace("Login {} has role as = {}", login, role);

        if(userService.isPasswordCorrectForLogin(login, password, role)) {
            logger.trace("The password {} for login {} and role {} is correct", password, login, role);
            CommandUtility.loginUser(req, login, password, role);
        } else {
            logger.trace("Password is incorrect");
            return "/WEB-INF/common/login.jsp?passwordCorrect=false";
        }

        String path = req.getServletContext().getContextPath();
        logger.trace("Path is {}", path);
        return "redirect@" + path + "/app/subscriberAccount";
    }
}
