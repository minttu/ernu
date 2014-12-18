package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.IntegerNode;
import fi.imberg.juhani.ernu.parser.node.Node;

public class NumberParser implements PrefixParser {

    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new IntegerNode(Integer.parseInt(token.getContent()));
    }
}
