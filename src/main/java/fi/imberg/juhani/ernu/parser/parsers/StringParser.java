package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.Node;
import fi.imberg.juhani.ernu.parser.node.StringNode;

public class StringParser implements PrefixParser {

    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new StringNode(token.getContent());
    }
}
