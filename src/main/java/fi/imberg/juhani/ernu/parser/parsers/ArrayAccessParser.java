package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;
import fi.imberg.juhani.ernu.parser.node.ArrayAccessNode;
import fi.imberg.juhani.ernu.parser.node.Node;


public class ArrayAccessParser implements InfixParser {
    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        Node index = parser.parseNode();
        parser.match(TokenType.RBRACKET);
        return new ArrayAccessNode(left, index);
    }

    @Override
    public int getPrecedence() {
        return 10;
    }
}
