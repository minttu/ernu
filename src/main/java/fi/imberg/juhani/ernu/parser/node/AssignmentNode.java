package fi.imberg.juhani.ernu.parser.node;

import fi.imberg.juhani.ernu.parser.TokenType;

public class AssignmentNode implements Node {
    private final IdentifierNode left;
    private final Node right;
    private final TokenType type;
    private String display;

    public AssignmentNode(IdentifierNode left, TokenType type, Node right) {
        this.left = left;
        this.right = right;
        this.type = type;
        display = type.getMatch();
        if (display.charAt(0) == '\\') {
            display = display.substring(1);
        }
    }

    public IdentifierNode getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + display + " " + this.left + " " + this.right + ")";
    }
}
