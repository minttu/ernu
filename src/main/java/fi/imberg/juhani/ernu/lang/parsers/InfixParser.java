package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.Node;

public interface InfixParser {
    Node parse(Parser parser, Node left, Token token) throws LangException;
    int getPrecedence();
}
