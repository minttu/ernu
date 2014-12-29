package fi.imberg.juhani.ernu.interpreter.exceptions;

/**
 * Thrown when an immutable object is tried to be modified.
 */
public class ImmutableException extends RuntimeException {
    public ImmutableException() {
        super("Object is immutable");
    }
}
