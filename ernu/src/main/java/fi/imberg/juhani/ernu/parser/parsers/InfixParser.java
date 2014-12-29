package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

/**
 * Interface for all parsers that work from an infix position. We have already parsed some token with a prefix parser.
 */
public interface InfixParser {
    public Node parse(Parser parser, Node left, Token token) throws LangException;

    public int getPrecedence();
}
