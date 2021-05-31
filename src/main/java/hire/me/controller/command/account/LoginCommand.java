package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.entity.account.UserRole;
import hire.me.model.service.UserService;
import hire.me.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

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
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        if(!userService.isLoginExist(login)) {
            logger.trace("Login {} doesn't exist", login);
            return "/WEB-INF/view/common/login.jsp?loginExist=false";
        }

        final UserRole role = userService.getRoleByLogin(login);
        final BigDecimal privateAccount = userService.getPrivateAccountByLogin(login);
        final Long userId = userService.getIdByLogin(login);

        if(userService.isPasswordCorrectForLogin(login, password, role)) {
            if (CommandUtility.checkUserIsLogged(request, login)) {
                return "/WEB-INF/view/common/error/multilogin.jsp";
            }
            CommandUtility.loginUser(request, userId, login, password, role, privateAccount);
        } else {
            logger.trace("Password is incorrect");
            return "/WEB-INF/view/common/login.jsp?passwordCorrect=false";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_home_page";
    }
}
