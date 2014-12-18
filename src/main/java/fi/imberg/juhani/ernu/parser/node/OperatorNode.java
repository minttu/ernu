package fi.imberg.juhani.ernu.parser.node;

import fi.imberg.juhani.ernu.parser.TokenType;

public class OperatorNode implements Node {
    private final Node left;
    private final Node right;
    private final TokenType operator;
    private String display;

    public OperatorNode(Node left, TokenType operator, Node right) {
        this.right = right;
        this.operator = operator;
        this.left = left;
        display = operator.getMatch();
        if(display.charAt(0) == '\\') {
            display = display.substring(1);
        }
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public TokenType getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "(" + display + " " + left + " " + right + ")";
    }
}
