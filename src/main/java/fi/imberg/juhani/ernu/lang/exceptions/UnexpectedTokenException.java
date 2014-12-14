package fi.imberg.juhani.ernu.lang.exceptions;


import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.TokenType;

public class UnexpectedTokenException extends LangException {
    public UnexpectedTokenException(TokenType expected, Token got) {
        super(String.format("Unexpected token met while parsing. Expected %s but got %s instead. Near %d:%d",
                expected, got.getType(), got.getLine()+1, got.getColumn()+1));
    }
}
