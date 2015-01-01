package fi.imberg.juhani.ernu.cli.exceptions;

/**
 * Thrown when the given command was unknown.
 */
public class UnknownCommandException extends CLIException {
    public UnknownCommandException(String string) {
        super("Unknown command: \"" + string + "\"");
    }
}
