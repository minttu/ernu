package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.Node;

public interface PrefixParser {
    Node parse(Parser parser, Token token) throws LangException;
}
