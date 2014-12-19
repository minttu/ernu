package fi.imberg.juhani.ernu.cli;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.cli.command.*;
import fi.imberg.juhani.ernu.cli.exceptions.CLIException;
import fi.imberg.juhani.ernu.cli.exceptions.NotEnoughArgumentsException;

import java.util.HashMap;

public class CLI {
    private final HashMap<String, Command> commands;

    public CLI() {
        this.commands = new HashMap<>();
        addCommand(new ParseCommand());
        addCommand(new TokenCommand());
        addCommand(new RunCommand());
        addCommand(new REPLCommand());
    }

    public void addCommand(Command command) {
        this.commands.put(command.getCommand(), command);
    }

    public void parseArguments(String[] args) {
        if (args.length == 0 || args[0].equals("help")) {
            showHelp();
        } else {
            Command command = commands.get(args[0]);
            if (command == null) {
                System.err.println("Unknown command \"" + args[0] + "\"");
                showHelp();
                System.exit(1);
            }
            String[] realArgs = new String[args.length - 1];
            System.arraycopy(args, 1, realArgs, 0, args.length - 1);
            try {
                if (!command.getRange().inRange(realArgs.length)) {
                    throw new NotEnoughArgumentsException(command.getRange(), realArgs.length);
                }
                command.call(realArgs);
            } catch (ErnuException exception) {
                if (exception instanceof CLIException) {
                    System.err.println(exception.getMessage());
                } else {
                    exception.printStackTrace();
                }
                System.exit(-1);
            }
        }
    }

    private void showHelp() {
        System.out.println("Usage: ernu [COMMAND] [ARGUMENTS]");
        System.out.println("  help        display this help");
        for (Command command : commands.values()) {
            System.out.println(String.format("  %-12s%s", command.getCommand(), command.getDescription()));
        }
    }
}
