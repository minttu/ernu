package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;
import fi.imberg.juhani.ernu.parser.TokenType;

/**
 * Represents an prefix operation on a node.
 */
public class PrefixNode implements Node {
    /**
     * The type of operation that this does
     */
    private final TokenType type;
    /**
     * The node to do the operation on
     */
    private final Node node;

    /**
     * @param type The type of operation that this does
     * @param node The node to do the operation on
     */
    public PrefixNode(TokenType type, Node node) {
        this.type = type;
        this.node = node;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node node = this.node.getValue(environment);
        switch (type) {
            case NOT:
                if (!(node instanceof BooleanNode)) {
                    throw new RuntimeException("Must be boolean to be notted!");
                }
                return new BooleanNode(!((BooleanNode) node).isTrue());
            case SUB:
                if (!(node instanceof NumberNode)) {
                    throw new RuntimeException("Must be number to be made negative");
                }
                return new OperatorNode(node, TokenType.MUL, new NumberNode("-1")).getValue(environment);
        }
        return new NullNode();
    }

    @Override
    public String toString() {
        return "(! " + node + ")";
    }
}
