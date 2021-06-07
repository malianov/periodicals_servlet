package hire.me.controller.command.account;

import hire.me.controller.command.Command;
import hire.me.controller.command.CommandUtility;
import hire.me.model.entity.account.UserStatus;
import hire.me.model.service.ServiceFactory;
import hire.me.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockUnblockCommand implements Command {
private static final Logger logger = LogManager.getLogger(BlockUnblockCommand.class);

    private UserService userService;
    private ServiceFactory serviceFactory;

    public BlockUnblockCommand() {
        logger.trace("Inside constructor");
        this.serviceFactory = ServiceFactory.getInstance();
        this.userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.trace("execute");
        CommandUtility.disallowBackToCached(request, response);
        final String userLogin = request.getParameter("user_login");
        String newStatus = request.getParameter("status");

        if(!userService.isLoginExist(userLogin)) {
            logger.trace("Login {} doesn't exist", userLogin);
            return "/WEB-INF/view/subscribers.jsp";
        }

        if(newStatus.equals("unblock")) {
            newStatus = UserStatus.ACTIVE.name();
        } else if(newStatus.equals("block")) {
            newStatus = UserStatus.BLOCKED.name();
        } else {
            // Exception - no suitable value
        }

        try {
            userService.changeUserStatus(userLogin, newStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return "/WEB-INF/view/subscribers.jsp";
        }

        String path = request.getServletContext().getContextPath();
        return "redirect@" + path + "/app/to_subscribers_page";
    }
}
