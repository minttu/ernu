package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * A internal node that represents a single case of a match expression.
 */
public class CaseNode implements Node {

    /**
     * A node that must resolve into a boolean node
     */
    private final Node conditional;
    /**
     * The body of the case node, what gets run
     */
    private final BlockNode body;

    /**
     * @param conditional A node that must resolve into a boolean node
     * @param body        The body of the case node, what gets run
     */
    public CaseNode(Node conditional, BlockNode body) {
        this.conditional = conditional;
        this.body = body;
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        return body.getValue(environment);
    }

    /**
     * Is the conditional true in the given environment
     *
     * @param environment Where to check
     * @return True if the conditional is true
     * @throws RuntimeException If anything goes haywire, this gets thrown
     */
    public boolean matches(Environment environment) throws RuntimeException {
        Node value = conditional.getValue(environment);
        if (!(value instanceof BooleanNode)) {
            throw new RuntimeException("Conditional must be boolean");
        }
        return ((BooleanNode) value).isTrue();
    }

    @Override
    public String toString() {
        return "(" + conditional + " " + body + ")";
    }

}
