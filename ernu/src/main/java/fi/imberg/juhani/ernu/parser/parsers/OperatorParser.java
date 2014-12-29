package fi.imberg.juhani.ernu.parser.parsers;

import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.interpreter.nodes.OperatorNode;
import fi.imberg.juhani.ernu.parser.Parser;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.exceptions.LangException;

/**
 * Parses an expression for an operator. For example 3 + 2
 */
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
