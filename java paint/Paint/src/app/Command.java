package app;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Daan Eekhof
 */

// used as the "Broker" replacement for the Command Pattern
public class Command {
    //Create command instance <<Singleton Pattern>>
    private static Command instance = new Command();
    //Create list of commands
    private List<ICommand> commands = new ArrayList<ICommand>();

    //Returns the current instance <<Singleton Pattern>>
    public static Command getInstance() {
        return instance;
    }
    //Adds a command to the list
    public void addCommand(ICommand command) {
        commands.add(command);
    }
    
    //Execute given command
    public void executeCommand() {
        for (ICommand command : commands) {
            command.execute();
        }
        commands.clear();
    }
}