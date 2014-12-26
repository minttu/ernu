package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.BooleanNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

public class BooleanParser implements PrefixParser {
    @Override
    public Node parse(Parser parser, Token token) throws LangException {
        return new BooleanNode(token.getType().equals(TokenType.TRUE));
    }
}
