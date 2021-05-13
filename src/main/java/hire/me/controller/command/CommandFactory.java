package hire.me.controller.command;

import hire.me.controller.command.account.LoginCommand;
import hire.me.controller.command.account.RegistrationCommand;
import hire.me.controller.command.directions_to.*;
import hire.me.model.exception.NotFoundCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static hire.me.controller.command.util.Operation.*;

public class CommandFactory {
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put(LOGIN.getPath(),new LoginCommand());
        commands.put(REGISTRATION.getPath(),new RegistrationCommand());
        commands.put(TO_LOGIN_PAGE.getPath(), new LoginPage());
        commands.put(TO_REGISTRATION_PAGE.getPath(), new RegistrationPage());
        commands.put(TO_HOME_PAGE.getPath(), new HomePage());
        commands.put(TO_CATALOG.getPath(), new CatalogPage());
        commands.put(TO_SUPPORT_PAGE.getPath(), new SupportPage());
    }

    public static Command getCommand(String url) throws NotFoundCommandException {
        Command command = commands.get(url);
        logger.trace("Inside getCommand: command {}", command);
        if (command == null) {
            throw new NotFoundCommandException();
        }
        return command;
    }
}
