package fi.imberg.juhani.ernu.parser.exceptions;

import fi.imberg.juhani.ernu.parser.Token;

/**
 * Exception that occurs when an unknown token is met.
 */
public class ParsingException extends LangException {
    /**
     * @param string The error message
     * @param token  The token that we ran into
     */
    public ParsingException(String string, Token token) {
        super(string + " \"" + token.getContent() + "\" Near " + (token.getLine() + 1) + ":" + (token.getColumn() + 1));
    }
}
