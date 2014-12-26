package fi.imberg.juhani.ernu.cli.exceptions;

public class UnknownCommandException extends CLIException {
    public UnknownCommandException(String string) {
        super("Unknown command: \"" + string + "\"");
    }
}
