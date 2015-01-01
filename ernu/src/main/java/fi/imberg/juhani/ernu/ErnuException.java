package fi.imberg.juhani.ernu;

/**
 * Base exception class for all other exceptions to be based on.
 */
public class ErnuException extends Exception {
    /**
     * @param string The error message
     */
    public ErnuException(String string) {
        super(string);
    }
}
