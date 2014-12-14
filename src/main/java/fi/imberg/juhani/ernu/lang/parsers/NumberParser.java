package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.IntegerNode;
import fi.imberg.juhani.ernu.lang.node.Node;

public class NumberParser implements PrefixParser {

    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new IntegerNode(Integer.parseInt(token.getContent()));
    }
}
