package fi.imberg.juhani.ernu.cli.exceptions;

import fi.imberg.juhani.ernu.util.Range;

public class NotEnoughArgumentsException extends CLIException {
    public NotEnoughArgumentsException(Range wanted, int got) {
        super("Wanted " + wanted + " arguments but got " + got + " instead.");
    }
}
