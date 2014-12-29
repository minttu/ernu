package fi.imberg.juhani.ernu;

/**
 * Base exception class for all other exceptions to be based on.
 */
public class ErnuException extends Exception {
    public ErnuException(String string) {
        super(string);
    }
}
