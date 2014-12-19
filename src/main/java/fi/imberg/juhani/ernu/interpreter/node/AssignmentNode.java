package fi.imberg.juhani.ernu.interpreter.node;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.parser.Token;
import fi.imberg.juhani.ernu.parser.TokenType;

import static fi.imberg.juhani.ernu.parser.TokenType.SET;

public class AssignmentNode implements Node {
    private final Node left;
    private final Node right;
    private final TokenType type;
    private String display;

    public AssignmentNode(Node left, TokenType type, Node right) {
        this.left = left;
        this.right = right;
        this.type = type;
        display = type.getMatch();
        if (display.charAt(0) == '\\') {
            display = display.substring(1);
        }
    }

    @Override
    public String toString() {
        return "(" + display + " " + this.left + " " + this.right + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        if (!(left instanceof IdentifierNode)) {
            throw new RuntimeException("Left needs to be identifier or array access");
        }
        Node realValue = right.getValue(environment);
        IdentifierNode identifier = (IdentifierNode) left;

        if(type != TokenType.SET) {
            Node leftValue = left.getValue(environment);
            OperatorNode calculation = new OperatorNode(leftValue, type, realValue);
            realValue = calculation.getValue(environment);
        }

        identifier.setValue(environment, realValue);
        return realValue;
    }
}
