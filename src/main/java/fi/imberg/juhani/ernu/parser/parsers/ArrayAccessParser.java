package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.node.ArrayAccessNode;
import fi.imberg.juhani.ernu.interpreter.node.IdentifierNode;
import fi.imberg.juhani.ernu.interpreter.node.Node;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;


public class ArrayAccessParser implements InfixParser {
    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        Node index = parser.parseNode();
        parser.match(TokenType.RBRACKET);
        return new ArrayAccessNode((IdentifierNode) left, index);
    }

    @Override
    public int getPrecedence() {
        return 10;
    }
}
