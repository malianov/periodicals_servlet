package hire.me.controller.command;

import hire.me.controller.command.account.LoginCommand;
import hire.me.controller.command.account.RegistrationCommand;
import hire.me.controller.command.util.Operation;
import hire.me.model.exception.NotFoundCommandException;

import java.util.HashMap;
import java.util.Map;

import static hire.me.controller.command.util.Operation.LOGIN;
import static hire.me.controller.command.util.Operation.REGISTRATION;

public class CommandFactory {

    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put(LOGIN.getPath(),new LoginCommand());
        commands.put(REGISTRATION.getPath(),new RegistrationCommand());
    }

    public static Command getCommand(String url) throws NotFoundCommandException {
        Command command = commands.get(url);
        if (command == null) {
            throw new NotFoundCommandException();
        }
        return command;
    }
}
