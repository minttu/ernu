package fi.imberg.juhani.ernu.cli.command;

import fi.imberg.juhani.ernu.ErnuException;
import fi.imberg.juhani.ernu.util.Range;

/**
 * Interface for all command line commands.
 */
public interface Command {
    /**
     * Calls the command with args.
     * @param args
     * @throws ErnuException
     */
    public void call(String[] args) throws ErnuException;

    /**
     * The number of arguments this command takes
     * @return
     */
    public Range getRange();

    /**
     * Gets the name of the command
     * @return
     */
    public String getCommand();

    /**
     * Gets the description of the command
     * @return
     */
    public String getDescription();
}
