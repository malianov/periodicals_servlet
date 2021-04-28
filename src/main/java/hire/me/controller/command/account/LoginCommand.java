package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.SubscriberService;
import hire.me.model.validator.DataValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    private SubscriberService subscriberService;
    private ServiceFactory serviceFactory;

    public LoginCommand() {
        this.serviceFactory = ServiceFactory.getInstance();
        this.subscriberService = serviceFactory.getSubscriberService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        if(!subscriberService.isLoginExist(login)) {
            return "/WEB-INF/common/login.jsp?loginExist=false";
        }

        if(subscriberService.isPasswordCorrectForLogin(login, password)) {
            CommandUtility.logUser(req, login, password);
        } else {
            return "/WEB-INF/common/login.jsp?passwordCorrect=false";
        }

        String path = req.getServletContext().getContextPath();

        return "redirect@" + path + "/app/personal-cabinet";
    }
}
