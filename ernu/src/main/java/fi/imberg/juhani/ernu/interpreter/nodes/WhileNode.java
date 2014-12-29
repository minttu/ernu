package fi.imberg.juhani.ernu.interpreter.nodes;

import fi.imberg.juhani.ernu.interpreter.Environment;
import fi.imberg.juhani.ernu.interpreter.exceptions.RuntimeException;
import fi.imberg.juhani.ernu.interpreter.interfaces.Node;

/**
 * While nodes evaluate the body so long as the conditional is true.
 */
public class WhileNode implements Node {
    private final Node conditional;
    private final BlockNode body;

    public WhileNode(Node conditional, BlockNode body) {
        this.conditional = conditional;
        this.body = body;
    }

    @Override
    public String toString() {
        return "(while " + conditional + " " + body + ")";
    }

    @Override
    public Node getValue(Environment environment) throws RuntimeException {
        Node valueNode = new NullNode();
        while (true) {
            Node truth = conditional.getValue(environment);
            if (!(truth instanceof BooleanNode)) {
                throw new RuntimeException("Conditional is not a boolean");
            }
            if (!((BooleanNode) truth).isTrue()) {
                break;
            }
            valueNode = body.getValue(environment);
        }
        return valueNode;
    }
}
