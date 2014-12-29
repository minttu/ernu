package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Identifier;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.TokenType;

/**
 * A node that assigns a node to an identifier.
 */
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
        if (!(left instanceof Identifier)) {
            throw new RuntimeException("Left needs to be identifier or array access");
        }
        Node realValue = right.getValue(environment);
        Identifier identifier = (Identifier) left;

        if (type != TokenType.SET) {
            Node leftValue = left.getValue(environment);
            OperatorNode calculation = new OperatorNode(leftValue, type, realValue);
            realValue = calculation.getValue(environment);
        }

        identifier.setValue(environment, realValue);
        return realValue;
    }
}
