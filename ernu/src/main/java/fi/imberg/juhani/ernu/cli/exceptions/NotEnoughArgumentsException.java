package fi.imberg.juhani.ernu.cli.exceptions;

import fi.imberg.juhani.ernu.util.Range;

/**
 * Thrown when not enough arguments were given to a command.
 */
public class NotEnoughArgumentsException extends CLIException {
    public NotEnoughArgumentsException(Range wanted, int got) {
        super("Wanted " + wanted + " arguments but got " + got + " instead.");
    }
}
