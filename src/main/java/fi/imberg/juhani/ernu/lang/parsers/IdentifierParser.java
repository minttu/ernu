package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.IdentifierNode;
import fi.imberg.juhani.ernu.lang.node.Node;

public class IdentifierParser implements PrefixParser {

    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new IdentifierNode(token.getContent());
    }
}
