package fi.imberg.juhani.ernu.interpreter.exceptions;

public class UnknownAttributeException extends RuntimeException {
    public UnknownAttributeException(String string) {
        super("Unknown attribute: \"" + string + "\"");
    }
}
