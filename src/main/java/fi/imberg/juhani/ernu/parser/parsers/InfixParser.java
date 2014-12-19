package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.Node;

public interface InfixParser {
    Node parse(Parser parser, Node left, Token token) throws LangException;

    int getPrecedence();
}
