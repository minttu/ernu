package fi.imberg.juhani.ernu.interpreter.exceptions;

/**
 * Thrown when an unknown attribute is being accessed.
 */
public class UnknownAttributeException extends RuntimeException {
    /**
     * @param string The string that was being used as an attribute
     */
    public UnknownAttributeException(String string) {
        super("Unknown attribute: \"" + string + "\"");
    }
}
