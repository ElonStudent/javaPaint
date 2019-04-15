package app;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Daan Eekhof
 */

// used as the "Broker" replacement for the Command Pattern
public class Command {
    private static Command instance = new Command();
    private List<ICommand> commands = new ArrayList<ICommand>();

    public static Command getInstance() {
        return instance;
    }

    public void addCommand(ICommand command) {
        commands.add(command);
    }

    public void executeCommand() {
        for (ICommand command : commands) {
            command.execute();
        }
        commands.clear();
    }
}