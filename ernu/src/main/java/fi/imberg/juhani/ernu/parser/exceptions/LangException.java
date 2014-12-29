package fi.imberg.juhani.ernu.parser.exceptions;

import fi.imberg.juhani.ernu.ErnuException;

/**
 * Base language exception for other language exceptions to be based on.
 */
public class LangException extends ErnuException {
    public LangException(String string) {
        super(string);
    }
}
