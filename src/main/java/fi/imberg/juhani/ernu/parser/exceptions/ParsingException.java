package fi.imberg.juhani.ernu.parser.exceptions;

import fi.imberg.juhani.ernu.parser.Token;

public class ParsingException extends LangException {
    public ParsingException(String string, Token token) {
        super(string + " \"" + token.getContent() + "\" Near " + (token.getLine() + 1) + ":" + (token.getColumn() + 1));
    }
}
