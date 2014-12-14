package fi.imberg.juhani.ernu.lang.parsers;

import fi.imberg.juhani.ernu.lang.Parser;
import fi.imberg.juhani.ernu.lang.Token;
import fi.imberg.juhani.ernu.lang.TokenType;
import fi.imberg.juhani.ernu.lang.exceptions.LangException;
import fi.imberg.juhani.ernu.lang.node.Node;
import fi.imberg.juhani.ernu.lang.node.OperatorNode;

public class OperatorParser implements InfixParser {
    private final int precedence;
    private final boolean isRightAssociative;

    public OperatorParser(int precedence, boolean isRightAssociative) {
        this.precedence = precedence;
        this.isRightAssociative = isRightAssociative;
    }

    @Override
    public Node parse(Parser parser, Node left, Token token) throws LangException {
        Node right = parser.parseNode(this.precedence - (isRightAssociative ? 1 : 0));
        return new OperatorNode(left, token.getType(), right);
    }

    @Override
    public int getPrecedence() {
        return precedence;
    }
}
