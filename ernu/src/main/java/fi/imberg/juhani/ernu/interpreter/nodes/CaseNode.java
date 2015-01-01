package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * A internal node that represents a single case of a match expression.
 */
public class CaseNode implements Node {

    private final Node conditional;
    private final BlockNode body;

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
     * @param environment Where to check
     * @return True if the conditional is true
     * @throws RuntimeException
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
