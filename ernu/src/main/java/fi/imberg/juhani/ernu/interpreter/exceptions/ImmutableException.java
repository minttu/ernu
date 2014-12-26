package fi.imberg.juhani.ernu.interpreter.exceptions;

public class ImmutableException extends RuntimeException {
    public ImmutableException() {
        super("Object is immutable");
    }
}
