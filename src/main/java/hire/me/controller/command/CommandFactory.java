package hire.me.controller.command;

import hire.me.controller.command.account.*;
import hire.me.controller.command.directions_to.*;
import hire.me.controller.command.subscription.MakeSubscriptionCommand;
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
        commands.put(TO_HOME_PAGE.getPath(), new HomePage());
        commands.put(TO_CATALOG.getPath(), new CatalogPage());
        commands.put(TO_SUPPORT_PAGE.getPath(), new SupportPage());
        commands.put(TO_SUBSCRIBERS_PAGE.getPath(), new SubscribersPage());
        commands.put(LOGOUT.getPath(),new LogOutCommand());
        commands.put(BLOCK_UNBLOCK.getPath(), new BlockUnblockCommand());
        commands.put(EDIT_PERIODIC.getPath(), new EditPeriodicCommand());
        commands.put(MAKE_ORDER_NONORDER_PERIODIC.getPath(), new OrderableNonorderableCommand());
        commands.put(MAKE_SUBSCRIPTION.getPath(), new MakeSubscriptionCommand());
        commands.put(ADDITION_TO_BALANCE.getPath(),new PrivateAccountCommand());
        commands.put(SHOW_ALL_SUBSCRIPTIONS.getPath(), new SubscriptionsPage());
        commands.put(SHOW_ALL_SINGLE_USER.getPath(), new SingleUserSubscriptionsPage());
    }

    public static Command getCommand(String url) throws NotFoundCommandException {
        Command command = commands.get(url);

        if (command == null) {
            throw new NotFoundCommandException();
        }

        return command;
    }
}
