package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.util.Range;

/**
 * Interface for all command line commands.
 */
public interface Command {
    /**
     * Calls the command with args.
     *
     * @param args the arguments to the command
     * @throws ErnuException If anything bad happens
     */
    public void call(String[] args) throws ErnuException;

    /**
     * The number of arguments this command takes
     *
     * @return A range of the possible argument numbers
     */
    public Range getRange();

    /**
     * Gets the name of the command
     *
     * @return The name
     */
    public String getCommand();

    /**
     * Gets the description of the command
     *
     * @return The description
     */
    public String getDescription();
}
