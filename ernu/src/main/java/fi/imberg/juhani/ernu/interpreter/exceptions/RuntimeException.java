package fi.imberg.juhani.ernu.interpreter.exceptions;

import fi.imberg.juhani.ernu.ErnuException;

/**
 * Generic runtime exception that other runtime exceptions subclass.
 */
public class RuntimeException extends ErnuException {
    /**
     * @param string The error message
     */
    public RuntimeException(String string) {
        super(string);
    }
}
