package fi.imberg.juhani.ernu.cli;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.cli.command.*;
import fi.imberg.juhani.ernu.cli.exceptions.CLIException;
import fi.imberg.juhani.ernu.cli.exceptions.NotEnoughArgumentsException;
import fi.imberg.juhani.ernu.cli.exceptions.UnknownCommandException;

import java.util.HashMap;

/**
 * Dispatches control to command line commands.
 */
public class CLI {
    /**
     * Contains all of the commands that are registered.
     */
    private final HashMap<String, Command> commands;

    public CLI() {
        this.commands = new HashMap<>();
        addCommand(new ParseCommand());
        addCommand(new TokenCommand());
        addCommand(new RunCommand());
        addCommand(new REPLCommand());
    }

    private void addCommand(Command command) {
        this.commands.put(command.getCommand(), command);
    }

    /**
     * Runs the commandline dispatcher
     *
     * @param args The programs arguments
     */
    public void run(String[] args) {
        try {
            parseArguments(args);
        } catch (ErnuException exception) {
            if (exception instanceof CLIException) {
                System.err.println(exception.getMessage());
                showHelp();
            } else {
                exception.printStackTrace();
            }
            System.exit(1);
        }
    }

    private void parseArguments(String[] args) throws ErnuException {
        if (args.length == 0 || args[0].equals("help")) {
            showHelp();
            return;
        }
        Command command = commands.get(args[0]);
        if (command == null) {
            throw new UnknownCommandException(args[0]);
        }
        String[] realArgs = new String[args.length - 1];
        System.arraycopy(args, 1, realArgs, 0, args.length - 1);
        if (!command.getRange().inRange(realArgs.length)) {
            throw new NotEnoughArgumentsException(command.getRange(), realArgs.length);
        }
        command.call(realArgs);
    }

    private void showHelp() {
        System.out.println("Usage: ernu [COMMAND] [ARGUMENTS]");
        System.out.println("  help        display this help");
        for (Command command : commands.values()) {
            System.out.println(String.format("  %-12s%s", command.getCommand(), command.getDescription()));
        }
    }
}
